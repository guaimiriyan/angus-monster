package simpleIoDemo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName NioServer.java
 * @Description TODO
 * @createTime 2021年04月17日 15:51:00
 */
public class NioServer {

    /**
     * 端口默认是8080
     */
    int port = 8080;

    /**
     * 设置一系列基于nio的非阻塞模式下的
     * 1、selector多路复用选择器
     * 2、buffer 管理数据的缓冲区
     */
    Selector selector;
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

   NioServer(int port){
       this.port = port;
       try {
           //1、获取监听某个fd的channel，这里是获取
           final ServerSocketChannel open = ServerSocketChannel.open();
           //2、绑定一个一个socke端口,地址默认为环回口地址
           open.bind(new InetSocketAddress(port));
           //3、将nio模型设置为非阻塞
           open.configureBlocking(false);
           //4、初始化一个多路复用器
           selector = Selector.open();
           //5、注册多路复用器，并添加状态,这标志着服务器已经可以支持其他连接了
           open.register(selector, SelectionKey.OP_ACCEPT);
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

   private void listen(){

       while (true) {
           //System.out.println("ssssss");
           try {
               //
               while (selector.select(50) > 0) {
                   //System.out.println("ssssss");
                   /**
                    * 此时应该是去获取一些基于信号驱动标记为为就绪状态的一些fd
                    */
                   ;
                   final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                   final Iterator<SelectionKey> iterator = selectionKeys.iterator();
                   while (iterator.hasNext()) {
                       final SelectionKey next = iterator.next();
                       iterator.remove();
                       process(next);
                   }


               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

   }


    public void  process(SelectionKey key) throws  IOException{
        /**
         * 应该是只有server的状态可以是accpt，在每次循环的时候就直接获取客户端套接字就行行操作
         */
       if (key.isAcceptable()){
           final ServerSocketChannel channel = (ServerSocketChannel) key.channel();
           final SocketChannel accept = channel.accept();
           accept.configureBlocking(false);
           //重新设置客户端套接字的内容
           accept.register(selector,SelectionKey.OP_READ);
       }else if (key.isReadable()){
           System.out.println("进入读状态");
           final SocketChannel channel = (SocketChannel) key.channel();
           final int read = channel.read(byteBuffer);
           if (read>0){
               //此方法是转换read和write的转换也就是将limit
               byteBuffer.flip();
               //从下面就可以进行读操作了
               String content = new String(byteBuffer.array(),0,read);
               //将该channel标志为写的状态
               channel.register(selector,SelectionKey.OP_WRITE|SelectionKey.OP_READ);
               //将读的内容传送到下一次中的轮询
               //key.attach(content);
               //byteBuffer.

               System.out.println("接收到客户端"+channel.getLocalAddress().toString()+":"+content);
           }
       } else if (key.isWritable()){
           final SocketChannel channel = (SocketChannel) key.channel();
           channel.write(ByteBuffer.wrap("hello client".getBytes()));
           //channel.register(selector,SelectionKey.OP_READ);
           System.out.println("进入写状态");
           //final String attachment = "服务器回显："+(String)key.attachment();
           //channel.write(ByteBuffer.wrap(attachment.getBytes()));
           //channel.register(selector,SelectionKey.OP_READ);
          // channel.close();
       }
    }

    public static void main(String[] args) {
       new NioServer(8080).listen();
    }
}
