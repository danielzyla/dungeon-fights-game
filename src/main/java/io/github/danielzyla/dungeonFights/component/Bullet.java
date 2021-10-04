package io.github.danielzyla.dungeonFights.component;

import io.github.danielzyla.dungeonFights.repository.ComponentImageRepository;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public abstract class Bullet extends Component {

    private final int x;
    private final int y;
    private final ComponentImage bulletImage;

    public Bullet(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.bulletImage = ComponentImageRepository.getBulletImage();
    }

    public abstract void move(Iterator<Bullet> bulletIterator) throws Exception;

    @Override
    public void draw(Graphics2D g) throws Exception {
        g.drawImage(getImage(), this.x, this.y, null);
    }

    @Override
    public BufferedImage getImage() throws Exception {
        return bulletImage.getImage();
    }
}
