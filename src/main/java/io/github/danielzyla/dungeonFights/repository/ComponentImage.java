package io.github.danielzyla.dungeonFights.repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ComponentImage {

    private final BufferedImage image;

    public ComponentImage(String imagePath) throws IOException {
        this.image = ImageIO.read(Objects.requireNonNull(this.getClass().getResource(imagePath)));
    }

    public BufferedImage getImage() {
        return image;
    }
}
