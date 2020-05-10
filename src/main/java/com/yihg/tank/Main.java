package com.yihg.tank;

import net.Client;

public class Main {
    public static void main(String[] args) {
        TankFream.INSTANCE.setVisible(true);

        //将重画UI的工作放到一个线程中，这样不会影响client的连接
        new Thread(()->{
            while(true) {
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TankFream.INSTANCE.repaint();
            }
        }).start();
        Client.INSTANCE.connect();
    }
}
