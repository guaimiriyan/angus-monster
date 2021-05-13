package com.angus;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName App.java
 * @Description TODO
 * @createTime 2021年05月13日 10:07:00
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = { "com.angus"})
public class App {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(App.class, args);
    }
}
