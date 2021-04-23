package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusMessageEncode.java
 * @Description TODO
 * @createTime 2021年04月24日 01:57:00
 */
public class AngusMessageEncode extends MessageToByteEncoder<angusMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, angusMessage msg, ByteBuf out) throws Exception {
        /**
         * 如果是encode说明是此时是需要向外传输，属于outbound，直接往外写就可以
         */
        out.writeBytes(new MessagePack().write(msg));
    }
}
