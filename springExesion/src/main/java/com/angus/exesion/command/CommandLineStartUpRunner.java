package com.angus.exesion.command;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName CommandLineStartUpRunner.java
 * @Description TODO
 * @createTime 2021年05月18日 22:24:00
 */
@Component
public class CommandLineStartUpRunner implements CommandLineRunner {
    /**
     * 可以获取到args参数，而且可以ApplicationStartedEvent
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("使用commandRunner扩展");
    }
}
