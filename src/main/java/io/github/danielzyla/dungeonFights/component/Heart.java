package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Heart extends Component {

    private final ComponentImage heartImage;
    private final int x;
    private final int y;

    public Heart(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.heartImage = ComponentImageRepository.getHeartImage();
    }

    @Override
    public Rectangle getBounds() throws Exception {
        return new Rectangle(this.x, this.y, getImage().getWidth(), getImage().getHeight());
    }

    @Override
    public BufferedImage getImage() throws Exception {
        return heartImage.getImage();
    }

}
