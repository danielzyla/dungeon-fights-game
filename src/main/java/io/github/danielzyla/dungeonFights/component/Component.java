package io.github.danielzyla.dungeonFights.component;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Component {

    private BufferedImage image;
    private final int x;
    private final int y;

    Component(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.image.getWidth(), this.image.getHeight());
    }

    public boolean collisionWith(Component component) {
        return getBounds().intersects(component.getBounds());
    }

    public void draw(Graphics2D g) {
        g.drawImage(getImage(), this.x, this.y, null);
    }
}
