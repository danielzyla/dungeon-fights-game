package io.github.danielzyla.dungeonFights.view;

import io.github.danielzyla.dungeonFights.game.GamePanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DungeonFightsGameFrame extends JFrame {

    public DungeonFightsGameFrame(GamePanel gamePanel) {
        int boardRowNumber = gamePanel.getGameBoard().getRowNumber();
        int boardColNumber = gamePanel.getGameBoard().getColNumber();
        int cellSize = gamePanel.getGameBoard().getCellSize();
        setSize(cellSize * boardColNumber, cellSize * boardRowNumber + 85);
        setTitle("Dungeon Fights");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        Border border = BorderFactory.createLineBorder(Color.YELLOW);
        ScorePanel scorePanel = gamePanel.getScorePanel();
        scorePanel.setBorder(border);
        add(scorePanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
    }

}
