package com.yihg.tank;

import java.awt.*;

public class Explode {
    private int x, y;
    //子弹是否存活，false时表示需要删除这颗子弹
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
    }

    public void paint(Graphics g) {

        g.drawImage(ResourceMgr.explodes[step], x, y, null);
        step++;
        if (step >= ResourceMgr.explodes.length){
            this.die();
        }
    }
    private void die() {
        this.isLive = false;
    }
}
