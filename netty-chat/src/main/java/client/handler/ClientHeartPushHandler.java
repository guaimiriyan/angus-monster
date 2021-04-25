package client.handler;

import client.chatClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;
import protocol.HeartMessageUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ClientHeartPushHandler.java
 * @Description TODO
 * @createTime 2021年04月25日 21:31:00
 */
@Slf4j
public class ClientHeartPushHandler extends ChannelInboundHandlerAdapter {


    //在这里进行进行chanel的保存并且触发ping的push工作
    Channel channel;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        channel = ctx.channel();
        System.out.println("连接服务器中，准备发送心跳！");
        pushHeartBear(ctx);

    }

    private void pushHeartBear(ChannelHandlerContext ctx) {
        //1、向eventLoop中添加一个Task，定时像服务器添加心跳包
        final ScheduledFuture<?> schedule = channel.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                if (channel.isActive()){
                    ctx.writeAndFlush(HeartMessageUtil.getClientHeartMsg());
                }else {
                    channel.closeFuture();
                    throw new RuntimeException("连接未创建成功！");
                }

            }
        }, HeartMessageUtil.HearBeatTime, TimeUnit.SECONDS);
        //2、当返回成功时就自动发送下一次心跳
       schedule.addListener(new GenericFutureListener() {
           @Override
           public void operationComplete(Future future) throws Exception {
               if (future.isSuccess()) {
                   pushHeartBear(ctx);
               }
           }
       });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("出现错误：",cause);
       ctx.close();
    }
}
