package com.controller;

import autoConfig.FormatTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import properties.formatProperties;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName HelloController.java
 * @Description TODO
 * @createTime 2021年04月27日 21:48:00
 */
@RestController
public class HelloController {
    @Autowired
    FormatTemplate formatTemplate;
    @Autowired
    formatProperties formatProperties;

    @RequestMapping("/hello")
    public String hello(){
        return formatTemplate.format(new Mag("angus","18"))+formatProperties.getValue();
    }
}
