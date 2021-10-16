package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.game.GamePanel;

import java.awt.*;
import java.util.Iterator;

public class BulletOnLeft extends Bullet {

    private int x;
    private final int y;
    private final int dx;

    public BulletOnLeft(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        this.x = x;
        this.y = y;
        this.dx = 10;
    }

    public void move(Iterator<Bullet> bulletIterator) throws Exception {
        x -= dx;
        setReactionOnCollision(bulletIterator);
    }

    @Override
    public Rectangle getBounds() throws Exception {
        return new Rectangle(this.x, this.y, getImage().getWidth(), getImage().getHeight());
    }

    @Override
    public void draw(Graphics2D g) throws Exception {
        g.drawImage(getImage(), this.x, this.y, null);
    }

}
