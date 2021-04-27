package autoConfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import processor.JdkFormatProcessor;
import processor.JsonFormatProcessor;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName formatConfig.java
 * @Description TODO
 * @createTime 2021年04月27日 22:02:00
 */
@Configuration
public class formatConfig {

    @Bean
    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
    public JdkFormatProcessor getJdkProcessor(){
        return new JdkFormatProcessor();
    }

    @Bean
    @Primary
    @ConditionalOnClass(name = "com.alibaba.fastjson.JSON")
    public JsonFormatProcessor getJsonProcessor(){
        return new JsonFormatProcessor();
    }
}
