package com.yihg.tank;

import java.awt.*;

public class Bullet extends AbstractGameObject{
    private int x, y;
    private int width, height;
    private Rectangle rect;
    private Dir dir;
    public static int SPEED = 6;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private Group group;
    //子弹是否存活，false时表示需要删除这颗子弹
    private Boolean isLive = true;
    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }



    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.width = ResourceMgr.tankMissile.getWidth();
        this.height = ResourceMgr.tankMissile.getHeight();
        this.rect = new Rectangle(x, y, width, height);
    }

    public void paint(Graphics g) {
        switch (group) {
            case GOOD:
                g.drawImage(ResourceMgr.tankMissile, x, y, null);
                break;
            case ENEMY:
                g.drawImage(ResourceMgr.enemyMissile, x, y, null);
                break;
        }
        move();
        //移动之后更新子弹方块的坐标
        rect.x = x;
        rect.y = y;
    }

    @Override
    public Boolean isLive() {
        return this.getLive();
    }

    public void die() {
        this.isLive = false;
    }

    private void move() {
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
    }

    //出界检查
    private void boundsCheck() {
        if (x < 0 || x > TankFream.GAME_WIDTH || y < 30 || y > TankFream.GAME_HEIGHT) {
            isLive = false;
        }
    }

    public Rectangle getRect(){
        return rect;
    }


}
