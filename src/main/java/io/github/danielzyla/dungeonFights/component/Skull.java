package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.game.GamePanel;
import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;

import java.awt.image.BufferedImage;

public class Skull extends Freak {

    private final ComponentImage skullImage;

    public Skull(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        this.skullImage = ComponentImageRepository.getSkullImage();
    }

    @Override
    public BufferedImage getImage() throws Exception {
        return skullImage.getImage();
    }

}
