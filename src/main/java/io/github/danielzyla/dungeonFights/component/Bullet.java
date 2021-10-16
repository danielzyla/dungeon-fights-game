package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.game.GamePanel;
import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public abstract class Bullet extends Component {

    private final int x;
    private final int y;
    private final ComponentImage bulletImage;
    private final GamePanel gamePanel;

    public Bullet(int x, int y, GamePanel gamePanel) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.bulletImage = ComponentImageRepository.getBulletImage();
        this.gamePanel = gamePanel;
    }

    public abstract void move(Iterator<Bullet> bulletIterator) throws Exception;

    void setReactionOnCollision(Iterator<Bullet> bulletIterator) throws Exception {
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
    public void draw(Graphics2D g) throws Exception {
        g.drawImage(getImage(), this.x, this.y, null);
    }

    @Override
    public BufferedImage getImage() throws Exception {
        return bulletImage.getImage();
    }
}
