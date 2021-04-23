package server;

import client.chatClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import protocol.AngusMessageDecode;
import protocol.AngusMessageEncode;
import server.handler.chatServerHandler;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName chatServer.java
 * @Description TODO
 * @createTime 2021年04月24日 02:46:00
 */
public class chatServer {
    int port;
    ServerBootstrap bootstrap;
    EventLoopGroup workLoopGroup;
    EventLoopGroup boosLoopGroup;
    //记录在线用户
    private static ChannelGroup onlineUsers = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    chatServer(int port){
        this.port = port;
        bootstrap = new ServerBootstrap();
        workLoopGroup = new NioEventLoopGroup();
        boosLoopGroup = new NioEventLoopGroup();
    }

    void start(){
        try {
            bootstrap.group(boosLoopGroup,workLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            final ChannelPipeline pipeline = ch.pipeline();
                            //在这里进行channelHandler的添加
                            //目前这里是基于普通的控制带进行聊天的控制
                            pipeline.addLast(new AngusMessageDecode());
                            pipeline.addLast(new AngusMessageEncode());
                            pipeline.addLast(new chatServerHandler(onlineUsers));
                        }
                    });
            //在这里进行端口的绑定
            final ChannelFuture sync = bootstrap.bind(this.port).sync();
            System.out.println("服务器启动成功,监听的端口号"+this.port);
            sync.channel().closeFuture().sync();
        }catch (InterruptedException e){

        }finally {
            boosLoopGroup.shutdownGracefully();
            workLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new chatServer(8080).start();
    }
}
