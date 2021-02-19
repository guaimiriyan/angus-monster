package com.angus.proxy;

import com.angus.service.angusService;
import com.angus.service.angusServiceImpl;

import java.lang.reflect.Proxy;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName JdkAngusProxy.java
 * @Description TODO
 * @createTime 2021年02月19日 23:28:00
 */
public class JdkAngusProxy {
    public static void main(String[] args) {
        angusService o = (angusService)Proxy.newProxyInstance(angusServiceImpl.class.getClassLoader(), angusServiceImpl.class.getInterfaces(), new JdkAngusInvokeHandler(new angusServiceImpl()));
        o.talk();
    }
}
