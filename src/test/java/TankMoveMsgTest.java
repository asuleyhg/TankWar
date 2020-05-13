import com.yihg.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import net.MsgDecoder;
import net.MsgEncoder;
import net.MsgType;
import net.TankMoveMsg;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TankMoveMsgTest {

    @Test
    public void encoderTest(){
        //测试用的虚拟的管道
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());

        TankMoveMsg msg = new TankMoveMsg(50, 100, Dir.RIGHT, UUID.randomUUID());

        //往外写一条消息
        channel.writeOutbound(msg);
        //读取一下写出去的消息
        ByteBuf buf = channel.readOutbound();

        MsgType msgType = MsgType.values()[buf.readInt()];

        int length = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        UUID id = new UUID(buf.readLong(), buf.readLong());

        assertEquals(MsgType.TankMoveMsg, msgType);
        assertEquals(28, length);
        assertEquals(50, x);
        assertEquals(100, y);
        assertEquals(Dir.RIGHT, dir);

    }

    @Test
    public void decodeTest(){

        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());


        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankMoveMsg.ordinal());
        buf.writeInt(28);
        buf.writeInt(50);
        buf.writeInt(100);
        buf.writeInt(Dir.RIGHT.ordinal());
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());


        channel.writeInbound(buf);

        TankMoveMsg msg = channel.readInbound();
        assertEquals(50, msg.getX());
        assertEquals(100, msg.getY());
        assertEquals(Dir.RIGHT, msg.getDir());
        assertEquals(id, msg.getId());
    }
}
