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

    public abstract Rectangle getBounds() throws Exception;

    public boolean collisionWith(Component component) throws Exception {
        return getBounds().intersects(component.getBounds());
    }

    public void draw(Graphics2D g) throws Exception {
        g.drawImage(getImage(), this.x, this.y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImage() throws Exception {
        return image;
    }


}
