package com.yihg.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {
    public static BufferedImage goodTankL, goodTankR, goodTankU, goodTankD;

    static {
        try {
            goodTankU = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/p1tankU.gif"));
            goodTankL = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/p1tankL.gif"));
            goodTankD = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/p1tankD.gif"));
            goodTankR = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/p1tankR.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
