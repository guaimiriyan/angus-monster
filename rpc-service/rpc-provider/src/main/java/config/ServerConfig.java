package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import provide.RpcServer;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName ServerConfig.java
 * @Description TODO
 * @createTime 2021年04月16日 10:14:00
 */
@Configuration
@ComponentScan(value = {"service"})
public class ServerConfig {

    @Bean(name = "rpcService")
    public RpcServer getRpcServer(){
        return new RpcServer();
    }
}
