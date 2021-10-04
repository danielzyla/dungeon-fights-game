package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.game.GamePanel;
import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;

import java.awt.image.BufferedImage;

public class Goblin extends Freak {

    private final ComponentImage goblinImage;

    public Goblin(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        this.goblinImage = ComponentImageRepository.getGoblinImage();
    }

    @Override
    public BufferedImage getImage() throws Exception {
        return goblinImage.getImage();
    }

}
