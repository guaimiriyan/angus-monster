package com.angus.extension;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName AngusEventListener.java
 * @Description TODO
 * @createTime 2021年05月18日 09:58:00
 */
@Component
public class AngusEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("你你你你你你你你你你你你你你你你你你你");
    }


    @Async
    @EventListener
    public void createOrderEvent(ApplicationStartedEvent event){
        System.out.println(this.getClass().getName()+"--订单创建事件，@EventListener注解实现");
    }
    /**
     * 此处为spring扩展点一
     */
}
