package client;

import transport.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName RpcInvocationHandler.java
 * @Description TODO
 * @createTime 2021年04月16日 11:25:00
 */
public class RpcInvocationHandler implements InvocationHandler {
    String ip;
    int port;
    RpcInvocationHandler(String ip,int port){
        this.ip = ip;
        this.port = port;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setCallClazz(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setCallVersion("V2.0");
        Socket socket = new Socket(ip,port);
        try(
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ) {
           oos.writeObject(request);
           oos.flush();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            final Object object = ois.readObject();
            System.out.println(object);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
