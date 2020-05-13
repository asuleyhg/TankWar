package com.yihg.tank;

import com.yihg.tank.strategy.FireStrategy;
import net.Client;
import net.TankJoinMsg;
import net.TankMoveMsg;
import net.TankStopMsg;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.UUID;

public class Player extends AbstractGameObject{
    // 速度
    public static final int SPEED = 5;

    //坦克ID
    private UUID id;
    // 初始位置
    private int x, y, w, h;
    //方向
    private Dir dir;
    //方向键是否被按下的变量
    private boolean bU, bD, bL, bR;
    //是否移动
    private boolean moving = false;
    //开火策略
    private FireStrategy strategy;
    //阵营
    private Group group;
    //是否存活
    private Boolean isLive = true;
    //坦克方块
    private Rectangle rect;

    public Player(int x, int y, Dir dir, Group group){
        this.x = x;
        this.y = y;
        this.w = ResourceMgr.goodTankL.getWidth();
        this.h = ResourceMgr.goodTankL.getHeight();
        this.dir = dir;
        this.group = group;
        this.id = UUID.randomUUID();
        this.rect = new Rectangle(x, y, w, h);
        initFireStrategy();
    }

    public Player(TankJoinMsg msg) {
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.group = msg.getGroup();
        this.id = msg.getId();
        this.w = ResourceMgr.goodTankL.getWidth();
        this.h = ResourceMgr.goodTankL.getHeight();

        this.rect = new Rectangle(x, y, w, h);
        initFireStrategy();
    }

    private void initFireStrategy() {
        String className = PropertyMgr.get("fireStrategy");
        try {
            Class clazz = Player.class.getClassLoader().loadClass("com.yihg.tank.strategy." + className);
            this.strategy = (FireStrategy) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

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

    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(), x, y-10);
        g.setColor(c);
            switch (dir){
                case LEFT:
                    g.drawImage(ResourceMgr.goodTankL, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.goodTankR, x, y, null);
                    break;
                case UP:
                    g.drawImage(ResourceMgr.goodTankU, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.goodTankD, x, y, null);
                    break;
            }

        move();
    }

    @Override
    public Boolean isLive() {
        return this.getLive();
    }

    @Override
    public Rectangle getRect() {
        return rect;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_W :
                bU = true;
                break;
            case KeyEvent.VK_A :
                bL = true;
                break;
            case KeyEvent.VK_S :
                bD = true;
                break;
            case KeyEvent.VK_D :
                bR = true;
                break;
        }
        setMainDir();
    }

    private void setMainDir() {
        //记录开始是否移动的标记
        boolean oldMoving = moving;
        Dir oldDir = this.dir;
        if(!bR && !bD && !bL && !bU){
            moving = false;
            Client.INSTANCE.send(new TankStopMsg(this.id));
        }else {
            moving = true;
            if(bR && !bD && !bL && !bU){
                dir = Dir.RIGHT;
            }
            if(!bR && bD && !bL && !bU){
                dir = Dir.DOWN;
            }
            if(!bR && !bD && bL && !bU){
                dir = Dir.LEFT;
            }
            if(!bR && !bD && !bL && bU){
                dir = Dir.UP;
            }
            //如果一开始是静止状态，现在改为移动状态了，需要发送这个消息；如果已经移动了则不发送消息
            if(!oldMoving){
                Client.INSTANCE.send(new TankMoveMsg(x, y, dir, id));
            }
            //当方向改变的时候也要发送移动的消息
            if(!oldDir.equals(this.dir)){
                Client.INSTANCE.send(new TankMoveMsg(x, y, dir, id));
            }

        }
    }

    private void move() {
         if(!moving){
            return;
        }
        switch (dir){
            case UP:
                y -= SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
        }
        //移动后更新坦克坐标
        this.rect.x = x;
        this.rect.y = y;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_W :
                bU = false;
                break;
            case KeyEvent.VK_A :
                bL = false;
                break;
            case KeyEvent.VK_S :
                bD = false;
                break;
            case KeyEvent.VK_D :
                bR = false;
                break;
            case KeyEvent.VK_J :
                fire();
                break;
        }
        setMainDir();
    }

    private void fire() {
        strategy.fire(this);
    }

    public void die() {
        this.isLive = false;
    }

}
