package com.yihg.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFream  extends Frame {
    public static final TankFream INSTANCE = new TankFream();
    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
    private Tank myTank;
    private Tank enemyTank;
    private List<Bullet> bullets;

    private TankFream() {
        this.setTitle("tank war");
        this.setLocation(400,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new MyKeyListener());
        myTank = new Tank(100,100, Dir.RIGHT, Group.GOOD);
        enemyTank = new Tank(300, 300, Dir.UP, Group.ENEMY);
        bullets = new ArrayList<>();
    }

    @Override
    public void paint(Graphics g) {
        //显示当前子弹的数量
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("bullet:" + bullets.size(), 10, 50);
        g.setColor(c);

        myTank.paint(g);
        enemyTank.paint(g);
        //删除出界的子弹
        bullets.removeIf(Bullet -> !Bullet.getLive());
        for(Bullet bullet : bullets){
            bullet.paint(g);
        }
    }

    Image offScreenImage = null;

    public void add(Bullet bullet){
        this.bullets.add(bullet);
    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}
