package io.github.danielzyla.dungeonFights.component;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Component {

    private BufferedImage image;
    private Point position;

    public Component(Point position) {
        this.position = position;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
