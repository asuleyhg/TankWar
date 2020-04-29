package com.yihg.tank;

import com.yihg.tank.chainofresponsebility.ColliderChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFream  extends Frame {
    public static final TankFream INSTANCE = new TankFream();
    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
    private Player myTank;
    private List<AbstractGameObject> objects;
    private ColliderChain chain;


    private TankFream() {
        this.setTitle("tank war");
        this.setLocation(300,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new MyKeyListener());
        this.objects = new ArrayList<>();
        initGameObject();
        chain = new ColliderChain();
    }

    /**
     * 添加游戏物体
     */
    private void initGameObject() {
        myTank = new Player(100,100, Dir.RIGHT, Group.GOOD);
        Wall w = new Wall(300, 200, 400, 50);
        this.objects.add(myTank);
        this.objects.add(w);
        for (int i=0; i<5; i++){
            addEnemy(this.objects);
        }
    }


    /**
     * 判断坦克生成的位置上是否已经有了物体，如果没有则把这辆坦克加进容器中等待被显示
     * @param objects
     */
    private void addEnemy(List<AbstractGameObject> objects) {
        Boolean flag = false;
        AbstractGameObject enemy = null;
        while(!flag){
            enemy = createOneEnemy();
            flag = true;
            for(AbstractGameObject go : objects){
                if(enemy.getRect().intersects(go.getRect())){
                    flag = false;
                    break;
                }
            }
        }
        objects.add(enemy);

    }


    /**
     * 随机生成一辆敌军坦克
     * @return
     */
    private Tank createOneEnemy() {
        int randomX = (int) (Math.random() * (GAME_WIDTH - ResourceMgr.enemyTankD.getWidth()));
        int randomY = (int) (Math.random() * (GAME_HEIGHT - 30 - ResourceMgr.enemyTankD.getHeight())) + 30;
         return new Tank(randomX, randomY, Dir.getRandomDir(), Group.ENEMY);
    }

    @Override
    public void paint(Graphics g) {
        //显示当前子弹的数量
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("objects:" + objects.size(), 10, 50);
        g.setColor(c);

        if(myTank.getLive()){
            myTank.paint(g);
        }
        objects.removeIf(abstractGameObject -> !abstractGameObject.isLive());
        for(int i = 0; i < objects.size(); i++){
            AbstractGameObject go1 = objects.get(i);
            //碰撞检测
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObject go2 = objects.get(j);
                if(!chain.collide(go1, go2)) {
                    break;
                }
            }
            if(go1.isLive()){
                go1.paint(g);
            }
        }

    }

    Image offScreenImage = null;


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

    public void add(AbstractGameObject object) {
        this.objects.add(object);
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
