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
    private volatile Class beanClass;
    private boolean primary = false;
    private boolean lazyInit = false;
    private boolean singlton = true;
    @Nullable
    private String factoryBeanName;

    public Class getBeanClass() {
        return beanClass;
    }

    public boolean isSinglton() {
        return singlton;
    }

    public void setSinglton(boolean singlton) {
        this.singlton = singlton;
    }

    public void setBeanClass(Class beanClass) {
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
