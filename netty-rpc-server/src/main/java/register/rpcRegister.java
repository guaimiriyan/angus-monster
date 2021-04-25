package register;

import annotation.rpcService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName rpcRegister.java
 * @Description 使用netty来实现一个rpc服務端
 * @createTime 2021年04月19日 22:16:00
 */
public class rpcRegister implements ApplicationContextAware, InitializingBean {

    Map<String,Object> registerRpcService = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        //在此处将之前使用的websocket转换为现在需要使用的netty的方式，现在首先将port写死

        this.start();
    }

    private void start() {
        try {
            //1、首先将两个线程组创建出来，分别是boss和工作线程组
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            //2、创建类似于nio中的serversocketchannal的东西
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            final ChannelPipeline pipeline = ch.pipeline();
                            //初步理解为将报文翻译为可识别的
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            //应该是反序列化的一种实现吧
                            pipeline.addLast("encoder",new ObjectEncoder());
                            pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            //最后进行自定义操作
                            pipeline.addLast(new RegistryHandler(registerRpcService));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            final ChannelFuture sync = serverBootstrap.bind(8080).sync();
            System.out.println("netty rpc server start listen at 8080");
            sync.channel().closeFuture().sync();
        }catch (Exception e){

        }


    }


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
