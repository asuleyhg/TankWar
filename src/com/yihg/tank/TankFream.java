package com.yihg.tank;

import com.yihg.tank.chainofresponsebility.BulletTankCollider;
import com.yihg.tank.chainofresponsebility.BulletWallCollider;
import com.yihg.tank.chainofresponsebility.Collider;

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
    private List<Collider> colliders;

    private TankFream() {
        this.setTitle("tank war");
        this.setLocation(400,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new MyKeyListener());
        myTank = new Player(100,100, Dir.RIGHT, Group.GOOD);

//        bullets = new ArrayList<>();
//        enemyTankList = new ArrayList<>();
//        explodes = new ArrayList<>();
        Wall w = new Wall(300, 200, 400, 50);
        objects = new ArrayList<>();
        for (int i=0; i<5; i++){
            addEnemy(objects);
        }
        objects.add(w);
        initCollider();
    }

    private void initCollider() {
        colliders = new ArrayList<>();
        String[] names = PropertyMgr.get("colliders").split(",");
        try {
            for (String name : names){
                Class clazz = Class.forName("com.yihg.tank.chainofresponsebility." + name);
                Collider c = (Collider)clazz.getConstructor().newInstance();
                colliders.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addEnemy(List<AbstractGameObject> enemyTankList) {
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
        g.drawString("objects:" + objects.size(), 10, 50);
        g.setColor(c);

        if(myTank.getLive()){
            myTank.paint(g);
        }
        objects.removeIf(abstractGameObject -> !abstractGameObject.isLive());
//        for (AbstractGameObject object : objects){
//            object.paint(g);
//        }
        for(int i = 0; i < objects.size(); i++){
            AbstractGameObject go1 = objects.get(i);
            //碰撞检测
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObject go2 = objects.get(j);
                for(Collider collider : colliders){
                    collider.collide(go1, go2);
                }
            }
            if(go1.isLive()){
                go1.paint(g);
            }
        }

//        explodes.removeIf(Explode -> !Explode.getLive());
//        for(Explode explode : explodes){
//            explode.paint(g);
//        }
        // 如果场面上剩余的敌人数不超过3，则生成新的敌人
//        if(enemyTankList.size() < 3){
//            addEnemy(enemyTankList);
//        }
        //删除出界的子弹
//        bullets.removeIf(Bullet -> !Bullet.getLive());
        //TODO 碰撞检测
//        for(Bullet bullet : bullets){
//            //与每一辆敌人坦克作比较
//            for (Tank enemyTank : enemyTankList){
//                //如果碰撞产生，则跳出循环
//                if(bullet.collidesWithTank(enemyTank)){
//                    break;
//                }
//            }
//            bullet.paint(g);
//        }
    }

    Image offScreenImage = null;

//    public void add(Bullet bullet){
//        this.bullets.add(bullet);
//    }

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
