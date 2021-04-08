package selfSpring.context;

import com.sun.istack.internal.Nullable;
import selfMvc.mvcframework.annotation.GPAutowired;
import selfMvc.mvcframework.annotation.GPService;
import selfSpring.beans.AngusBeanWrapper;
import selfSpring.beans.config.AngusBeanDefinition;
import selfSpring.beans.factory.AngusBeanDefinitionReader;
import selfSpring.beans.factory.BeanFactory;
import selfSpring.beans.util.BeanNameUtil;
import selfSpring.context.support.AngusAbstractApplicationContext;
import selfSpring.context.support.AngusDefaultListableBeanFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusApplicationContext.java
 * @Description 使用该类模拟spring-ApplicationContext
 * @createTime 2021年03月28日 20:28:00
 */
public class AngusApplicationContext extends AngusAbstractApplicationContext implements BeanFactory {



    private final Map<String, AngusBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>(16);

    /**
     * 用于存放
     */
    private String[] configLocations;

    /**
     * 该beanFactory本来是放在AbstractRefreshableApplicationContext中
     * 为了简化开发将其放在自建的ApplicationContext下
     */
    @Nullable
    private AngusDefaultListableBeanFactory beanFactory;

    private final String BEAN_PATH = "scanPackage";

    private final String BEAN_PATH_SPLIT = ";";

    private final Object beanFactoryMonitor = new Object();

    public AngusApplicationContext(){
        setConfigLocations();
        refresh();
    }

    @Override
    public Object getBean(String name) {
        //省略doGetBaen,简略单吗

        //1、此处进行单例缓存的获取
        final Object singleton = beanFactory.getSingleton(name);
        if (singleton != null){
            return singleton;
        }else {
            //2、获取beandefinition
            final AngusBeanDefinition abd = beanFactory.beanDefinitionMap.get(name);
            //3、对dependsOn进行处理，此处省略
            //4、真正创建
            beanFactory.getSingleton(name,()->{
                return  createBean(name, abd);
            });

        }
        return null;
    }

    private Object createBean(String name, AngusBeanDefinition abd) {
        //1、处理重写方法
        //2、前置处理器
//        Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
//        if (bean != null) {
//            return bean;
//        }
        Object beanInstance = doCreateBean(name, abd);
        return beanInstance;
    }

    private Object doCreateBean(String name, AngusBeanDefinition abd) {
        AngusBeanWrapper instanceWrapper = null;
        if (abd.isSinglton()) {
            instanceWrapper = this.factoryBeanInstanceCache.remove(name);
        }
        if (instanceWrapper == null) {
            instanceWrapper = createBeanInstance(name, abd);
        }
        final Object bean = instanceWrapper.getWrappedInstance();
        //获取实例化对象的类型
        Class<?> beanType = instanceWrapper.getWrappedClass();

        //在此处将其放入提前暴露
        beanFactory.addSingleton(name,bean);
        //进行属性注入
        populateBean(name, abd, instanceWrapper);
        //此处进行aop
        return bean;
    }

    private void populateBean(String name, AngusBeanDefinition abd, AngusBeanWrapper instanceWrapper) {
        final Class<?> wrappedClass = instanceWrapper.getWrappedClass();
        if (!wrappedClass.isAnnotationPresent(GPService.class)&&!wrappedClass.isAnnotationPresent(GPService.class)) return;
        //1、初始化前置
        //2、注入
        final Field[] declaredFields = wrappedClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (!field.isAnnotationPresent(GPAutowired.class)) continue;
            GPAutowired autowired = field.getAnnotation(GPAutowired.class);
            String autowiredBeanName =  autowired.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName = BeanNameUtil.toLowerFirst(field.getType().getName());
                final Object bean = getBean(autowiredBeanName);
                field.setAccessible(true);
                try {
                    field.set(instanceWrapper.getWrappedInstance(),bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        //3、初始化后置
    }

    private AngusBeanWrapper createBeanInstance(String name, AngusBeanDefinition abd) {
        //1、获取需要实例化的class
        final Class beanClass = abd.getBeanClass();
        AngusBeanWrapper angusBeanWrapper = null;
        if (beanClass == null){
            throw new RuntimeException("在beanDefinition中未获取到class！");
        }
        try {
            final Constructor constructor = beanClass.getConstructor(null);
            constructor.setAccessible(true);
            final Object instance = constructor.newInstance(null);
            angusBeanWrapper = new AngusBeanWrapper(instance);

        }catch (Exception e){

        }
        return angusBeanWrapper;

    }

    public void setConfigLocations() {
        //1、获取配置文件文件
        String[] paths = null;
        URL resource = getClass().getClassLoader().getResource("application.properties");
        File file = new File(resource.getFile());
        try(FileInputStream inputStream = new FileInputStream(file)){
            Properties config = new Properties();
            config.load(inputStream);
            final String property = config.getProperty(BEAN_PATH);
            paths = property.split(BEAN_PATH_SPLIT);

        }catch (Exception e){

        }
        if (paths == null) throw new RuntimeException("未配置加载路径！");
        configLocations = paths;
    }

    /**
     * 重写AngusAbstractApplicationContext的方法，主要是适配自己的加载方式
     */
    @Override
    protected final AngusDefaultListableBeanFactory refreshBeanFactory(){
        //1、首先创建默认的DefalutListBeanFactory
        AngusDefaultListableBeanFactory beanFactory = new AngusDefaultListableBeanFactory();
        //2、此处可以对其进行一些属性的配置或者，搞一些个性化方法
        //3、loadBeanDefinition
        loadBeanDefinitions(beanFactory);
        synchronized (this.beanFactoryMonitor) {
            this.beanFactory = beanFactory;
        }
       return beanFactory;


    }

    @Override
    protected void loadBeanDefinitions(AngusDefaultListableBeanFactory beanFactory) {
        //1、创建BeanDefinitionReader进行配置文件
        AngusBeanDefinitionReader reader = new AngusBeanDefinitionReader();
        //2、直接调用reader的加载beandefindition
        reader.loadBeanDefinitions(this.configLocations,this.beanFactory);
    }

    /**
     * 添加一个可以获取到所有能使用到的beanwapper
     */

    public String[] getBeanDefinitionNames(){
        return factoryBeanInstanceCache.keySet().toArray(new String[factoryBeanInstanceCache.size()]);
    }
}