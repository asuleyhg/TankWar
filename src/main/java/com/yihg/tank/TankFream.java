package com.yihg.tank;

import com.yihg.tank.chainofresponsebility.ColliderChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TankFream  extends Frame {
    public static final TankFream INSTANCE = new TankFream();
    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;



    private GameModel gm;

    public GameModel getGm() {
        return gm;
    }

    public void setGm(GameModel gm) {
        this.gm = gm;
    }


    private TankFream() {
        this.setTitle("tank war");
        this.setLocation(300,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new MyKeyListener());
        this.gm = new GameModel();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    Image offScreenImage = null;


    @Override
    public void update(Graphics g) {
        if (offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }


    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_F9){
                saveGame();
            }else if(key == KeyEvent.VK_F12){
                loadGame();
            }
            gm.getMyTank().keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gm.getMyTank().keyReleased(e);
        }
    }

    /**
     * 保存游戏进度
     */
    private void saveGame() {
        ObjectOutputStream oos = null;
        try {
            File f = new File("d:/test/tank.bat");
            FileOutputStream fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(gm);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取游戏进度
     */
    private void loadGame() {
        ObjectInputStream ois = null;
        try {
            File f = new File("d:/test/tank.bat");
            FileInputStream fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            this.gm = (GameModel) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
