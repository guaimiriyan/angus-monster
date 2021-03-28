package selfSpring.beans.support;

import selfSpring.beans.config.AngusBeanDefinition;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusBeanDefinitionRegistry.java
 * @Description TODO
 * @createTime 2021年03月28日 22:55:00
 */
public interface AngusBeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, AngusBeanDefinition beanDefinition);



    AngusBeanDefinition getBeanDefinition(String beanName);
}
