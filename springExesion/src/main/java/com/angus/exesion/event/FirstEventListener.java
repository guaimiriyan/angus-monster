package com.angus.exesion.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName FirstEventListener.java
 * @Description TODO
 * @createTime 2021年05月18日 21:53:00
 */
@Component
public class FirstEventListener implements ApplicationListener {
    /**
     * 该方式是使用继承ApplicationListener的方式进行事件的
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.getClass().getSimpleName()+"事件通知！");

    }
}
