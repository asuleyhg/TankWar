package net;

import com.yihg.tank.Dir;
import com.yihg.tank.Player;
import com.yihg.tank.TankFream;

import java.io.*;
import java.util.UUID;

public class TankStopMsg extends Msg{

//    private int x, y;
    private UUID id;

    /*public int getX() {
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
    }*/


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TankStopMsg{" +
                /*"x=" + x +
                ", y=" + y +*/
                ", id=" + id +
                '}';
    }



    public TankStopMsg() {}

    /*public TankStopMsg(int x, int y, UUID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }*/

    public TankStopMsg(UUID id) {
        this.id = id;
    }

    public byte[] toBytes(){
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            /*dos.writeInt(x);
            dos.writeInt(y);*/
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
            /*this.x = dis.readInt();
            this.y = dis.readInt();*/
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
        //如果消息是自己发的，那就不处理；
        if(this.id.equals(TankFream.INSTANCE.getGm().getMyTank().getId())){return;}

        Player p = (Player) TankFream.INSTANCE.getGm().findByUUID(this.id);

        if(p != null){
            p.setMoving(false);
        }

        /*//如果p不等于null说明找到了这个角色,如果没找到则不做任何处理
        if(p != null){
            p.setX(this.x);
            p.setY(this.y);
            p.setMoving(true);
        }*/

    }

    /**
     * 返回消息的类型
     * @return
     */
    @Override
    public MsgType getMsgType() {
        return MsgType.TankStopMsg;
    }
}
