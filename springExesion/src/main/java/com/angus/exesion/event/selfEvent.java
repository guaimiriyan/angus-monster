package com.angus.exesion.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName selfEvent.java
 * @Description TODO
 * @createTime 2021年05月18日 22:11:00
 */
public class selfEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public selfEvent(Object source) {
        super(source);
    }
}
