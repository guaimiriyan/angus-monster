package annotation;

import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName rpcApi.java
 * @Description rpcservice,标注为该注解的类自动主入到spring
 * @createTime 2021年04月16日 09:58:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface rpcService {
    Class<?> value();
    String version() default "";
}
