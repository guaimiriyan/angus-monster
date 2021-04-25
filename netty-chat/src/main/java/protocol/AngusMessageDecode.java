package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusMessageDecode.java
 * @Description TODO
 * @createTime 2021年04月24日 01:58:00
 */
public class AngusMessageDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            final int length = in.readableBytes();
            byte[] array = new byte[length];
            in.readBytes(array,0,length);
            out.add(new MessagePack().read(array,angusMessage.class));
        }catch (Exception e){
            //将该channel的pipeline中去掉
            ctx.channel().pipeline().remove(this);
        }

    }
}
