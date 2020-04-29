package com.yihg.tank;

import java.awt.*;
import java.util.Random;

public class Tank extends AbstractGameObject{
    // 速度
    public static final int SPEED = 5;
    // 初始位置
    private int x = 100, y = 100;
    //方向
    private Dir dir;
    //是否移动
    private boolean moving = true;
    //阵营
    private Group group;
    //是否存活
    private Boolean isLive = true;
    //记录出界前的位置
    private int oldX, oldY;
    //坦克宽高
    private int width, height;

    private Rectangle rect;

    private Random random = new Random();

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.dir = dir;
        this.group = group;
        this.width = ResourceMgr.enemyTankD.getWidth();
        this.height = ResourceMgr.enemyTankD.getHeight();

        this.rect = new Rectangle(x, y, width, height);
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g) {
        switch (dir) {
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
        move();
    }

    @Override
    public Boolean isLive() {
        return getLive();
    }

    private void move() {
        if (!moving) {
            return;
        }
        //记录出界前的位置
        this.oldX = x;
        this.oldY = y;
        switch (dir) {
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
        boundsCheck();
        //每次move完了之后改变方向
        randomDir();
        //随机开火
        if(random.nextInt(100) > 90){
            fire();
        }
        //移动完之后更新坦克方块的坐标
        rect.x = x;
        rect.y = y;
    }

    private void randomDir() {
        if(random.nextInt(100) > 90){
            this.dir = Dir.getRandomDir();
        }
    }


    private void fire() {
        TankFream.INSTANCE.add(new Bullet(x + ResourceMgr.goodTankD.getWidth() / 2 - ResourceMgr.tankMissile.getWidth() / 2
                , y + ResourceMgr.goodTankD.getHeight() / 2 - ResourceMgr.tankMissile.getHeight() / 2
                , dir, group));
    }

    //出界检查
    private void boundsCheck() {
        if (x < 0 || x > TankFream.GAME_WIDTH - width || y < 30 || y + height > TankFream.GAME_HEIGHT) {
            this.back();
        }
    }

    public void back() {
        this.x = oldX;
        this.y = oldY;
    }

    public void die() {
        this.isLive = false;
        TankFream.INSTANCE.add(new Explode(x, y));
    }

    public Rectangle getRect(){
        return this.rect;
    }
}
