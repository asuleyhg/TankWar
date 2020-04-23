package com.yihg.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {
    public static BufferedImage goodTankL, goodTankR, goodTankU, goodTankD;
    public static BufferedImage enemyTankL, enemyTankR, enemyTankU, enemyTankD;
    public static BufferedImage tankMissile,enemyMissile;


    static {
        try {
            goodTankU = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/p1tankU.gif"));
            goodTankL = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/p1tankL.gif"));
            goodTankD = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/p1tankD.gif"));
            goodTankR = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/p1tankR.gif"));
            enemyTankL = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/enemy1L.gif"));
            enemyTankR = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/enemy1R.gif"));
            enemyTankU = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/enemy1U.gif"));
            enemyTankD = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/enemy1D.gif"));
            tankMissile = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/tankmissile.gif"));
            enemyMissile = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/enemymissile.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
