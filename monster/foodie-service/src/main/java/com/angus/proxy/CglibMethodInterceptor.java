package com.angus.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName CglibMethodInterceptor.java
 * @Description TODO
 * @createTime 2021年02月20日 00:06:00
 */
public class CglibMethodInterceptor implements MethodInterceptor {
    /**
     * @param o           被代理的对象（需要增强的对象）
     * @param method      被拦截的方法（需要增强的方法）
     * @param objects        方法入参
     * @param methodProxy 用于调用原始方法
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //method.invoke(o,objects);
        methodProxy.invokeSuper(o,objects);
        return null;
    }
}
