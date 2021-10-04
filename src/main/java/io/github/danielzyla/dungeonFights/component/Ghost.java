package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.game.GamePanel;
import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;

import java.awt.image.BufferedImage;

public class Ghost extends Freak {

    private final ComponentImage ghostImage;

    public Ghost(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        this.ghostImage = ComponentImageRepository.getGhostImage();
    }

    @Override
    public BufferedImage getImage() throws Exception {
        return ghostImage.getImage();
    }

}
