package simpleIoDemo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName NioClient.java
 * @Description TODO
 * @createTime 2021年04月17日 15:51:00
 */
public class NioClient {




    int port;
    private Selector selector;
    private SocketChannel socketChannel;

    NioClient(int port){
        try {
            this.port = port;
            socketChannel = SocketChannel.open();
            selector = Selector.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen(){
        doConnect();
        while (true){
            try {
                selector.select(1000);
                final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                final Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    process(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void process(SelectionKey key) throws IOException{
        if (key.isValid()){
            final SocketChannel channel = (SocketChannel) key.channel();
            if (key.isConnectable()){
                if (channel.finishConnect()){
                    channel.register(selector,SelectionKey.OP_READ);
                    dowrite(channel);
                }else {
                    System.exit(1);
                }


            }
            if (key.isReadable()){
                final ByteBuffer allocate = ByteBuffer.allocate(1024);
                final int read = channel.read(allocate);
                if (read>0){
                    allocate.flip();
                    String content = new String(allocate.array(),0,read);
                    System.out.println(content);
                }

            }
        }
    }

    private void dowrite(SocketChannel channel) throws  IOException{
        String sql = "客户端连接服务器！";
        ByteBuffer byteBuffer = ByteBuffer.wrap(sql.getBytes());
        //byteBuffer.flip();
        channel.write(byteBuffer);
    }

    private void doConnect() {
        try {
            if (socketChannel.connect(new InetSocketAddress("127.0.0.1",port))){
                socketChannel.register(selector, SelectionKey.OP_READ);
                dowrite(socketChannel);
            }else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioClient(8080).listen();
    }
}
