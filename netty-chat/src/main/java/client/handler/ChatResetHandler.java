package client.handler;

import client.chatClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ChatResetHandler.java
 * @Description TODO
 * @createTime 2021年04月25日 22:13:00
 */
public class ChatResetHandler extends ChannelInboundHandlerAdapter {
    chatClient chatClient;
    public ChatResetHandler(chatClient chatClient){
        this.chatClient = chatClient;
    }
    static int  retryCount = 10;
    int retrySleepTime = 10;
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (retryCount == 0) {
            System.err.println("Lost the TCP connection with the server.");
            ctx.close();
        }

        int sleepTime = (11-retryCount)*retrySleepTime;
        System.out.println(sleepTime+"s,后进行重试！");
        //在这里进行重试
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("Reconnecting ...");
                chatClient.conect();
                --retryCount;
            }
        },sleepTime, TimeUnit.SECONDS);

        //在这里处理重连的事情
        super.channelInactive(ctx);
    }
}
