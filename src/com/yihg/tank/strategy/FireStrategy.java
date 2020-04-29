package com.yihg.tank.strategy;

import com.yihg.tank.Player;

import java.io.Serializable;

public interface FireStrategy extends Serializable {
    void fire(Player player);
}
