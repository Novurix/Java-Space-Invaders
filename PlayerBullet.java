import java.awt.Rectangle;
import java.awt.Graphics;

public class PlayerBullet extends Rectangle {
    Screen screen;
    int speed = 3;

    public PlayerBullet(int xCor, int yCor, Screen screen) {
        this.screen = screen;
        setBounds(xCor,yCor,5,10);
    }   

    public void render(Graphics graphics) {
        graphics.fillRect(x, y, width, height);
    }

    public void update() {
        this.y -= 1 * speed;
    }

    public Rectangle receiveBounds() {
        return new Rectangle(x,y,width,height);
    }
}