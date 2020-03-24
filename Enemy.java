import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends Rectangle {

    int directionY = 1;
    int speed = 2;
    int maxY = 720;
    boolean canCollide = true, isAlive = true;

    Screen screen;
    EnemyBullet bullet;

    int shootTimer = 0, maxShootTimer = 75;

    public Enemy(int xCor, int yCor, Screen screen) {
        setBounds(xCor,yCor,15,15);
        this.screen = screen;
    }

    public void update() {

        if (isAlive) {
            this.y += directionY * speed;

            if (this.y >= maxY) {
                this.y = 0;
                Random randomX = new Random();
                int randomXPos = randomX.nextInt(999);
                this.x = randomXPos+1;
            }
            shootTimer++;

            if (shootTimer >= maxShootTimer) {
                shootTimer = 0;
                int bulletPosX = x + 15/3, bulletPosY = y;

                bullet = new EnemyBullet(bulletPosX, bulletPosY, this);
                screen.CreateEnemyBullet(bullet);
            }
        }
        else {
            screen.DestroyEnemy(this);
        }
    }

    public void render(Graphics graphics) {
        graphics.fillRect(x, y, width, height);
    }

    public Rectangle receiveBounds() {
        return new Rectangle(x,y,width,height);
    }
}