package selfSpring.context;

import com.sun.istack.internal.Nullable;
import selfSpring.beans.factory.AngusBeanDefinitionReader;
import selfSpring.beans.factory.BeanFactory;
import selfSpring.context.support.AngusAbstractApplicationContext;
import selfSpring.context.support.AngusDefaultListableBeanFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusApplicationContext.java
 * @Description 使用该类模拟spring-ApplicationContext
 * @createTime 2021年03月28日 20:28:00
 */
public class AngusApplicationContext extends AngusAbstractApplicationContext implements BeanFactory {



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

    @Override
    public Object getBean(String name) {
        return null;
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
    protected final void refreshBeanFactory(){
        //1、首先创建默认的DefalutListBeanFactory
        AngusDefaultListableBeanFactory beanFactory = new AngusDefaultListableBeanFactory();
        //2、此处可以对其进行一些属性的配置或者，搞一些个性化方法
        //3、loadBeanDefinition
        loadBeanDefinitions(beanFactory);
        synchronized (this.beanFactoryMonitor) {
            this.beanFactory = beanFactory;
        }

    }

    @Override
    protected void loadBeanDefinitions(AngusDefaultListableBeanFactory beanFactory) {
        //1、创建BeanDefinitionReader进行配置文件
        AngusBeanDefinitionReader reader = new AngusBeanDefinitionReader();
        //2、直接调用reader的加载beandefindition
        reader.loadBeanDefinitions(this.configLocations,this.beanFactory);
    }
}
