package autoConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import processor.formatProcessor;
import properties.formatProperties;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName formatAutoConfig.java
 * @Description TODO
 * @createTime 2021年04月27日 22:07:00
 */
@Configuration
@Import(formatConfig.class)
@EnableConfigurationProperties(formatProperties.class)
public class formatAutoConfig {
    @Bean
    public FormatTemplate getFormatTemplate(formatProcessor processor){
        return new FormatTemplate(processor);
    }
}
