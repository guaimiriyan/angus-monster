package com.angus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName App.java
 * @Description TODO
 * @createTime 2021年05月13日 11:10:00
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(App.class, args);
    }
}
