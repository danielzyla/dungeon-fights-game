package io.github.danielzyla.dungeonFights.game;

import io.github.danielzyla.dungeonFights.component.*;
import io.github.danielzyla.dungeonFights.component.Component;
import io.github.danielzyla.dungeonFights.view.GameBoard;
import io.github.danielzyla.dungeonFights.view.ScorePanel;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    Timer t = new Timer(10, this);
    private final GameBoard gameBoard;
    private final ScorePanel scorePanel;
    private final Set<Component> componentSet;
    private final LinkedList<Hero> heroes = new LinkedList<>();
    private final Set<Integer> pressedKeys = new HashSet<>();
    private Hero current;
    private final List<Bullet> bulletList = new ArrayList<>();
    private final AudioPlayer audioPlayer;


    public GamePanel(ScorePanel scorePanel) throws IOException {
        this.scorePanel = scorePanel;
        this.gameBoard = new GameBoard(this);
        this.componentSet = gameBoard.getComponentSet();
        this.audioPlayer = new AudioPlayer();
        scorePanel.setGamePanel(this);
        addKeyListener(this);
        setFocusable(true);
    }

    public void startNewGame() throws InterruptedException {
        heroes.clear();
        componentSet.clear();
        scorePanel.setScore(0L);
        bulletList.clear();
        repaint();
        IntStream.range(0, 3).forEach(i -> heroes.add(i, new Hero(50, 50, this, scorePanel)));
        t.start();
        scorePanel.setRemainingHeroCount(heroes.size());
        audioPlayer.playScoreSound("soundtrack.wav");
    }

    public void levelWon() {
        t.stop();
        audioPlayer.stopSoundClip();
        audioPlayer.playScoreSound("levelWon.wav");
        openGameStatusPopUp("<html><p>CONGRATULATIONS !</p><br><p>LEVEL COMPLETE !</p></html>");
    }

    public void gameOver() {
        t.stop();
        audioPlayer.stopSoundClip();
        audioPlayer.playScoreSound("gameOver.wav");
        openGameStatusPopUp("<html><u>GAME OVER !</u></html>");
    }

    private void openGameStatusPopUp(String message) {
        Thread thread = new Thread(() -> {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/hero-right.png")));
            JOptionPane.showMessageDialog(this,
                    message,
                    "Game status",
                    JOptionPane.INFORMATION_MESSAGE,
                    icon);
            SwingUtilities.invokeLater(() -> scorePanel.setPlayGameButton(true));
        });
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        try {
            this.gameBoard.drawBoard(g2d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!heroes.isEmpty()) {
            try {
                current = heroes.getLast();
                current.draw(g2d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!bulletList.isEmpty()) {
            bulletList.forEach(bullet -> {
                try {
                    bullet.draw(g2d);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void move() throws Exception {
        heroes.getLast().move();
        componentSet.stream()
                .filter(component -> component instanceof Freak)
                .forEach(component -> {
                    try {
                        ((Freak) component).move();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        Iterator<Bullet> bulletIterator = bulletList.iterator();
        while (bulletIterator.hasNext()) {
            Bullet next = bulletIterator.next();
            next.move(bulletIterator);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //TODO
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!heroes.isEmpty()) {
            pressedKeys.add(keyEvent.getKeyCode());
            try {
                heroes.getLast().keyPressed(pressedKeys);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (!heroes.isEmpty()) {
            pressedKeys.remove(keyEvent.getKeyCode());
            try {
                heroes.getLast().keyPressed(pressedKeys);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (current.isShooterSkill() && keyEvent.getKeyCode() == 70) {
            try {
                current.shoot();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!heroes.isEmpty()) {
            repaint();
            try {
                move();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public ScorePanel getScorePanel() {
        return scorePanel;
    }

    public LinkedList<Hero> getHeroes() {
        return heroes;
    }

    public Hero getCurrent() {
        return current;
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }

}
