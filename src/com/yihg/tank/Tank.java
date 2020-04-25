package com.yihg.tank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tank {
    // 速度
    public static final int SPEED = 5;
    // 初始位置
    private int x = 100, y = 100;
    //方向
    private Dir dir;
    //方向键是否被按下的变量
    private boolean bU, bD, bL, bR;
    //是否移动
    private boolean moving = false;
    //阵营
    private Group group;

    public Tank(int x, int y, Dir dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }


    public void paint(Graphics g) {
        if(this.group == Group.GOOD){
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
        }else if (this.group == Group.ENEMY){
            switch (dir){
                case LEFT:
                    g.drawImage(ResourceMgr.enemyTankL, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.enemyTankR, x, y, null);
                    break;
                case UP:
                    g.drawImage(ResourceMgr.enemyTankU, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.enemyTankD, x, y, null);
                    break;
            }
        }

        move();
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
        TankFream.INSTANCE.add(new Bullet(x, y, dir, group));
    }
}
