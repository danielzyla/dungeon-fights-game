package io.github.danielzyla.dungeonFights.repository;

import io.github.danielzyla.dungeonFights.component.ComponentImage;

import java.io.IOException;

public class ComponentImageRepository {

    private static ComponentImage heroRightImage;
    private static ComponentImage heroLeftImage;
    private static ComponentImage bulletImage;
    private static ComponentImage wallImage;
    private static ComponentImage ghostImage;
    private static ComponentImage skullImage;
    private static ComponentImage goldImage;
    private static ComponentImage heartImage;
    private static ComponentImage goblinImage;
    private static ComponentImage runeImage;

    static {
        try {
            heroRightImage = new ComponentImage("/img/hero-right.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            heroLeftImage = new ComponentImage("/img/hero-left.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            bulletImage = new ComponentImage("/img/bullet.png");
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
            goblinImage = new ComponentImage("/img/goblin.png");
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
            skullImage = new ComponentImage("/img/skull.png");
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

    static {
        try {
            runeImage = new ComponentImage("/img/rune.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private ComponentImageRepository() {
    }

    public static ComponentImage getHeroRightImage() {
        return heroRightImage;
    }

    public static ComponentImage getHeroLeftImage() {
        return heroLeftImage;
    }

    public static ComponentImage getBulletImage() {
        return bulletImage;
    }

    public static ComponentImage getWallImage() {
        return wallImage;
    }

    public static ComponentImage getGhostImage() {
        return ghostImage;
    }

    public static ComponentImage getSkullImage() {
        return skullImage;
    }

    public static ComponentImage getGoldImage() {
        return goldImage;
    }

    public static ComponentImage getHeartImage() {
        return heartImage;
    }

    public static ComponentImage getGoblinImage() {
        return goblinImage;
    }

    public static ComponentImage getRuneImage() {
        return runeImage;
    }
}
