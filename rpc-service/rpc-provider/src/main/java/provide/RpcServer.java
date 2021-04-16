package provide;

import annotation.rpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName RpcServer.java
 * @Description TODO
 * @createTime 2021年04月16日 10:19:00
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {
    AtomicInteger threadId = new AtomicInteger(0);
    //获取目前的cpu中数量
    int corePoolSize = Runtime.getRuntime().availableProcessors();
    //最大线程数
    int maximumPoolSize = 10;
    //最大存货时间
    long keepAliveTime = 1000l;
    TimeUnit unit = TimeUnit.MILLISECONDS;
    Map<String,Object> registerRpcService = new ConcurrentHashMap<>();
    //目前使用
   ExecutorService executorService = Executors.newCachedThreadPool(); //new ThreadPoolExecutor(corePoolSize,
//            maximumPoolSize,
//            keepAliveTime,
//            unit,
//            new ArrayBlockingQueue<>(10),
//            new ThreadFactory() {
//                @Override
//                public Thread newThread(Runnable r) {
//                    return new Thread(r,"rpcThread"+threadId.incrementAndGet());
//                }
//            },
//            new ThreadPoolExecutor.AbortPolicy());
    /**
     *使用初始化后置进行数据的rpc的执行
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //执行rpcServer
        ServerSocket serverSocket = new ServerSocket(8081);
        while (true){
            final Socket accept = serverSocket.accept();
            executorService.submit(new RpcRequestHandler(accept,registerRpcService));
        }
    }
    /**
     * 通过aware方式讲其他rpcservice提前记载道spring以至于while(true)不会造成
     * 这些类不被初始化
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        final Map<String, Object> beans = applicationContext.getBeansWithAnnotation(rpcService.class);
        for (String serviceName : beans.keySet()) {
            final Object o = beans.get(serviceName);
            final rpcService annotation = o.getClass().getAnnotation(rpcService.class);
            final Class<?> value = annotation.value();
            final String version = annotation.version();
            String registerName = value.getName()+version;
            registerRpcService.put(registerName,o);
        }

    }
}
