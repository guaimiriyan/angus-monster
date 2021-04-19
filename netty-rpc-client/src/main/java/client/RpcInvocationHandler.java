package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import transport.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
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
    RpcInvocationHandler(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())){
            return method.invoke(proxy,args);
        }else {
            return rpcInvoke(proxy,method,args);
        }


    }

    private Object rpcInvoke(Object proxy, Method method, Object[] args) {
        final clientHandler clientHandler = new clientHandler();
        try {
            RpcRequest request = new RpcRequest();
            request.setCallClazz(method.getDeclaringClass().getName());
            request.setMethodName(method.getName());
            request.setParams(args);
            request.setCallVersion("V2.0");
            //1、将此处改为neeey
            EventLoopGroup group = new NioEventLoopGroup();
            final Bootstrap client = new Bootstrap();
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            final ChannelPipeline pipeline = channel.pipeline();
                            //初步理解为将报文翻译为可识别的
                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            //自定义协议编码器
                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                            //对象参数类型编码器
                            pipeline.addLast("encoder", new ObjectEncoder());
                            //对象参数类型解码器
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            //最后进行自定义操作
                            pipeline.addLast("handler",clientHandler);
                        }
                    });
            final ChannelFuture connect = client.connect("127.0.0.1", 8080).sync();
            connect.channel().writeAndFlush(request).sync();
            connect.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }
        return clientHandler.getResponse();
    }
}
