package io.github.danielzyla.dungeonFights;

import io.github.danielzyla.dungeonFights.game.GamePanel;
import io.github.danielzyla.dungeonFights.view.DungeonFightsGameFrame;
import io.github.danielzyla.dungeonFights.view.ScorePanel;

import java.io.IOException;

public class DungeonFightsApplication {

    public static void main(String[] args) throws IOException {
        ScorePanel scorePanel = new ScorePanel();
        GamePanel gamePanel = new GamePanel(scorePanel);
        new DungeonFightsGameFrame(gamePanel);
    }

}
