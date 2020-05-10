package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //当一条消息确认全部收到之后才开始解码工作
        if(in.readableBytes() < 37){return;}

        //首先把消息长度读出来，读出来之后以后的读操作都读不到这个信息了，
        // 就是说如果消息现在还是37字节，读完长度之后就要减去这个int的4字节
        // 以后再读这条消息他的长度就只有33字节了
        int length = in.readInt();

        byte[] bytes = new byte[length];

        in.readBytes(bytes);

        TankJoinMsg msg = new TankJoinMsg();
        msg.parse(bytes);

        out.add(msg);
    }
}
