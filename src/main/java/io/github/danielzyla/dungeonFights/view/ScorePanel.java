package io.github.danielzyla.dungeonFights.view;

import io.github.danielzyla.dungeonFights.component.Heart;
import io.github.danielzyla.dungeonFights.game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScorePanel extends JPanel implements ActionListener {

    Timer t = new Timer(5, this);
    private long score;
    private final JLabel scoreValueLabel;
    private int remainingHeroCount;
    private final JButton playButton;
    private GamePanel gamePanel;

    public ScorePanel() {
        setPreferredSize(new Dimension(1000, 50));
        setLayout(null);
        JLabel scoreLabel = new JLabel("SCORE");
        scoreLabel.setBounds(600, 10, 100, 30);
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
        scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        scoreValueLabel = new JLabel();
        scoreValueLabel.setText(String.valueOf(this.score));
        scoreValueLabel.setOpaque(true);
        scoreValueLabel.setBackground(Color.WHITE);
        scoreValueLabel.setForeground(Color.BLUE);
        scoreValueLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        scoreValueLabel.setBounds(720, 10, 250, 30);
        add(scoreLabel);
        add(scoreValueLabel);
        playButton = new JButton();
        playButton.setText("PLAY");
        playButton.setBounds(300,5, 100, 40);
        playButton.setFont(new Font("Verdana", Font.BOLD, 20));
        playButton.setForeground(Color.BLUE);
        setPlayGameButton(true);
        t.start();
    }

    public void setPlayGameButton(boolean ready) {
        if (ready) {
            playButton.addActionListener(actionEvent -> {
                gamePanel.startNewGame();
                remove(playButton);
            });
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
                g2d.drawImage(heart.getImage(), heart.getX(), heart.getY(), null);
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
