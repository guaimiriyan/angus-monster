package selfSpring.beans;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusBeanWrapper.java
 * @Description TODO
 * @createTime 2021年03月29日 22:57:00
 */
public class AngusBeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public AngusBeanWrapper(Object wrappedInstance){
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance(){
        return this.wrappedInstance;
    }

    // 返回代理以后的Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }
}
