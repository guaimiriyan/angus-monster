package selfSpring.context.support;

import selfSpring.beans.config.AngusBeanDefinition;
import selfSpring.beans.factory.BeanFactory;
import selfSpring.beans.factory.ObjectFactory;
import selfSpring.beans.support.AngusBeanDefinitionRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusDefaultListableBeanFactory.java
 * @Description TODO
 * @createTime 2021年03月28日 20:46:00
 */
public class AngusDefaultListableBeanFactory  implements BeanFactory, AngusBeanDefinitionRegistry {

    public final Map<String, AngusBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

    /** Cache of singleton objects: bean name --> bean instance */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    @Override
    public Object getBean(String name) {
       return null;
    }

    @Override
    public void registerBeanDefinition(String beanName, AngusBeanDefinition beanDefinition) {
         AngusBeanDefinition oldbd = beanDefinitionMap.get(beanName);
         if (oldbd !=null){
             if (!oldbd.equals(beanDefinition)){
                 System.out.println(beanName+"被覆盖!");
             }
             this.beanDefinitionMap.put(beanName, beanDefinition);
         }else {
             synchronized (this.beanDefinitionMap){
                 this.beanDefinitionMap.put(beanName, beanDefinition);
                 this.beanDefinitionNames.add(beanName);
             }
         }
    }

    @Override
    public AngusBeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    /**
     * 在此处进行未配置懒加载的bean进行初始化
     */
    public void preInstantiateSingletons() {
        List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
        //循环保存在defaultList中的
        for (String beanName : beanNames) {
            final AngusBeanDefinition angusBeanDefinition = this.beanDefinitionMap.get(beanName);
            //判断是否时单例或者是否为懒加载
            if (angusBeanDefinition.isSinglton()&&!angusBeanDefinition.isLazyInit()){
                getBean(beanName);
            }
        }
    }

    /**
     * 在此处添加方法用来判断是否为单例
     */
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    /**
     * 使用重载方法进行
     * @param beanName
     * @param singleFactory
     * @return
     */
    public Object getSingleton(String beanName, ObjectFactory singleFactory){
        synchronized (this.singletonObjects) {
             Object singleton = singletonObjects.get(beanName);
            if (singleton == null){
                singleton = singleFactory.getObject();
                //并将其放入单例缓存中
                this.singletonObjects.put(beanName,singleton);
            }
            return singleton;
        }
    }

    public void addSingleton(String name, Object bean) {
        synchronized (this.singletonObjects) {
            Object singleton = singletonObjects.get(name);
            if (singleton == null){
                //并将其放入单例缓存中
                this.singletonObjects.put(name,bean);
            }
        }
    }
}
