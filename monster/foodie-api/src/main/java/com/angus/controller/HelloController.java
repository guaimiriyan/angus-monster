package com.angus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//默认返回为json数据
@RestController
public class HelloController {
    @GetMapping("/hello")
    public Object hello(){
        return "hello world";
    }

}
