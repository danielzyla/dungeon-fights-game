package io.github.danielzyla.dungeonFights.component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Wall extends Component{

    private final BufferedImage image;

    public Wall(Point position) throws IOException {
        super(position);
        this.image = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/img/wall.png")));
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }
}
