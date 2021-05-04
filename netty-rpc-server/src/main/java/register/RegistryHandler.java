package register;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
import transport.RpcRequest;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName RegistryHandler.java
 * @Description TODO
 * @createTime 2021年04月19日 22:42:00
 */
public class RegistryHandler extends ChannelInboundHandlerAdapter {

    Map<String,Object> registerRpcService;
    RegistryHandler(Map<String,Object> registerRpcService){
        this.registerRpcService = registerRpcService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = (RpcRequest)msg;
        System.out.println("llllllllllllllllll");
        //当客户端建立连接时，需要从自定义协议中获取信息，拿到具体的服务和实参
        //使用反射调用
        final Object invoke = invoke(request);
        ctx.write(invoke);
        ctx.flush();
        ctx.close();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private Object invoke(RpcRequest request){
        try {
            final Class<?> aClass = Class.forName(request.getCallClazz());
            final String name = aClass.getName();
            final String callVersion = request.getCallVersion();
            final String methodName = request.getMethodName();
            final Object[] params = request.getParams();
            System.out.println(name + callVersion);
            final Object o = registerRpcService.get(name + callVersion);
            Method method = null;
            if (params != null) {
                Class<?>[] types = new Class[params.length]; //获得每个参数的类型
                for (int i = 0; i < params.length; i++) {
                    types[i] = params[i].getClass();
                }
                method = aClass.getMethod(methodName, types);
            }

            Object reslut = null;
            if (o != null) {
                if (params != null) {
                    reslut = method.invoke(o, params);
                } else {
                    System.out.println(o);
                    reslut = method.invoke(o);
                }
            }
            return  reslut;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
