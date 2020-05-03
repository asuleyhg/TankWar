package com.yihg.tank.chainofresponsebility;

import com.yihg.tank.AbstractGameObject;
import com.yihg.tank.Bullet;
import com.yihg.tank.Tank;
import com.yihg.tank.Wall;

public class TankTankCollider implements Collider {
    @Override
    public Boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 == go2){return true;}
        if(go1 instanceof Tank && go2 instanceof Tank){
            Tank t1 = (Tank)go1;
            Tank t2 = (Tank)go2;
            if(t1.isLive() && t2.isLive()){
                if(t1.getRect().intersects(t2.getRect())){
                    t1.back();
                    t2.back();
                }
            }
        }
        return true;
    }
}
