import java.awt.Rectangle;
import java.awt.Graphics;

public class EnemyBullet extends Rectangle {

    int speed = 3;
    int maxY = 720;
    Enemy enemy;

    public EnemyBullet(int xCor, int yCor, Enemy enemy) {
        setBounds(xCor,yCor,5,10);
        this.enemy = enemy;
    }

    public void update() {
        this.y += 1 * speed;
        if (this.y >= maxY) {
            enemy.screen.DestroyEnemyBullet(this);
        }
    }  

    public void render(Graphics graphics) {
        graphics.fillRect(x, y, width, height);
    }

    public Rectangle receiveBounds() {
        return new Rectangle(x,y,width,height);
    }
}