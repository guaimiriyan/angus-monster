package properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName properties.formatProperties.java
 * @Description TODO
 * @createTime 2021年04月27日 22:40:00
 */
@ConfigurationProperties(prefix = "format")
public class formatProperties {

    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
