import java.awt.Rectangle;
import java.awt.Graphics;

public class Invinsible extends Rectangle {

    Screen screen;

    public Invinsible(int xCor, Screen screen) {
        setBounds(xCor,0,10,10);

        this.screen = screen;
    }

    public void render(Graphics graphics) {
        graphics.fillRect(x, y, width, height);
    }

    public void update() {
        this.y += 2;

        if (this.y >= 720) {

        }
    }

    public Rectangle receiveBounds() {
        return new Rectangle(x,y,width,height);
    }
}