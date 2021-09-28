package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;
import io.github.danielzyla.dungeonFights.game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ghost extends Component {

    private int x;
    private int y;
    private final static int SPEED_RATE = 2;
    private final ComponentImage ghostImage;
    private final GamePanel gamePanel;

    public Ghost(int x, int y, GamePanel gamePanel) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.ghostImage = ComponentImageRepository.getGhostImage();
        this.gamePanel = gamePanel;
    }

    public void move() {
        int xa = getRandomInt();
        int ya = getRandomInt();
        if (x + xa >= getBounds().width && x + xa <= (gamePanel.getWidth() - 2 * getBounds().width - 2 * xa))
            x += xa * SPEED_RATE;
        if (y + ya >= getBounds().height && y + ya <= (gamePanel.getHeight() - 2 * getBounds().height - 2 * ya))
            y += ya * SPEED_RATE;
        for (Component component : gamePanel.getGameBoard().getComponentSet()) {
            if (component instanceof Wall) {
                if (collisionWith(component)) {
                    Rectangle cb = component.getBounds();
                    setPositionAfterCollision(cb);
                }
            }
        }
    }

    private int getRandomInt() {
        int min = -1;
        int max = 1;
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    private void setPositionAfterCollision(Rectangle cb) {
        if (y > cb.y && y < (cb.y + cb.height)) {
            y = cb.y + cb.height;
        }
        if (y < cb.y && y > (cb.y - getBounds().height)) {
            y = cb.y - getBounds().height;
        }
        if (x > cb.x && x < (cb.x + cb.width)) {
            x = cb.x + cb.width;
        }
        if (x < cb.x && x > (cb.x - getBounds().width)) {
            x = cb.x - getBounds().width;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, getImage().getWidth(), getImage().getHeight());
    }

    @Override
    public BufferedImage getImage() {
        return ghostImage.getImage();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(getImage(), this.x, this.y, null);
    }
}
