package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Application.java
 * @Description TODO
 * @createTime 2021年04月27日 21:45:00
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
    }
}
