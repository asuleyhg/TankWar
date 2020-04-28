package com.yihg.tank;

import com.yihg.tank.chainofresponsebility.Collider;

import java.awt.*;

public class Wall extends AbstractGameObject{
    private int x, y, w, h;
    private Rectangle rect;
    private Boolean isLive = true;
    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }



    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        rect = new Rectangle(x, y, w, h);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }

    @Override
    public Boolean isLive() {
        return this.getLive();
    }

    private void die() {
        this.isLive = false;
    }

    public Rectangle getRect() {
        return this.rect;
    }

}
