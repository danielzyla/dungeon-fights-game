package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rune extends Component {

    private final ComponentImage runeImage;
    private final int x;
    private final int y;

    public Rune(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.runeImage = ComponentImageRepository.getRuneImage();
    }

    public Rectangle getBounds() throws Exception {
        return new Rectangle(this.x, this.y, getImage().getWidth(), getImage().getHeight());
    }

    @Override
    public BufferedImage getImage() throws Exception {
        return runeImage.getImage();
    }

}
