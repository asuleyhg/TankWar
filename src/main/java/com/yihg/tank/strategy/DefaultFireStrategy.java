package com.yihg.tank.strategy;

import com.yihg.tank.*;
import net.BulletNewMsg;
import net.Client;

public class DefaultFireStrategy implements FireStrategy {

    @Override
    public void fire(Player player) {

        Bullet b = new Bullet(player.getId(), player.getX() + ResourceMgr.goodTankD.getWidth() / 2 - ResourceMgr.tankMissile.getWidth() / 2
                , player.getY() + ResourceMgr.goodTankD.getHeight() / 2 - ResourceMgr.tankMissile.getHeight() / 2
                , player.getDir(), player.getGroup());
        TankFream.INSTANCE.getGm().add(b);
        //当一颗子弹产生的时候，就需要发送这颗子弹产生的消息
        Client.INSTANCE.send(new BulletNewMsg(b));
    }
}
