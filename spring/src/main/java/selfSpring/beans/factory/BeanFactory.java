package selfSpring.beans.factory;

import selfSpring.beans.config.AngusBeanDefinition;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName BeanFactory.java
 * @Description 创建bean的顶层接口，主要是使用其进行springbean的初始化
 * @createTime 2021年03月28日 20:22:00
 */
public interface BeanFactory {

    String FACTORY_BEAN_PREFIX = "&";

    /**
     * 简单创建一个使用名称进行获取bean的方式，使用该方法对ioc中的bean进行获取
     * 或者对lazy-init的bean进行初始化和di，aop
     * @param name
     * @return
     */
    Object getBean(String name);

}
