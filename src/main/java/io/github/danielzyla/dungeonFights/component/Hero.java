package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.game.AudioPlayer;
import io.github.danielzyla.dungeonFights.game.GamePanel;
import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;
import io.github.danielzyla.dungeonFights.view.ScorePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Set;

public class Hero extends Component {

    private int x;
    private int y;
    private int dx;
    private int dy;
    private final static int SPEED_RATE = 10;
    private final ComponentImage heroImage;
    private final GamePanel gamePanel;
    private final ScorePanel scorePanel;

    public Hero(int x, int y, GamePanel gamePanel, ScorePanel scorePanel) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.heroImage = ComponentImageRepository.getHeroImage();
        this.gamePanel = gamePanel;
        this.scorePanel = scorePanel;
    }

    public void keyPressed(Set<Integer> pressedKeys) {
        if (Set.of(37).equals(pressedKeys)) {
            setCoordinates(-1, 0);
        } else if (Set.of(38).equals(pressedKeys)) {
            setCoordinates(0, -1);
        } else if (Set.of(39).equals(pressedKeys)) {
            setCoordinates(1, 0);
        } else if (Set.of(40).equals(pressedKeys)) {
            setCoordinates(0, 1);
        } else if (Set.of(37, 38).equals(pressedKeys)) {
            setCoordinates(-1, -1);
        } else if (Set.of(38, 39).equals(pressedKeys)) {
            setCoordinates(1, -1);
        } else if (Set.of(39, 40).equals(pressedKeys)) {
            setCoordinates(1, 1);
        } else if (Set.of(37, 40).equals(pressedKeys)) {
            setCoordinates(-1, 1);
        } else if (Set.of(32, 37).equals(pressedKeys)) {
            setCoordinates(-2, 0);
        } else if (Set.of(32, 39).equals(pressedKeys)) {
            setCoordinates(2, 0);
        } else if (Set.of(32, 38).equals(pressedKeys)) {
            setCoordinates(0, -2);
        } else if (Set.of(32, 40).equals(pressedKeys)) {
            setCoordinates(0, 2);
        }
    }

    private void setCoordinates(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        Iterator<Component> componentIterator = gamePanel.getGameBoard().getComponentSet().iterator();

        while (componentIterator.hasNext()) {
            Component component = componentIterator.next();
            if (collisionWith(component)) {
                if (component instanceof Wall) {
                    setPositionAfterCollision(component);
                }
                if (component instanceof Ghost) {
                    AudioPlayer.playScoreSound("lost.wav");
                    componentIterator.remove();
                    gamePanel.getHeroes().removeLast();
                    scorePanel.setRemainingHeroCount(gamePanel.getHeroes().size());
                    if (gamePanel.getHeroes().isEmpty()) {
                        gamePanel.gameOver();
                    }
                }
                if (component instanceof Gold) {
                    AudioPlayer.playScoreSound("score.wav");
                    componentIterator.remove();
                    scorePanel.setScore(scorePanel.getScore() + 10);
                }
            }
        }

        if (x + dx >= getBounds().width && x + dx <= (gamePanel.getWidth() - 2 * getBounds().width - 2 * dx))
            x += dx * SPEED_RATE;
        dx = 0;
        if (y + dy >= getBounds().height && y + dy <= (gamePanel.getHeight() - 2 * getBounds().height - 2 * dy))
            y += dy * SPEED_RATE;
        dy = 0;
    }

    private void setPositionAfterCollision(Component component) {
        Rectangle cb = component.getBounds();
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
        return new Rectangle(x, y, getImage().getWidth(), getImage().getHeight());
    }

    @Override
    public BufferedImage getImage() {
        return heroImage.getImage();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(getImage(), this.x, this.y, null);
    }
}
