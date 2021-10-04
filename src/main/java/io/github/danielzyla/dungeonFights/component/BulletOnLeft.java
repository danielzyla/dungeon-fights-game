package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.game.GamePanel;

import java.awt.*;
import java.util.Iterator;

public class BulletOnLeft extends Bullet {

    private int x;
    private final int y;
    private final GamePanel gamePanel;
    private final int dx;

    public BulletOnLeft(int x, int y, GamePanel gamePanel) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
        this.dx = 10;
    }

    public void move(Iterator<Bullet> bulletIterator) throws Exception {
        x -= dx;
        Iterator<Component> componentIterator = gamePanel.getGameBoard().getComponentSet().iterator();
        while (componentIterator.hasNext()) {
            Component next = componentIterator.next();
            if (collisionWith(next)) {
                if (next instanceof Wall || next instanceof Gold) {
                    bulletIterator.remove();
                }
                if (next instanceof Freak) {
                    componentIterator.remove();
                    bulletIterator.remove();
                }
            }
        }
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
