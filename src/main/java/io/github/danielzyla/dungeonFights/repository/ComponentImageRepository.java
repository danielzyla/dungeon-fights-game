package io.github.danielzyla.dungeonFights.repository;

import java.io.IOException;

public class ComponentImageRepository {

    private static ComponentImage heroImage;
    private static ComponentImage wallImage;
    private static ComponentImage ghostImage;
    private static ComponentImage goldImage;
    private static ComponentImage heartImage;

    static {
        try {
            heroImage = new ComponentImage("/img/hero.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            wallImage = new ComponentImage("/img/wall.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            ghostImage = new ComponentImage("/img/ghost.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            goldImage = new ComponentImage("/img/gold.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            heartImage = new ComponentImage("/img/heart.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private ComponentImageRepository() {
    }

    public static ComponentImage getHeroImage() {
        return heroImage;
    }

    public static ComponentImage getWallImage() {
        return wallImage;
    }

    public static ComponentImage getGhostImage() {
        return ghostImage;
    }

    public static ComponentImage getGoldImage() {
        return goldImage;
    }

    public static ComponentImage getHeartImage() {
        return heartImage;
    }
}
