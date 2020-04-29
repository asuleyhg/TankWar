package com.yihg.tank;

import com.yihg.tank.strategy.FireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends AbstractGameObject{
    // 速度
    public static final int SPEED = 5;
    // 初始位置
    private int x = 100, y = 100;

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    //方向
    private Dir dir;
    //方向键是否被按下的变量
    private boolean bU, bD, bL, bR;
    //是否移动
    private boolean moving = false;
    //开火策略
    private FireStrategy strategy;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    //阵营
    private Group group;
    //是否存活
    private Boolean isLive = true;

    public Player(int x, int y, Dir dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
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
        if(!bR && !bD && !bL && !bU){
            moving = false;
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
