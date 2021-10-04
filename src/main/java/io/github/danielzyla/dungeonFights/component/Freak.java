package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.game.GamePanel;

import java.awt.*;

public class Freak extends Component {

    private int x;
    private int y;
    private final static int SPEED_RATE = 2;
    private final GamePanel gamePanel;
    int xa;
    int ya;

    public Freak(int x, int y, GamePanel gamePanel) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
        this.xa = 1;
        this.ya = 0;
    }

    public void move() throws Exception {
        x += xa * SPEED_RATE;
        y += ya * SPEED_RATE;

        Hero current = gamePanel.getCurrent();

        for (Component component : gamePanel.getGameBoard().getComponentSet()) {
            if (!(component instanceof Freak)) {
                if (collisionWith(component)) {
                    xa *= -1;
                    if (component.getX() < x) x = component.getX() + component.getBounds().width;
                    if (component.getX() > x) x = component.getX() - component.getBounds().width;
                }
                if (current.getX() < x && current.getY() == y) {
                    if (x + xa > x) xa *= -1;
                }
                if (current.getX() > x && current.getY() == y) {
                    if (x + xa < x) xa *= -1;
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

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

}
