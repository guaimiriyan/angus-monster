import Config.ClientConfig;
import client.RpcClientProxy;
import domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.ISaveService;
import service.IUserService;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName App.java
 * @Description TODO
 * @createTime 2021年04月16日 11:17:00
 */
public class ChilentApp {
    public static void main(String[] args) {
        final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ClientConfig.class);
        final RpcClientProxy rpcClient = (RpcClientProxy) annotationConfigApplicationContext.getBean("rpcClient");
        final IUserService localhost = rpcClient.clientProxy(IUserService.class, "127.0.0.1", 8081);
        localhost.dealUser(new User("angus",18));
    }
}
