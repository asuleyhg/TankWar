package com.yihg.tank;

import java.awt.*;

public class Bullet {
    private int x, y;
    private Dir dir;
    public static int SPEED = 6;
    private Group group;
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

    private void boundsCheck() {
        if (x < 0 || x > TankFream.GAME_WIDTH || y < 30 || y > TankFream.GAME_HEIGHT) {
            isLive = false;
        }
    }


}
