package com.yihg.tank.chainofresponsebility;

import com.yihg.tank.AbstractGameObject;
import com.yihg.tank.Player;
import com.yihg.tank.Tank;

public class PlayerTankCollider implements Collider {
    @Override
    public Boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 == go2){return true;}
        if(go1 instanceof Player && go2 instanceof Tank){
            Player p = (Player) go1;
            Tank t = (Tank)go2;
            if(p.isLive() && t.isLive()){
                if(p.getRect().intersects(t.getRect())){
                    p.setMoving(false);
                    t.back();
                }
            }
        }
        return true;
    }
}
