package selfSpring.context.support;

import selfSpring.beans.config.AngusBeanDefinition;
import selfSpring.beans.factory.BeanFactory;
import selfSpring.beans.support.AngusBeanDefinitionRegistry;

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

    private final Map<String, AngusBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

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
             }
         }
    }

    @Override
    public AngusBeanDefinition getBeanDefinition(String beanName) {
        return null;
    }
}
