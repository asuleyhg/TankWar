package com.yihg.tank.strategy;

import com.yihg.tank.*;

public class DefaultFireStrategy implements FireStrategy {

    @Override
    public void fire(Player player) {

        TankFream.INSTANCE.getGm().add(new Bullet(player.getX() + ResourceMgr.goodTankD.getWidth() / 2 - ResourceMgr.tankMissile.getWidth() / 2
                , player.getY() + ResourceMgr.goodTankD.getHeight() / 2 - ResourceMgr.tankMissile.getHeight() / 2
                , player.getDir(), player.getGroup()));
    }
}
