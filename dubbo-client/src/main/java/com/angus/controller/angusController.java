package com.angus.controller;

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
    @DubboReference(interfaceClass = IHelloService.class)
    IHelloService iHelloService;
    @GetMapping("/hello")
    public String hello(){
        return iHelloService.hello("hello world!");
    }
}
