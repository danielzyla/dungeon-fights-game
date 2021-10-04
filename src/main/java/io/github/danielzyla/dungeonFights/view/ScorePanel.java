package io.github.danielzyla.dungeonFights.view;

import io.github.danielzyla.dungeonFights.component.Heart;
import io.github.danielzyla.dungeonFights.game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ScorePanel extends JPanel implements ActionListener {

    Timer t = new Timer(5, this);
    private long score;
    private final JLabel scoreValueLabel;
    private final JLabel scoreLabel;
    private int remainingHeroCount;
    private final JButton playButton;
    private GamePanel gamePanel;

    public ScorePanel() {
        setPreferredSize(new Dimension(1000, 50));
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        playButton = new JButton();
        playButton.setText("PLAY");
        playButton.setSize(100, 40);
        playButton.setFont(new Font("Verdana", Font.BOLD, 20));
        playButton.setForeground(Color.BLUE);
        setPlayGameButton(true);
        scoreLabel = new JLabel("SCORE", JLabel.CENTER);
        scoreLabel.setSize(100, 30);
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
        scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        scoreValueLabel = new JLabel();
        scoreValueLabel.setText(String.valueOf(this.score));
        scoreValueLabel.setOpaque(true);
        scoreValueLabel.setBackground(Color.WHITE);
        scoreValueLabel.setForeground(Color.BLUE);
        scoreValueLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        scoreValueLabel.setSize(250, 30);
        t.start();
    }

    public void setPlayGameButton(boolean ready) {
        if (ready) {
            playButton.addActionListener(actionEvent -> {
                try {
                    gamePanel.startNewGame();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                remove(playButton);
                add(scoreLabel);
                add(scoreValueLabel);
            });
            if (Arrays.stream(this.getComponents()).collect(Collectors.toList()).contains(scoreLabel)) remove(scoreLabel);
            if (Arrays.stream(this.getComponents()).collect(Collectors.toList()).contains(scoreValueLabel)) remove(scoreValueLabel);
            add(playButton);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        if (remainingHeroCount > 0) {
            for (int i = 1; i <= remainingHeroCount; i++) {
                Heart heart = new Heart(50 * i, 10);
                try {
                    g2d.drawImage(heart.getImage(), heart.getX(), heart.getY(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public void setRemainingHeroCount(int remainingHeroCount) {
        this.remainingHeroCount = remainingHeroCount;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        scoreValueLabel.setText("");
        scoreValueLabel.setText(String.valueOf(score));
        repaint();
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

}
