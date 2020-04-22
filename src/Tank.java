import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    // 初始位置
    private int x = 100, y = 100;
    // 速度
    public static final int SPEED = 5;
    //方向
    private Dir dir;
    //方向键是否被按下的变量
    private boolean bU, bD, bL, bR;
    //是否移动
    private boolean moving = false;

    public Tank(int x, int y, Dir dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }


    public void paint(Graphics g) {
        g.fillRect(x, y, 50, 50);
        move();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_W :
                bU = true;
                break;
            case KeyEvent.VK_A :
                bL = true;
                break;
            case KeyEvent.VK_S :
                bD = true;
                break;
            case KeyEvent.VK_D :
                bR = true;
                break;
        }
        setMainDir();
    }

    private void setMainDir() {
        if(!bR && !bD && !bL && !bU){
            moving = false;
        }else {
            moving = true;
            if(bR && !bD && !bL && !bU){
                dir = Dir.RIGHT;
            }
            if(!bR && bD && !bL && !bU){
                dir = Dir.DOWN;
            }
            if(!bR && !bD && bL && !bU){
                dir = Dir.LEFT;
            }
            if(!bR && !bD && !bL && bU){
                dir = Dir.UP;
            }
            if(!bR && !bD && bL && bU){
                dir = Dir.UP_LEFT;
            }
            if(bR && !bD && !bL && bU){
                dir = Dir.UP_RIGHT;
            }
            if(!bR && bD && bL && !bU){
                dir = Dir.DOWN_LEFT;
            }
            if(bR && bD && !bL && !bU){
                dir = Dir.DOWN_RIGHT;
            }
        }
    }

    private void move() {
        if(!moving){
            return;
        }
        switch (dir){
            case UP:
                y -= SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP_LEFT:
                x -= SPEED;
                y -= SPEED;
                break;
            case UP_RIGHT:
                x += SPEED;
                y -= SPEED;
                break;
            case DOWN_LEFT:
                x -= SPEED;
                y += SPEED;
                break;
            case DOWN_RIGHT:
                x += SPEED;
                y += SPEED;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_W :
                bU = false;
                break;
            case KeyEvent.VK_A :
                bL = false;
                break;
            case KeyEvent.VK_S :
                bD = false;
                break;
            case KeyEvent.VK_D :
                bR = false;
                break;
        }
        setMainDir();
    }
}
