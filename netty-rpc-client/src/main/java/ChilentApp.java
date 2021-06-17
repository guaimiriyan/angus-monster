import client.RpcClientProxy;
import config.ClientConfig;
import domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.IUserService;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName App.java
 * @Description TODO
 * @createTime 2021年04月16日 11:17:00
 */
public class ChilentApp {
    public static void main(String[] args) throws InterruptedException {
        final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ClientConfig.class);
        final RpcClientProxy rpcClient = (RpcClientProxy) annotationConfigApplicationContext.getBean("rpcClient");
        final IUserService localhost = rpcClient.clientProxy(IUserService.class);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(2000);
            localhost.dealUser(new User("angus",18));
        }

    }
}
