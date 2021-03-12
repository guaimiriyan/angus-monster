package d_ProxyModel.dynamicModel.jdkProxy;


import d_ProxyModel.beipiao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName JdkInvokeHander.java
 * @Description TODO
 * @createTime 2021年03月13日 00:10:00
 */
public class Jdkzhongjie implements InvocationHandler {

    beipiao beipiao;

    public Jdkzhongjie(d_ProxyModel.beipiao beipiao) {
        this.beipiao = beipiao;
    }

    public beipiao getInstance(){
        return (beipiao)Proxy.newProxyInstance(beipiao.getClass().getClassLoader(), beipiao.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(beipiao,args);
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
