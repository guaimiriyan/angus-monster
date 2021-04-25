package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import protocol.ChatCmdType;
import protocol.angusMessage;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName chatServerHandler.java
 * @Description TODO
 * @createTime 2021年04月24日 02:50:00
 */
@Slf4j
public class chatServerHandler extends SimpleChannelInboundHandler<angusMessage> {


    ChannelGroup onlineUsers;
    public chatServerHandler(ChannelGroup onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, angusMessage msg) throws Exception {
        final String cmd = msg.getCmd();
        if (ChatCmdType.LOGIN.cmdStr.equals(cmd)){
            onlineUsers.add(ctx.channel());
            onlineUsers.writeAndFlush(msg);

        }else if (ChatCmdType.LOGOUT.cmdStr.equals(cmd)){
            onlineUsers.writeAndFlush(msg);
            onlineUsers.remove(ctx.channel());

        }else if (ChatCmdType.CHAT.cmdStr.equals(cmd)){
            onlineUsers.writeAndFlush(msg);
        }else if (ChatCmdType.HEART.cmdStr.equals(cmd)){
            System.out.println(msg.toString());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            final IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE){
                System.out.println("客户端"+ctx.channel().remoteAddress().toString()+"超时失去连接！");
                ctx.disconnect();
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }

    }
}
