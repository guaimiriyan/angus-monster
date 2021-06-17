package com.angus.exesion.init.All;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AllStrategiesExampleBean.java
 * @Description TODO
 * @createTime 2021年05月18日 22:52:00
 */
@Component
@Scope("prototype")
public class AllStrategiesExampleBean implements InitializingBean {
     

    public AllStrategiesExampleBean() {
        System.out.println("Constructor");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("PostConstruct");
    }

    public void init() {
        System.out.println("init-method");
    }
}
