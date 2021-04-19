package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import register.rpcRegister;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ServerConfig.java
 * @Description TODO
 * @createTime 2021年04月19日 22:15:00
 */
@Configuration
@ComponentScan(value = {"service"})
public class ServerConfig {

    @Bean(name = "rpcRgister")
    public rpcRegister getRpcRgister(){
        return new rpcRegister();
    }
}
