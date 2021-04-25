package client;

import client.handler.ChatClientHandler;
import client.handler.ChatResetHandler;
import client.handler.ClientHeartPushHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import protocol.AngusMessageDecode;
import protocol.AngusMessageEncode;
import protocol.HeartMessageUtil;

import java.util.Scanner;

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
    String NickName;

    chatClient(int port){
        System.out.println("请输入你的昵称！");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNext()){
            this.NickName = sc.nextLine();
        }
        this.port = port;
        bootstrap = new Bootstrap();
        workLoopGroup = new NioEventLoopGroup();
    }

    void start(chatClient chatClient){
        try {
            bootstrap.group(workLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            final ChannelPipeline pipeline = ch.pipeline();
                            //在此处给客户端添加心跳处理，如果10s没有调用channelwrite就吹触发
                            pipeline.addLast(new ClientHeartPushHandler());
                            pipeline.addLast(new ChatResetHandler(chatClient));
                            pipeline.addLast(new IdleStateHandler(0, HeartMessageUtil.HearBeatTime,0));
                            //在这里进行channelHandler的添加
                            //目前这里是基于普通的控制带进行聊天的控制
                            pipeline.addLast(new AngusMessageDecode());
                            pipeline.addLast(new AngusMessageEncode());
                            pipeline.addLast(new ChatClientHandler(NickName));
                        }
                    });
            //在这里进行端口的绑定
            final ChannelFuture sync =conect().sync();
            System.out.println("11111111111111111111111");
//            sync.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
//            workLoopGroup.shutdownGracefully();
        }
    }

    public ChannelFuture conect(){
        synchronized (bootstrap){
            final ChannelFuture connect = bootstrap.connect("127.0.0.1", this.port);
            connect.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        System.out.println("失去连接，即将重试");
                        future.channel().pipeline().fireChannelInactive();
                    }
                }
            });
            return connect;
        }

    }

    public static void main(String[] args) {
        final chatClient chatClient = new chatClient(8080);
        chatClient.start(chatClient);
    }
}
