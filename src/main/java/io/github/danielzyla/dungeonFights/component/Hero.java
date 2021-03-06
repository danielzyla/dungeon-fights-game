package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.audio.AudioPlayer;
import io.github.danielzyla.dungeonFights.game.GamePanel;
import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;
import io.github.danielzyla.dungeonFights.view.ScorePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Hero extends Component {

    private int x;
    private int y;
    private int dx;
    private int dy;
    private final static int SPEED_RATE = 3;
    private ComponentImage heroImage;
    private final GamePanel gamePanel;
    private final ScorePanel scorePanel;
    private boolean shooter;

    public Hero(int x, int y, GamePanel gamePanel, ScorePanel scorePanel) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.heroImage = ComponentImageRepository.getHeroRightImage();
        this.gamePanel = gamePanel;
        this.scorePanel = scorePanel;
        this.shooter = false;
    }

    public void changePositionWith(Set<Integer> pressedKeys) {
        if (pressedKeys.equals(Set.of(37))) {
            this.heroImage = ComponentImageRepository.getHeroLeftImage();
            setCoordinates(-1, 0);
        }
        if (pressedKeys.equals(Set.of(39))) {
            this.heroImage = ComponentImageRepository.getHeroRightImage();
            setCoordinates(1, 0);
        }
        if (pressedKeys.isEmpty()) setCoordinates(0, 0);
        if (pressedKeys.equals(Set.of(38))) setCoordinates(0, -1);
        if (pressedKeys.equals(Set.of(40))) setCoordinates(0, 1);
        if (pressedKeys.equals(Set.of(32, 37))) setCoordinates(-2, 0);
        if (pressedKeys.equals(Set.of(32, 39))) setCoordinates(2, 0);
        if (pressedKeys.equals(Set.of(32, 38))) setCoordinates(0, -2);
        if (pressedKeys.equals(Set.of(32, 40))) setCoordinates(0, 2);
    }

    private void setCoordinates(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move() throws Exception {
        x += dx * SPEED_RATE;
        y += dy * SPEED_RATE;

        Iterator<Component> componentIterator = gamePanel.getGameBoard().getComponentSet().iterator();

        while (componentIterator.hasNext()) {
            Component component = componentIterator.next();
            if (collisionWith(component)) {
                if (component instanceof Wall) {
                    setPositionAfterCollisionWithWall(component);
                }
                if (component instanceof Freak) {
                    setReactionOnCollisionWithFreak(componentIterator);
                }
                if (component instanceof Gold) {
                    setReactionOnCollisionWithGold(componentIterator);
                }
                if (component instanceof Rune) {
                    setReactionOnCollisionWithRune(componentIterator);
                }
            }
        }
    }

    private void setReactionOnCollisionWithRune(Iterator<Component> componentIterator) {
        AudioPlayer.playSingleSound("score.wav");
        componentIterator.remove();
        this.shooter = true;
        scorePanel.setScore(scorePanel.getScore() + 1000);
    }

    private void setReactionOnCollisionWithGold(Iterator<Component> componentIterator) {
        AudioPlayer.playSingleSound("score.wav");
        componentIterator.remove();
        scorePanel.setScore(scorePanel.getScore() + 10);
        List<Gold> remained = new ArrayList<>();
        for (Component componentLeft : gamePanel.getGameBoard().getComponentSet()) {
            if (componentLeft instanceof Gold) remained.add((Gold) componentLeft);
        }
        if (remained.isEmpty()) {
            gamePanel.levelWon();
        }
    }

    private void setReactionOnCollisionWithFreak(Iterator<Component> componentIterator) {
        AudioPlayer.playSingleSound("lost.wav");
        componentIterator.remove();
        gamePanel.getHeroes().removeLast();
        scorePanel.setRemainingHeroCount(gamePanel.getHeroes().size());
        if (gamePanel.getHeroes().isEmpty()) {
            gamePanel.gameOver();
        }
    }

    private void setPositionAfterCollisionWithWall(Component component) throws Exception {
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

    public void shoot() throws Exception {
        Bullet bullet;
        if (this.heroImage == ComponentImageRepository.getHeroRightImage()) {
            bullet = new BulletOnRight(
                    (this.getX() + this.getBounds().width),
                    this.getY() + (this.getBounds().height / 2),
                    gamePanel);
        } else {
            bullet = new BulletOnLeft(
                    (this.getX() - ComponentImageRepository.getBulletImage().getImage().getWidth()),
                    this.getY() + (this.getBounds().height / 2),
                    gamePanel
            );
        }
        AudioPlayer.playSingleSound("shoot.wav");
        gamePanel.getBulletList().add(bullet);
    }

    @Override
    public Rectangle getBounds() throws Exception {
        return new Rectangle(x, y, getImage().getWidth(), getImage().getHeight());
    }

    @Override
    public BufferedImage getImage() throws Exception {
        return heroImage.getImage();
    }

    @Override
    public void draw(Graphics2D g) throws Exception {
        g.drawImage(getImage(), this.x, this.y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isShooter() {
        return shooter;
    }

}
