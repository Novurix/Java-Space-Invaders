import java.awt.Rectangle;
import java.awt.Graphics;

public class Player extends Rectangle {
    Screen screen;
    int xDir, yDir;
    int speed = 2, invinsibleTimer = 0;

    boolean isInvinsible = false;

    boolean isAlive = true;

    public Player(Screen screen) {
        this.screen = screen;
        
        int xCor = screen.window.getWidth()/2, yCor =  screen.window.getHeight()/2;
        setBounds(xCor,yCor,15,15);
    }

    public void render(Graphics graphics) {
        graphics.fillRect(x, y, width, height);
    }

    public void update() {

        if (isAlive) {
            this.x -= xDir * speed;
            this.y -= yDir * speed;
        }

        if (isInvinsible) {
            invinsibleTimer++;
            if (invinsibleTimer >= 500) {
                isInvinsible = false;
                invinsibleTimer = 0;
            }
        }
    }

    public void ChangeX(int x) {
        xDir = x;
    }
    public void ChangeY(int y) {
        yDir = y;
    }

    public Rectangle receiveBounds() {
        return new Rectangle(x,y,width,height);
    }
}