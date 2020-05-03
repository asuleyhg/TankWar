package com.yihg.tank;

public class Main {
    public static void main(String[] args) {
        TankFream frame = TankFream.INSTANCE;
        frame.setVisible(true);

        while(true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             frame.repaint();
        }
    }
}
