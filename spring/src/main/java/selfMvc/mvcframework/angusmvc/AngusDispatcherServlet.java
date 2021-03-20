package selfMvc.mvcframework.angusmvc;

import selfMvc.mvcframework.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusDispatcherServlet.java
 * @Description 编写自定义DispatcherServlet
 * @createTime 2021年03月20日 15:31:00
 */
public class AngusDispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    private List<String> classes = new ArrayList<>();


    private Map<String,Object> ioc = new ConcurrentHashMap<>();

    private Map<String,Method> handlerMapping = new ConcurrentHashMap<>();
    @Override
    /**
     * @title init
     * @description
     * @author admin [config]
     * @updateTime 2021/3/20 [config] void
     * @throws 继承HttpServlet类,初始化
     */
    public void init(ServletConfig config) throws ServletException {

        /**
         * 1、加载mvc配置文件
         */

        loadConfig(config);

        /**
         *2、解析配置文件，找到配置文件中所涉及的类
         */

        resloveConfigFindClass(properties.getProperty("scanPackage"));

        /**
         * 3、创建类并交给ioc管理
         */
        instanceAndIoc();

        /**
         *4、进行注入
         */
        autowiredInstance();

        /**
         *5、初始化HandlerMapping
         */
        initHandlerMapping();

        /**
         * 6、进行调用
         */
        //doDispatcher();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatcher(req,resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            resp.getWriter().write("500 error!");
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        //获取访问的路径
        String url = req.getRequestURI();
        //处理成相对路径
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");
        if (!handlerMapping.containsKey(url)){
            resp.getWriter().write("404 not find!");
            return;
        }
        Method method = handlerMapping.get(url);
        //获取出参数
        Map<String,String[]> params = req.getParameterMap();

        //获取参数
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] paramValues = new Object[parameterTypes.length];
        int index = -1;
        for (Class<?> parameterType : parameterTypes) {
            index++;
            if (parameterType == HttpServletRequest.class){
                paramValues[index] = req;
                continue;
            }else if (parameterType == HttpServletResponse.class){
                paramValues[index] = resp;
                continue;
            }else {
                String fieldParam = null;
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                //判断该字段上是否有注解
                //if (parameterType.isAnnotationPresent(GPRequestParam.class)){
                GPRequestParam an = null;
                Annotation[] parameterAnnotation = parameterAnnotations[index];
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation.annotationType() == GPRequestParam.class){
                        an = (GPRequestParam)annotation;
                    }
                }
                if (an != null){
                    String paramStr = an.value();
                    if(params.containsKey(paramStr)) {
                        for (Map.Entry<String,String[]> param : params.entrySet()){
                            fieldParam = Arrays.toString(param.getValue())
                                    .replaceAll("\\[|\\]","")
                                    .replaceAll("\\s",",");
                            //这里可以写个转换方法进行策略模式转换
                            paramValues[index] = fieldParam;
                        }
                    }
                }
            }
        }

        //进行反射调用
        //获取需要调用的action
        String beanName  = toLowerFirst(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName),paramValues);
    }

    /**
     * 使用循环ioc中的数据，进行ioc的中controller的发现并注入
     */
    private void initHandlerMapping() {
        if (ioc.isEmpty()) return;

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
           if (!entry.getValue().getClass().isAnnotationPresent(GPController.class)) continue;
           //获取Requestmapping
            String outPath = "";
            GPRequestMapping outMapping = entry.getValue().getClass().getAnnotation(GPRequestMapping.class);
            outPath =  outMapping.value();
            if ("".equals(outPath)){
                outPath= "/"+toLowerFirst(entry.getValue().getClass().getSimpleName());
            }
            //获取里层方法,只需要有public的
            Method[] methods = entry.getValue().getClass().getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(GPRequestMapping.class)) continue;
                String inPath = "";
                GPRequestMapping inMapping = method.getAnnotation(GPRequestMapping.class);
                inPath = inMapping.value();
                if ("".equals(inPath)) {
                    inPath = method.getName();
                }
                //最后把url和method封装到handlermapping中
                String allPath = "/"+outPath.replaceAll("/","")+"/"+inPath.replaceAll("/","");
                handlerMapping.put(allPath,method);
                System.out.println("Mapped :" + allPath + "," + method);
            }
        }

    }

    /**
     * 使用简单的方式进行属性注入
     */
    private void autowiredInstance() {
        if (ioc.isEmpty()) return;
        //循环使用ioc中的容器获取
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            //获取出所有的fields，通过循环field进行注入
            for (Field field : fields) {
                String fieldBeanName = "";
             if (!field.isAnnotationPresent(GPAutowired.class)){
                 fieldBeanName = field.getAnnotation(GPAutowired.class).value();
                 if ("".equals(fieldBeanName)){
                     //使用类名小写的方式注入
                     fieldBeanName = toLowerFirst(field.getClass().getSimpleName());
                 }
                 Object fieldBean = ioc.get(fieldBeanName);
                 if (fieldBean==null) throw new RuntimeException("ioc容器中未找到"+fieldBeanName);
                 field.setAccessible(true);
                 try {
                     field.set(entry.getValue(),fieldBean);
                 } catch (IllegalAccessException e) {
                     e.printStackTrace();
                 }
             }
            }
        }
    }

    /**
     * 实例化classes下的类放入ioc容器中
     */
    private void instanceAndIoc() {
        if (classes.isEmpty()) return;
        for (String clazzStr : classes) {
            try {
                Class<?> clazz = Class.forName(clazzStr);
                //首先判断是否有什么注解的
                if (clazz.isAnnotationPresent(GPController.class)){
                    //实例化数据service
                    ioc.put(toLowerFirst(clazz.getSimpleName()),clazz.newInstance());
                }else if (clazz.isAnnotationPresent(GPService.class)){
                    //1、当service里存在存在名字时使用名字注册
                    GPService annotation = clazz.getAnnotation(GPService.class);
                    //实例化数据
                    Object o = clazz.newInstance();
                    String servicename = annotation.value();
                    if ("".equals(servicename)){
                        servicename = toLowerFirst(clazz.getSimpleName());
                    }
                    //2、等于空的时候使用类名小写注入
                    ioc.put(servicename,o);
                    //3、最后将所有接口都接口都注入进去
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        if (!ioc.containsKey(anInterface.getSimpleName())){
                            ioc.put(anInterface.getName(),o);
                        }
                    }

                }else {
                    continue;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
    }


    private String toLowerFirst(String name){
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
    /**
     *
     * 获取路径下的类
     */
    private void resloveConfigFindClass(String path) {
               //使用读取文件
        URL resource = getClass().getClassLoader().getResource("/" + path.replaceAll("\\.", "/"));
        File file = new File(resource.getFile());
        for(File childFile : file.listFiles()){
            if (childFile.isDirectory()){
                resloveConfigFindClass(path+"."+childFile.getName());
            }else{
                if (!childFile.getName().endsWith("class")) continue;
                classes.add(path+"."+childFile.getName().replace(".class",""));
            }
        }

    }

    /**
     *加载配置文件
     */
    private void loadConfig(ServletConfig config)   {
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL resource = getClass().getClassLoader().getResource(contextConfigLocation);
       try ( FileInputStream file = new FileInputStream(new File(resource.getFile()));){
           properties.load(file);
       }catch (IOException e){
           e.printStackTrace();
       }
    }
}
