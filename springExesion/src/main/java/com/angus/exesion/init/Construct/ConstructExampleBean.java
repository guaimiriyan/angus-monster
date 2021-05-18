package com.angus.exesion.init.Construct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ConstructExampleBean.java
 * @Description TODO
 * @createTime 2021年05月18日 22:50:00
 */
@Component
public class ConstructExampleBean {
    Environment environment;
    @Autowired
    ConstructExampleBean(Environment environment){
        this.environment = environment;
        System.out.println("构造器注入方式进行！");
    }
}
