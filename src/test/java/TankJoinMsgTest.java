import com.yihg.tank.Dir;
import com.yihg.tank.Group;
import com.yihg.tank.Player;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import net.MsgDecoder;
import net.MsgEncoder;
import net.MsgType;
import net.TankJoinMsg;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TankJoinMsgTest {

    @Test
    public void encoderTest(){
        //测试用的虚拟的管道
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());

        Player p = new Player(50, 100, Dir.RIGHT, Group.GOOD);
        TankJoinMsg msg = new TankJoinMsg(p);

        //往外写一条消息
        channel.writeOutbound(msg);
        //读取一下写出去的消息
        ByteBuf buf = channel.readOutbound();

        MsgType msgType = MsgType.values()[buf.readInt()];

        int length = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group group = Group.values()[buf.readInt()];
        UUID id = new UUID(buf.readLong(), buf.readLong());

        assertEquals(MsgType.TankJoinMsg, msgType);
        assertEquals(33, length);
        assertEquals(50, x);
        assertEquals(100, y);
        assertEquals(Dir.RIGHT, dir);
        assertFalse(moving);
        assertEquals(Group.GOOD, group);
        assertEquals(p.getId(), id);

    }

    @Test
    public void decodeTest(){

        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());


        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankJoinMsg.ordinal());
        buf.writeInt(33);
        buf.writeInt(50);
        buf.writeInt(100);
        buf.writeInt(Dir.RIGHT.ordinal());
        buf.writeBoolean(true);
        buf.writeInt(Group.GOOD.ordinal());
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());


        channel.writeInbound(buf);

        TankJoinMsg msg = channel.readInbound();
        assertEquals(50, msg.getX());
        assertEquals(100, msg.getY());
        assertEquals(Dir.RIGHT, msg.getDir());
        assertTrue(msg.getMoving());
        assertEquals(Group.GOOD, msg.getGroup());
        assertEquals(id, msg.getId());
    }
}
