package com.angus.exesion.init.initMethod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName initConfig.java
 * @Description TODO
 * @createTime 2021年05月18日 22:46:00
 */
@Configuration
@Component
public class initConfig {

    @Bean(initMethod = "init")
    public InitMethodExampleBean getInitMethodExampleBean(){
        return new InitMethodExampleBean();
    }

}
