package io.github.danielzyla.dungeonFights.game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    public static void playScoreSound(String soundFileName) {
        Thread playSound = new Thread(() -> {
            try (AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/sound/" + soundFileName))) {
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        });
        playSound.start();
    }
}
