package selfSpring.webmvc;

import selfMvc.mvcframework.annotation.GPController;
import selfMvc.mvcframework.annotation.GPRequestMapping;
import selfSpring.beans.util.BeanNameUtil;
import selfSpring.context.AngusApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusDispatcherServlet.java
 * @Description TODO
 * @createTime 2021年03月31日 21:45:00
 */
public class AngusDispatcherServlet extends HttpServlet {


    private AngusApplicationContext context;

    private List<AngusHandlerMapping> handlerMappings = new ArrayList<AngusHandlerMapping>();


    private Map<AngusHandlerMapping, AngusHandlerAdapter> handlerAdapters = new HashMap<AngusHandlerMapping, AngusHandlerAdapter>();

    private List<AngusViewResolver> viewResolvers = new ArrayList<AngusViewResolver>();

    /**
     * 重写init方法加载
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        //1、初始化容器上下文
        context = new AngusApplicationContext();
        //2、加载springmvc的九大主键
        initStrategies(context);
    }

    //初始化策略
    protected void initStrategies(AngusApplicationContext context) {
        //多文件上传的组件,非必要
        //initMultipartResolver(context);
        //初始化本地语言环境,非必要
        //initLocaleResolver(context);
        //初始化模板处理器,非必要
        //initThemeResolver(context);
        //handlerMapping 必要
        initHandlerMappings(context);
        //初始化参数适配器 必要
        initHandlerAdapters(context);
        //初始化异常拦截器
       //initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        //initRequestToViewNameTranslator(context);
        //初始化视图转换器 必要
        initViewResolvers(context);
        //
        //initFlashMapManager(context);
    }

    private void initViewResolvers(AngusApplicationContext context) {
    }

    private void initHandlerAdapters(AngusApplicationContext context) {
        //1、每一个handlerMapping对应着一个HandlerAdapters
        //2、
    }

    /**
     * 初始化url与method对应关系
     * @param context
     */
    private void initHandlerMappings(AngusApplicationContext context) {
        //1、从上下文里获取出初测了的beanWapper
        final String[] definitionNames = context.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            /**
             * 这里如果是单例也是在缓存里进行获取所以效率不会太慢，与和beanwapper速率无异
             */
            final Object bean = context.getBean(definitionName);
            final Class<?> aClass = bean.getClass();
            //判断是否为Action
            if (!aClass.isAnnotationPresent(GPController.class)) continue;
            String outPath = "";
            GPRequestMapping outMapping = aClass .getAnnotation(GPRequestMapping.class);
            outPath =  outMapping.value();
            if ("".equals(outPath)){
                outPath= "/"+ BeanNameUtil.toLowerFirst(aClass.getSimpleName());
            }
            //获取里层方法,只需要有public的
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(GPRequestMapping.class)) continue;
                String inPath = "";
                GPRequestMapping inMapping = method.getAnnotation(GPRequestMapping.class);
                inPath = inMapping.value();
                if ("".equals(inPath)) {
                    inPath = method.getName();
                }
                //最后把url和method封装到handlermapping中
                String allPath = "/"+outPath.replaceAll("/","")+"/"+inPath.replaceAll("/","").replaceAll("\\*",".*");
                /**
                 * 创建HandlerMapping将对象封装起来
                 */
                final Pattern pattern =  Pattern.compile(allPath);
                this.handlerMappings.add(new AngusHandlerMapping(pattern,bean,method));

        }
    }


}
}
