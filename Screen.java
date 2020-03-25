import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Random;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.*;

public class Screen extends JPanel implements ActionListener, MouseListener, KeyListener {

    /* -------------------------------------------------------------------------------------- */

    /* VARIABLES */

    /* -------------------------------------------------------------------------------------- */

    Color playerColor = new Color(36, 193, 237);
    Color enemyColor = new Color(235, 70, 70);

    Background background;
    Player player;

    int playerHealth = 100;

    Enemy[] enemies = new Enemy[10000];
    EnemyBullet[] enemyBullets = new EnemyBullet[10000];
    PlayerBullet[] playerBullets = new PlayerBullet[10000];

    Invinsible[] invinsible = new Invinsible[10000];

    int timerToSpawn = 0, timerToSpawnLimit = 300;

    Window window;
    Timer timer = new Timer(10,this);

    public Screen(Window window) {
        this.window = window;
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);

        background = new Background(this);
        player = new Player(this);

        timer.start();
    }

    /* -------------------------------------------------------------------------------------- */

    /* RENDERING */

    /* -------------------------------------------------------------------------------------- */

    public void paint(Graphics graphics) {
        Graphics backgroundGraphics = graphics;
        backgroundGraphics.setColor(Color.black);
        background.render(backgroundGraphics);

        Graphics playerGraphics = graphics;

        if (player.isAlive) {
            if (player.isInvinsible) {
                playerGraphics.setColor(new Color(250, 191, 42));
                player.render(playerGraphics);
            }
            else {
                playerGraphics.setColor(playerColor);
                player.render(playerGraphics);
            }
        }

        for (int i = 0; i < invinsible.length; i++) {
            Graphics invinsibleGraphics = graphics;
            invinsibleGraphics.setColor(new Color(250, 191, 42));

            if (invinsible[i] != null) {
                invinsible[i].render(invinsibleGraphics);
            }
        }

        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] != null) {

                Graphics enemyGraphics = graphics;
                enemyGraphics.setColor(enemyColor);
                enemies[i].render(enemyGraphics);
            }
        }
        
        for (int i = 0; i < enemyBullets.length; i++) {
            if (enemyBullets[i] != null) {
                Graphics enemyBulletGraphics = graphics;
                enemyBulletGraphics.setColor(enemyColor);

                enemyBullets[i].render(enemyBulletGraphics);
            }
        }
        for (int i = 0; i < playerBullets.length; i++) {
            if (playerBullets[i] != null) {
                Graphics playerBulletGraphics = graphics;
                if (player.isInvinsible) {
                    playerBulletGraphics.setColor(playerColor);
                }
                else {
                    playerBulletGraphics.setColor(new Color(250, 191, 42));
                }

                playerBullets[i].render(playerBulletGraphics);
            }
        }

        if (!player.isAlive) {
            playerGraphics.setColor(new Color(15,15,15));
            player.render(playerGraphics);
        }
    }

    /* -------------------------------------------------------------------------------------- */

    /* KEYBOARD */

    /* -------------------------------------------------------------------------------------- */

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_W) {
            if (player.isAlive) {
                player.ChangeY(1);
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_S) {
            if (player.isAlive) {
                player.ChangeY(-1);
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_A) {
            if (player.isAlive) {
                player.ChangeX(1);
            }
        }
        else if (key.getKeyCode() == KeyEvent.VK_D) {
            if (player.isAlive) {
                player.ChangeX(-1);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_W) {
            player.ChangeY(0);
        }
        else if (key.getKeyCode() == KeyEvent.VK_S) {
            player.ChangeY(-0);
        }
        else if (key.getKeyCode() == KeyEvent.VK_A) {
            player.ChangeX(0);
        }
        else if (key.getKeyCode() == KeyEvent.VK_D) {
            player.ChangeX(-0);
        }
    }

    /* -------------------------------------------------------------------------------------- */

    /* MOUSE CLICK */

    /* -------------------------------------------------------------------------------------- */

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (player.isAlive) {
            for (int i = 0; i < playerBullets.length; i++) {
                if (playerBullets[i] == null) {
                    int playerX = player.x + player.width/3, playerY = player.y;
                    playerBullets[i] = new PlayerBullet(playerX, playerY, this);
                    break;
                }
            } 
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /* -------------------------------------------------------------------------------------- */

    /* UPDATES */

    /* -------------------------------------------------------------------------------------- */

    @Override
    public void actionPerformed(ActionEvent e) {

        if(!player.isInvinsible) {
            EnemyPlayerCollision();
            PlayerBulletCollision();
        }

        EnemyBulletCollision();
        PlayerInvinsibleCollision();

        if (!player.isAlive) {

            for (int i = 0; i < enemies.length; i++) {
                enemies[i] = null;
            }
            for (int i = 0; i < enemyBullets.length; i++) {
                enemyBullets[i] = null;
            }
            for (int i = 0; i < playerBullets.length; i++) {
                playerBullets[i] = null;
            }

            timerToSpawn = 0;
            player = new Player(this);
        }

        repaint();

        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] != null) {
                enemies[i].update();
            }
        }

        for (int i = 0; i < playerBullets.length; i++) {
            if (playerBullets[i] != null) {
                playerBullets[i].update();
            }
        }

        for (int i = 0; i < enemyBullets.length; i++) {
            if (enemyBullets[i] != null) {
                enemyBullets[i].update();
            }
        }

        for (int i = 0; i < invinsible.length; i++) {
            if (invinsible[i] != null) {
                invinsible[i].update();
            }
        }

        player.update();

        timerToSpawn++;
        System.out.println(timerToSpawn);

        if (timerToSpawn >= timerToSpawnLimit) {
            Random random = new Random();
            int randomPercent = random.nextInt(99);

            if (randomPercent+1 > 10) {
                CreateEnemy();
            }
            else if (randomPercent+1 <= 10) {
                CreateInvinsible();
            }
            timerToSpawn = 0;
        }
    }

    /* -------------------------------------------------------------------------------------- */

    /* ADDING AND DESTROYING ENTITIES */

    /* -------------------------------------------------------------------------------------- */

    void CreateEnemy() {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] == null) {
                Random randomX = new Random();
                int randomXCor = randomX.nextInt(999);

                enemies[i] = new Enemy(randomXCor+1, 0, this);
                break;
            }
        }
    }

    public void DestroyEnemy(Enemy enemy) {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] == enemy) {
                enemies[i] = null;
            }
        }
    }

    void DestroyEnemyBullet(EnemyBullet bullet) {
        for (int i = 0; i < enemyBullets.length; i++) {
            if (enemyBullets[i] == bullet) {
                enemyBullets[i] = null;
            }
        }
    }

    void DestroyPlayerBullet(PlayerBullet bullet) {
        for (int i = 0; i < playerBullets.length; i++) {
            if (playerBullets[i] == bullet) {
                playerBullets[i] = null;
            }
        }
    }

    void CreateSpeed() {

    }

    void CreateInvinsible() {
        for (int i = 0; i < invinsible.length; i++) {
            if (invinsible[i] == null) {
                Random randomX = new Random();
                int randomXPos = randomX.nextInt(999);

                invinsible[i] = new Invinsible(randomXPos+1, this);
                break;
            }
        }
    }

    public void CreateEnemyBullet(EnemyBullet bullet) {
        for (int i = 0; i < enemyBullets.length; i++) {
            if (enemyBullets[i] == null) {
                enemyBullets[i] = bullet;
                break;
            }
        }
    }

    /* -------------------------------------------------------------------------------------- */

    /* COLLISIONS */

    /* -------------------------------------------------------------------------------------- */

    public void EnemyPlayerCollision() {
        Rectangle playerBounds = player.receiveBounds();

        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] != null) {
                if (enemies[i].canCollide) {
                    Rectangle enemyBounds = enemies[i].receiveBounds();

                    if (playerBounds.intersects(enemyBounds)) {
                        enemies[i].canCollide = false;
                        enemies[i].isAlive = false;
                    }
                }

            }
        }
    }

    public void PlayerInvinsibleCollision() {
        Rectangle playerBounds = player.receiveBounds();

        for (int i = 0; i < invinsible.length; i++) {
            if (invinsible[i] != null) {
                Rectangle enemyBounds = invinsible[i].receiveBounds();

                if (playerBounds.intersects(enemyBounds)) {
                    invinsible[i] = null;
                    player.isInvinsible = true;
                }
            }
        }
    }  

    public void EnemyBulletCollision() {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] != null) {
                if (enemies[i].canCollide) {
                    Rectangle enemyBounds = enemies[i].receiveBounds();

                    for (int j = 0; j < playerBullets.length; j++) {
                        if (playerBullets[j] != null) {
                            Rectangle bulletBounds = playerBullets[j].receiveBounds();

                            if (bulletBounds.intersects(enemyBounds)) {
                                enemies[i].canCollide = false;
                                enemies[i].isAlive = false;

                                DestroyPlayerBullet(playerBullets[j]);
                            }
                        }
                    }
                }
            }
        }
    }

    public void PlayerBulletCollision() {
        Rectangle playerBounds = player.receiveBounds();

        for (int i = 0; i < enemyBullets.length; i++) {
            if (enemyBullets[i] != null) {
                Rectangle enemyBounds = enemyBullets[i].receiveBounds();

                if (playerBounds.intersects(enemyBounds)) {
                    DestroyEnemyBullet(enemyBullets[i]);
                    player.isAlive = false;
                }
            }
        }
    }
}

class Background {
    Screen screen;

    public Background(Screen screen) {
        this.screen = screen;
    }
    public void render(Graphics graphics) {
        graphics.fillRect(0, 0, screen.window.getWidth(), screen.window.getHeight());
    }
}