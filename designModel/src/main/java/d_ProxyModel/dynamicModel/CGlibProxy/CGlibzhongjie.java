package d_ProxyModel.dynamicModel.CGlibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName CGlibzhongjie.java
 * @Description 使用cglib模拟中介
 * @createTime 2021年03月15日 09:27:00
 */
public class CGlibzhongjie implements MethodInterceptor {


    /**
     * 创建代理对象
     */
    public Object getInstance(Class clazz){
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setClassLoader(clazz.getClassLoader());
        enhancer.setCallback(this);
        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        methodProxy.invokeSuper(o,objects);
        after();
        return null;
    }
    private void before(){
        System.out.println("jdk中介为您服务！");
    }

    private void after(){
        System.out.println("jdk中介完成服务！");
    }

}
