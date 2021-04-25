import config.ServerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName App.java
 * @Description TODO
 * @createTime 2021年04月16日 09:57:00
 */
public class App {
    /**
     * 使用spring注册rpcserver，并执行rpc生命周期
     */
    public static void main(String[] args) {
        final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ServerConfig.class);
        annotationConfigApplicationContext.start();
    }
}
