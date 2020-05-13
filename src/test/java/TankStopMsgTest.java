import com.yihg.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import net.*;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankStopMsgTest {

    @Test
    public void encoderTest(){
        //测试用的虚拟的管道
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());

        UUID randomID = UUID.randomUUID();

        TankStopMsg msg = new TankStopMsg(randomID);

        //往外写一条消息
        channel.writeOutbound(msg);
        //读取一下写出去的消息
        ByteBuf buf = channel.readOutbound();

        MsgType msgType = MsgType.values()[buf.readInt()];

        int length = buf.readInt();
        UUID id = new UUID(buf.readLong(), buf.readLong());

        assertEquals(MsgType.TankStopMsg, msgType);
        assertEquals(16, length);
        assertEquals(randomID, id);

    }

    @Test
    public void decodeTest(){

        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStopMsg.ordinal());
        buf.writeInt(16);
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());

        channel.writeInbound(buf);

        TankStopMsg msg = channel.readInbound();
        assertEquals(id, msg.getId());
    }
}
