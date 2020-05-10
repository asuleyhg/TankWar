import com.yihg.tank.Dir;
import com.yihg.tank.Group;
import com.yihg.tank.Player;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import net.MsgEncoder;
import net.TankJoinMsg;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CodecTest {

    @Test
    public void encoderTest(){
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());

        Player p = new Player(50, 100, Dir.RIGHT, Group.GOOD);
        TankJoinMsg msg = new TankJoinMsg(p);

        channel.writeOutbound(msg);
        ByteBuf buf = channel.readOutbound();

        int length = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group group = Group.values()[buf.readInt()];
        UUID id = new UUID(buf.readLong(), buf.readLong());

        assertEquals(33, length);
        assertEquals(50, x);
        assertEquals(100, y);
        assertEquals(Dir.RIGHT, dir);
        assertFalse(moving);
        assertEquals(Group.GOOD, group);
        assertEquals(p.getId(), id);

    }
}
