package com.angus.exesion.AppRunner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AppStartupRunner.java
 * @Description TODO
 * @createTime 2021年05月18日 22:33:00
 */
@Component
public class AppStartupRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationArguments扩展点,拥有比CommandLineRunner更好的参数封装");
    }
}
