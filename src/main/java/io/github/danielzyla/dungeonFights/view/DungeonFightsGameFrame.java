package io.github.danielzyla.dungeonFights.view;

import javax.swing.*;

public class DungeonFightsGameFrame extends JFrame {

    public DungeonFightsGameFrame(GamePanel gamePanel) {
        setSize(1000, 1035);
        setTitle("Dungeon Fights");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        add(gamePanel);
    }
}
