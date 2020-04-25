package com.yihg.tank;

import java.util.Random;

public enum Dir {
    UP, DOWN, LEFT, RIGHT;

    public static Dir getRandomDir(){
        Random random = new Random();
        return Dir.values()[random.nextInt(values().length)];
    }
}
