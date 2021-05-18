package com.angus.exesion.init.initMethod;

import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName InitMethodExampleBean.java
 * @Description TODO
 * @createTime 2021年05月18日 22:45:00
 */
@Component
public class InitMethodExampleBean {

    public void init(){
    System.out.println("init_method初始化");
    }
}
