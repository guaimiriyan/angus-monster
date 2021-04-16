package provide;

import transport.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName RpcRequestHandler.java
 * @Description TODO
 * @createTime 2021年04月16日 10:51:00
 */
public class RpcRequestHandler implements Runnable{

    Socket socket;
    Map<String,Object> handlerMap;

    RpcRequestHandler(Socket socket,Map<String,Object> handlerMap){
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    /**
     * 执行真正的调用
     */
    @Override
    public void run() {
        System.out.println("````````````````");
        //1、首先从客户端传的套接字中获取Request
        try(ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ) {
            final Object object = ois.readObject();
            if (object instanceof RpcRequest){
                System.out.println("11111111111111111");
                final RpcRequest request = (RpcRequest) object;
                final Class<?> aClass = Class.forName(request.getCallClazz());
                final String name = aClass.getName();
                final String callVersion = request.getCallVersion();
                final String methodName = request.getMethodName();
                final Object[] params = request.getParams();
                System.out.println(name + callVersion);
                final Object o = handlerMap.get(name + callVersion);
                Method method = null;
                if(params!=null) {
                    Class<?>[] types = new Class[params.length]; //获得每个参数的类型
                    for (int i = 0; i < params.length; i++) {
                        types[i] = params[i].getClass();
                    }
                      method =  aClass.getMethod(methodName,types);
                }

                Object reslut = null;
                if (o!=null){
                    if (params != null){
                        reslut = method.invoke(o,params);
                    }else {
                        System.out.println(o);
                        reslut = method.invoke(o);
                    }
                }
                if (request!=null){
                    oos.writeObject(reslut);
                    oos.flush();
                }
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
