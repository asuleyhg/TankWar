package com.yihg.tank.strategy;

import com.yihg.tank.*;

public class FourDirFireStrategy implements FireStrategy{

    @Override
    public void fire(Player player) {

        Dir[] dirs = Dir.values();
        for(Dir d : dirs){
            TankFream.INSTANCE.getGm().add(new Bullet(player.getX() + ResourceMgr.goodTankD.getWidth() / 2 - ResourceMgr.tankMissile.getWidth() / 2
                    , player.getY() + ResourceMgr.goodTankD.getHeight() / 2 - ResourceMgr.tankMissile.getHeight() / 2
                    , d, player.getGroup()));
        }
    }
}
