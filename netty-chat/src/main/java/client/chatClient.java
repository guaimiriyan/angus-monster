package client;

import client.handler.ChatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import protocol.AngusMessageDecode;
import protocol.AngusMessageEncode;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName chatClient.java
 * @Description TODO
 * @createTime 2021年04月24日 01:32:00
 */
public class chatClient {


    int port;
    Bootstrap bootstrap;
    EventLoopGroup workLoopGroup;

    chatClient(int port){
        this.port = port;
        bootstrap = new Bootstrap();
        workLoopGroup = new NioEventLoopGroup();
    }

    void start(){
        try {
            bootstrap.group(workLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            final ChannelPipeline pipeline = ch.pipeline();
                            //在这里进行channelHandler的添加
                            //目前这里是基于普通的控制带进行聊天的控制
                            pipeline.addLast(new AngusMessageDecode());
                            pipeline.addLast(new AngusMessageEncode());
                            pipeline.addLast(new ChatClientHandler());
                        }
                    });
            //在这里进行端口的绑定
            final ChannelFuture sync = bootstrap.connect("127.0.0.1",this.port).sync();
            sync.channel().closeFuture().sync();
        }catch (InterruptedException e){

        }finally {
            workLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new chatClient(8080).start();
    }
}
