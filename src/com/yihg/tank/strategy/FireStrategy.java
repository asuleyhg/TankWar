package com.yihg.tank.strategy;

import com.yihg.tank.AbstractGameObject;
import com.yihg.tank.Player;

public interface FireStrategy {
    void fire(Player player);
}
