package com.angus.exesion.init.PostCons;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName PostConstructExampleBean.java
 * @Description TODO
 * @createTime 2021年05月18日 22:43:00
 */
@Component
public class PostConstructExampleBean {
    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+"初始化");
    }
}
