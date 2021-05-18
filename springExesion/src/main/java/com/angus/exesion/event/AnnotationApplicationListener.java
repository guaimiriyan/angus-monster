package com.angus.exesion.event;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.jws.Oneway;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AnnotationApplicationListener.java
 * @Description TODO
 * @createTime 2021年05月18日 21:58:00
 */
@Component
public class AnnotationApplicationListener {

    /**
     * 使用注解的方式进行spring事件的扩展,并使用异步的方式进行事件的消费
     * 使用@enableAsync的方式进行异步的开始，原理在之后进行研究
     * @param event
     */
    @Async
    @EventListener(classes = ApplicationStartedEvent.class)
    public void notifyByAnnotation(ApplicationEvent event){
        System.out.println(event.getClass().getSimpleName()+"使用注解的方式进行事件的响应");
    }
}
