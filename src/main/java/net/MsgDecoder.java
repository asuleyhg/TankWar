package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //确定收到消息类型和消息长度，如果没收到则直接返回,消息类型和长度都是int，所以加起来是8字节
        if(in.readableBytes() < 8){return;}
        //读取出消息类型和消息长度,并且在这之前记录一下读取位置的下标
        in.markReaderIndex();

        MsgType msgType = MsgType.values()[in.readInt()];
        int length = in.readInt();

        //如果之后的可读取长度小于length，说明消息没有收全，需要复位读取下标然后重新读取这条消息
        if(in.readableBytes() < length){
            in.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        /*TankJoinMsg msg = new TankJoinMsg();
        msg.parse(bytes);*/
        Msg msg = null;
        msg = (Msg)Class.forName("net." + msgType.toString()).getDeclaredConstructor().newInstance();
        msg.parse(bytes);
        out.add(msg);
    }
}
