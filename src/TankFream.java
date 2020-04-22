import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankFream  extends Frame {
    Tank myTank;

    public TankFream() {
        this.setTitle("tank war");
        this.setLocation(400,100);
        this.setSize(800,600);
        this.addKeyListener(new MyKeyListener());
        myTank = new Tank(100,100, Dir.STOP);
    }

    @Override
    public void paint(Graphics g) {
        myTank.paint(g);
    }

    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}
