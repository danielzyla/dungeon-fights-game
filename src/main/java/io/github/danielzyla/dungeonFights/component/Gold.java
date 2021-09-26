package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.repository.ComponentImage;
import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Gold extends Component {

    private final ComponentImage goldImage;
    private final int x;
    private final int y;

    public Gold(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.goldImage = ComponentImageRepository.getGoldImage();
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, getImage().getWidth(), getImage().getHeight());
    }

    @Override
    public BufferedImage getImage() {
        return goldImage.getImage();
    }
}
