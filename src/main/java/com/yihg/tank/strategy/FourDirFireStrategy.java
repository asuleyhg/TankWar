package com.yihg.tank.strategy;

import com.yihg.tank.*;
import net.BulletNewMsg;
import net.Client;

public class FourDirFireStrategy implements FireStrategy{

    @Override
    public void fire(Player player) {

        Dir[] dirs = Dir.values();
        for(Dir d : dirs){
            Bullet b = new Bullet(player.getId(), player.getX() + ResourceMgr.goodTankD.getWidth() / 2 - ResourceMgr.tankMissile.getWidth() / 2
                    , player.getY() + ResourceMgr.goodTankD.getHeight() / 2 - ResourceMgr.tankMissile.getHeight() / 2
                    , d, player.getGroup());
            TankFream.INSTANCE.getGm().add(b);
            Client.INSTANCE.send(new BulletNewMsg(b));
        }
    }
}
