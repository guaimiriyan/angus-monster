package Config;

import client.RpcClientProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName ClientConfig.java
 * @Description TODO
 * @createTime 2021年04月16日 11:18:00
 */
@Configuration
public class ClientConfig {

 @Bean(name = "rpcClient")
    public RpcClientProxy getRpcClientProxy(){
     return new RpcClientProxy();
 }

}
