package com.angus.controller;

import com.angus.Mock.HelloServiceMock;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.IHelloService;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName angusController.java
 * @Description TODO
 * @createTime 2021年05月13日 11:07:00
 */
@RestController("/angus")
public class angusController {
    @DubboReference(interfaceClass = IHelloService.class,cluster = "failfast",timeout = 3000,mock = "com.angus.Mock.HelloServiceMock")
    IHelloService iHelloService;
    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        return iHelloService.hello("hello world!");
    }
}
