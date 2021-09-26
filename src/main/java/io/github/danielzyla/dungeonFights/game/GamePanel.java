package io.github.danielzyla.dungeonFights.game;

import io.github.danielzyla.dungeonFights.component.Component;
import io.github.danielzyla.dungeonFights.component.Ghost;
import io.github.danielzyla.dungeonFights.component.Hero;
import io.github.danielzyla.dungeonFights.view.GameBoard;
import io.github.danielzyla.dungeonFights.view.ScorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.IntStream;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    Timer t = new Timer(10, this);
    private final static int CELL_SIZE = 50;
    private final GameBoard gameBoard;
    private final ScorePanel scorePanel;
    private final Set<Component> componentSet;
    private final LinkedList<Hero> heroes = new LinkedList<>();
    private final Set<Integer> pressedKeys = new HashSet<>();

    public GamePanel(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
        this.gameBoard = new GameBoard(CELL_SIZE, this);
        this.componentSet = gameBoard.getComponentSet();
        scorePanel.setGamePanel(this);
        addKeyListener(this);
        setFocusable(true);
    }

    public void startNewGame() {
        heroes.clear();
        componentSet.clear();
        scorePanel.setScore(0L);
        repaint();
        IntStream.range(0, 5).forEach(i -> heroes.add(i, new Hero(50, 50, this, scorePanel)));
        t.start();
        scorePanel.setRemainingHeroCount(heroes.size());
    }

    public void stopGame() {
        t.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        try {
            this.gameBoard.drawBoard(g2d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!heroes.isEmpty()) {
            heroes.getLast().draw(g2d);
        }
    }

    public void move() {
        heroes.getLast().move();
        componentSet.stream()
                .filter(component -> component instanceof Ghost)
                .forEach(component -> ((Ghost) component).move());
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //TODO
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!heroes.isEmpty()) {
            pressedKeys.add(keyEvent.getKeyCode());
            heroes.getLast().keyPressed(pressedKeys);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        pressedKeys.clear();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!heroes.isEmpty()) {
            repaint();
            move();
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
}
