package com.angus.exesion.event;

import org.springframework.context.ApplicationListener;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName selfApplicationListener.java
 * @Description TODO
 * @createTime 2021年05月18日 22:13:00
 */
public class selfApplicationListener implements ApplicationListener<selfEvent> {
    @Override
    public void onApplicationEvent(selfEvent event) {
        System.out.println("触发了自定义的selfevent");
    }
}
