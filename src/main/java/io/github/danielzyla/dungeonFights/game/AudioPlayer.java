package io.github.danielzyla.dungeonFights.game;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Objects;

public class AudioPlayer {

    private Clip clip;

    public void playScoreSound(String soundFileName) {
        Thread playSound = new Thread(() -> {
            try (AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(AudioPlayer.class.getResource("/sound/" + soundFileName)))) {
                clip = AudioSystem.getClip();
                clip.open(inputStream);
                if (soundFileName.equals("sound-track.wav")) clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        });
        playSound.setDaemon(true);
        playSound.start();
    }

    public void stopSoundClip() {
        clip.stop();
    }

}
