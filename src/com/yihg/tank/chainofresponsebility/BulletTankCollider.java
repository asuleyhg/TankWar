package com.yihg.tank.chainofresponsebility;

import com.yihg.tank.AbstractGameObject;
import com.yihg.tank.Bullet;
import com.yihg.tank.Tank;

public class BulletTankCollider implements Collider {
    @Override
    public Boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Bullet && go2 instanceof Tank){
            Bullet b = (Bullet)go1;
            Tank t = (Tank)go2;
            //如果物体已经死了就不再继续判断
            if(!b.isLive() || !t.isLive()){
                return false;
            }
            //如果阵营相同，则不作碰撞判断
            if(b.getGroup() == t.getGroup()) {
                return true;
            }
            if(b.getRect().intersects(t.getRect())){
                b.die();
                t.die();
                return false;
            }
        }else if(go1 instanceof Tank && go2 instanceof Bullet){
            return collide(go2, go1);
        }
        return true;
    }
}
