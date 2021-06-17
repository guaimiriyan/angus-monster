package com.angus;

import com.angus.exesion.event.selfEvent;
import com.angus.exesion.init.All.AllStrategiesExampleBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName App.java
 * @Description TODO
 * @createTime 2021年05月18日 21:54:00
 */
@SpringBootApplication
@EnableAsync
public class App {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(App.class, args);
        run.getBean(AllStrategiesExampleBean.class);
        run.publishEvent(new selfEvent("angus event"));
    }
}
