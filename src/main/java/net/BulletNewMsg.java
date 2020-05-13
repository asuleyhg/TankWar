package net;

import com.yihg.tank.Bullet;
import com.yihg.tank.Dir;
import com.yihg.tank.Group;
import com.yihg.tank.TankFream;

import java.io.*;
import java.util.UUID;

public class BulletNewMsg extends Msg {

    private UUID playerID;
    private UUID id;
    private int x, y;
    private Dir dir;
    private Group group;

    public BulletNewMsg() {
    }

    public BulletNewMsg(Bullet b) {
        this.playerID = b.getPlayerId();
        this.id = b.getId();
        this.x = b.getX();
        this.y = b.getY();
        this.dir = b.getDir();
        this.group = b.getGroup();
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "playerID=" + playerID +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                '}';
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(playerID.getMostSignificantBits());
            dos.writeLong(playerID.getLeastSignificantBits());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());

            dos.flush();
            //写入一个byte数组
            bytes = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(baos != null){
                    baos.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return  bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.playerID = new UUID(dis.readLong(), dis.readLong());
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void handle() {
        if(this.id.equals(TankFream.INSTANCE.getGm().getMyTank().getId())){return;}

        Bullet b = new Bullet(this.playerID, this.x, this.y, this.dir, this.group);
        b.setId(this.id);
        TankFream.INSTANCE.getGm().add(b);

    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNewMsg;
    }
}
