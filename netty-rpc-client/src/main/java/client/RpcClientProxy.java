package client;

import discovery.ZkDiscovery;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Proxy;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName RpcClientProxy.java
 * @Description TODO
 * @createTime 2021年04月16日 11:20:00
 */
public class RpcClientProxy {

    @Autowired
    ZkDiscovery zkDiscovery;

    public <T> T clientProxy(Class<T> tClass){
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(),new Class[]{tClass},new RpcInvocationHandler(zkDiscovery));
    }
}
