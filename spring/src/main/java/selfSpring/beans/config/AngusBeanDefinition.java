package selfSpring.beans.config;

import com.sun.istack.internal.Nullable;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName BeanDefinition.java
 * @Description TODO
 * @createTime 2021年03月28日 20:48:00
 */
public class AngusBeanDefinition {
    @Nullable
    private volatile Object beanClass;
    private boolean primary = false;
    private boolean lazyInit = false;
    @Nullable
    private String factoryBeanName;

    public Object getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Object beanClass) {
        this.beanClass = beanClass;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
