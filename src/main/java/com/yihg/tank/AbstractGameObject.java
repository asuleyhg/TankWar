package com.yihg.tank;

import java.awt.*;
import java.io.Serializable;

public abstract class  AbstractGameObject implements Serializable {
    public abstract void paint(Graphics g);
    public abstract Boolean isLive();
    public abstract Rectangle getRect();
}
