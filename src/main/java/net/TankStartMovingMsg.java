package net;

import com.yihg.tank.Dir;
import com.yihg.tank.Group;
import com.yihg.tank.Player;
import com.yihg.tank.TankFream;

import java.io.*;
import java.util.UUID;

public class TankStartMovingMsg extends Msg{

    private int x, y;
    private Dir dir;
    private UUID id;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TankStartMovingMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", id=" + id +
                '}';
    }



    public TankStartMovingMsg() {}

    public TankStartMovingMsg(int x, int y, Dir dir, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.id = id;
    }

    public byte[] toBytes(){
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
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

    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
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



    /**
     * 消息发出之后的处理逻辑
     */
    public void handle() {
        /*//如果消息是自己发的，那就不处理；
        if(this.id.equals(TankFream.INSTANCE.getGm().getMyTank().getId())){return;}
        if(TankFream.INSTANCE.getGm().findByUUID(this.id) != null){return;}

        Player p = new Player(this);

        TankFream.INSTANCE.getGm().add(p);

        //每次接收到消息之后，把自己重新发一遍给所有人
        Client.INSTANCE.send(new TankStartMovingMsg(TankFream.INSTANCE.getGm().getMyTank()));*/


    }

    /**
     * 返回消息的类型
     * @return
     */
    @Override
    public MsgType getMsgType() {
        return MsgType.TankStartMovingMsg;
    }
}
