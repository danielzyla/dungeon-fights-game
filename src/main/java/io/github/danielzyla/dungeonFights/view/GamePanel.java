package io.github.danielzyla.dungeonFights.view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel {

    private GameBoard gameBoard;

    public GamePanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        try {
            this.gameBoard = new GameBoard(50, graphics2D);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
