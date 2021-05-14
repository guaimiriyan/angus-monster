package com.angus.Mock;

import service.IHelloService;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName HelloServiceMock.java
 * @Description TODO
 * @createTime 2021年05月14日 11:10:00
 */
public class HelloServiceMock implements IHelloService {
    @Override
    public String hello(String echo) throws InterruptedException {
        return "服务不可用！";
    }
}
