package com.angus.proxy;

import com.angus.service.angusService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName JdkAngusInvoke.java
 * @Description TODO
 * @createTime 2021年02月19日 23:38:00
 */
public class JdkAngusInvokeHandler implements InvocationHandler {

    private com.angus.service.angusService angusService;

    public JdkAngusInvokeHandler(com.angus.service.angusService angusService) {
        this.angusService = angusService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("1000");
        method.invoke(angusService,args);
        return null;
    }
}
