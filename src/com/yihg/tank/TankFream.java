package com.yihg.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFream  extends Frame {
    public static final TankFream INSTANCE = new TankFream();
    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
    private Player myTank;
    private List<Tank> enemyTankList = new ArrayList<>();
    private List<Bullet> bullets;

    private TankFream() {
        this.setTitle("tank war");
        this.setLocation(400,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new MyKeyListener());
        myTank = new Player(100,100, Dir.RIGHT, Group.GOOD);
        bullets = new ArrayList<>();
    }

    private void addEnemy(List<Tank> enemyTankList) {
        enemyTankList.add(
                new Tank((int) (Math.random() * (GAME_WIDTH - ResourceMgr.enemyTankD.getWidth()))
                        , (int) (Math.random() * (GAME_HEIGHT - 30 - ResourceMgr.enemyTankD.getHeight())) + 30
                        , Dir.UP, Group.ENEMY));
    }

    @Override
    public void paint(Graphics g) {
        //显示当前子弹的数量
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("bullet:" + bullets.size(), 10, 50);
        g.setColor(c);

        if(myTank.getLive()){
            myTank.paint(g);
        }
        enemyTankList.removeIf(Tank -> !Tank.getLive());
        for (Tank enemyTank : enemyTankList){
            enemyTank.paint(g);
        }
        //如果场面上剩余的敌人数不超过3，则生成新的敌人
        if(enemyTankList.size() < 3){
            addEnemy(enemyTankList);
        }
        //删除出界的子弹
        bullets.removeIf(Bullet -> !Bullet.getLive());
        for(Bullet bullet : bullets){
            //与每一辆敌人坦克作比较
            for (Tank enemyTank : enemyTankList){
                //如果碰撞产生，则跳出循环
                if(bullet.collidesWithTank(enemyTank)){
                    break;
                }
            }
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
