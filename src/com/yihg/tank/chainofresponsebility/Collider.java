package com.yihg.tank.chainofresponsebility;

import com.yihg.tank.AbstractGameObject;

public interface Collider {
    public void collide(AbstractGameObject go1, AbstractGameObject go2);
}
