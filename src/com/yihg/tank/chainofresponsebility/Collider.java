package com.yihg.tank.chainofresponsebility;

import com.yihg.tank.AbstractGameObject;

public interface Collider {
    //返回true则继续往下判断，返回false说明已经撞上了就不需要再往下判断了
    Boolean collide(AbstractGameObject go1, AbstractGameObject go2);
}
