package com.angus.impl;

import org.apache.dubbo.config.annotation.DubboService;
import service.IHelloService;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName HelloServiceImpl.java
 * @Description TODO
 * @createTime 2021年05月13日 09:44:00
 */
@DubboService(interfaceClass = IHelloService.class)
public class HelloServiceImpl implements IHelloService {

    @Override
    public String hello(String echo) {
        return "sb";
    }
}
