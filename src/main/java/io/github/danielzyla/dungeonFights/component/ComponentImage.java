package io.github.danielzyla.dungeonFights.component;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ComponentImage {

    private final BufferedImage image;
    private final static double SCALE_FACTOR = 1;

    public ComponentImage(String imagePath) throws IOException {
        this.image = ImageIO.read(Objects.requireNonNull(this.getClass().getResource(imagePath)));
    }

    public BufferedImage getImage() throws Exception {
        return simpleResizeImage(image, (int) (image.getWidth() * SCALE_FACTOR));
    }

    BufferedImage simpleResizeImage(BufferedImage originalImage, int targetWidth) {
        return Scalr.resize(originalImage, targetWidth);
    }

}
