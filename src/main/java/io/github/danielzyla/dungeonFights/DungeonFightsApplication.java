package io.github.danielzyla.dungeonFights;

import io.github.danielzyla.dungeonFights.view.DungeonFightsGameFrame;
import io.github.danielzyla.dungeonFights.view.GamePanel;

public class DungeonFightsApplication {

    public static void main(String[] args) {
        GamePanel gamePanel = new GamePanel();
        new DungeonFightsGameFrame(gamePanel);
    }
}
