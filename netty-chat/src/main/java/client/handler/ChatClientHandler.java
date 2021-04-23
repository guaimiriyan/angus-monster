package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import protocol.angusMessage;

import java.util.Random;
import java.util.Scanner;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ChatClientHandler.java
 * @Description TODO
 * @createTime 2021年04月24日 02:11:00
 */
@Slf4j
public class ChatClientHandler extends SimpleChannelInboundHandler<angusMessage> {


    ChannelHandlerContext ctx;
    private String nickname;
    public ChatClientHandler(){
        System.out.println("请输入你的昵称！");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNext()){
           this.nickname = sc.nextLine();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 1、保存上下文
         * 2、封装一个登录路消息给服务器
         * 3、进行数据发送
         */
        this.ctx = ctx;
        final angusMessage login = new angusMessage("login", nickname, System.currentTimeMillis(), nickname + "准备加入聊天室！");
        ctx.writeAndFlush(login);
        log.info("成功连接服务器,已执行登录动作");
        //在这里另起一个线程进行询问客户端输入数据
        clientLoop();
    }

    private void clientLoop() {
        new Thread(()->{
            System.out.println("请输入聊天内容");
            Scanner sc = new Scanner(System.in);
            angusMessage message = null;
            while (sc.hasNext()){
                final String cmd = sc.nextLine();
                //todo 这里可以进行优化
                if ("exit".equals(cmd)){
                    message = new angusMessage("logout",nickname,System.currentTimeMillis(),nickname+"退出聊天室！");
                    ctx.writeAndFlush(message);
                    break;
                }else {
                    message = new angusMessage("chat",nickname,System.currentTimeMillis(),cmd);
                    ctx.writeAndFlush(message);
                }
            }
            sc.close();

        }).start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, angusMessage msg) throws Exception {
        //1、首先判断msg是否为空，如果为空则放弃次聊天
        angusMessage m = (angusMessage)msg;
        System.out.println((null == m.getNickName() ? "" : (m.getNickName() + ":")) + m.getSendMsg());
    }
}
