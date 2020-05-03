package com.yihg.tank;

import java.awt.*;

public class Explode extends AbstractGameObject{
    private int x, y, w, h;
    private Boolean isLive = true;
    //爆炸图片绘画步骤
    private int step = 0;
    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        this.w = ResourceMgr.explodes[0].getWidth();
        this.h = ResourceMgr.explodes[0].getHeight();
    }

    public void paint(Graphics g) {

        g.drawImage(ResourceMgr.explodes[step], x, y, null);
        step++;
        if (step >= ResourceMgr.explodes.length){
            this.die();
        }
    }

    @Override
    public Boolean isLive() {
        return this.getLive();
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);
    }

    private void die() {
        this.isLive = false;
    }
}
