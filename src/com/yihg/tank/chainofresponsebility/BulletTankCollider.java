package com.yihg.tank.chainofresponsebility;

import com.yihg.tank.AbstractGameObject;
import com.yihg.tank.Bullet;
import com.yihg.tank.Tank;

public class BulletTankCollider implements Collider {
    @Override
    public void collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Bullet && go2 instanceof Tank){
            Bullet b = (Bullet)go1;
            Tank t = (Tank)go2;
            b.collidesWithTank(t);
        }else if(go1 instanceof Tank && go2 instanceof Bullet){
            collide(go2, go1);
        }
    }
}
