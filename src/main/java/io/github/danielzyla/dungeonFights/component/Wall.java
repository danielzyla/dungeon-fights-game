package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends Component {

    private final ComponentImage wallImage;
    private final int x;
    private final int y;

    public Wall(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.wallImage = ComponentImageRepository.getWallImage();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, getImage().getWidth(), getImage().getHeight());
    }

    @Override
    public BufferedImage getImage() {
        return wallImage.getImage();
    }
}
