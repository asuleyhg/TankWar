package com.yihg.tank;

import com.yihg.tank.chainofresponsebility.ColliderChain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GameModel implements Serializable {

    private Player myTank;
    private List<AbstractGameObject> objects;
    private ColliderChain chain;

    public Player getMyTank() {
        return myTank;
    }

    public void setMyTank(Player myTank) {
        this.myTank = myTank;
    }
    public GameModel(){
        this.objects = new ArrayList<>();
        initGameObject();
        chain = new ColliderChain();
    }

    Random random = new Random();
    /**
     * 添加游戏物体
     */
    private void initGameObject() {

        myTank = new Player(50 + random.nextInt(TankFream.INSTANCE.GAME_WIDTH - ResourceMgr.enemyTankD.getWidth()),
                50 + random.nextInt(TankFream.INSTANCE.GAME_HEIGHT - ResourceMgr.enemyTankD.getHeight()),
                Dir.getRandomDir(), Group.values()[random.nextInt(Group.values().length)]);
        Wall w = new Wall(300, 200, 400, 50);
        this.objects.add(myTank);
//        this.objects.add(w);
//        for (int i=0; i<5; i++){
//            addEnemy(this.objects);
//        }
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
        int randomX = (int) (Math.random() * (TankFream.INSTANCE.GAME_WIDTH - ResourceMgr.enemyTankD.getWidth()));
        int randomY = (int) (Math.random() * (TankFream.INSTANCE.GAME_HEIGHT - 30 - ResourceMgr.enemyTankD.getHeight())) + 30;
        return new Tank(randomX, randomY, Dir.getRandomDir(), Group.ENEMY);
    }

    public void paint(Graphics g) {
        //显示当前子弹的数量
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("objects:" + objects.size(), 10, 50);
        g.setColor(c);
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

    public void add(AbstractGameObject object) {
        this.objects.add(object);
    }

    /**
     * 根据UUID查找游戏物体
     * @param id
     * @return
     */
    public AbstractGameObject findByUUID(UUID id) {

        for(AbstractGameObject o : this.objects){
            Player p = (Player)o;
            if(id.equals(p.getId())){
                return p;
            }

        }
        return null;
    }
}
