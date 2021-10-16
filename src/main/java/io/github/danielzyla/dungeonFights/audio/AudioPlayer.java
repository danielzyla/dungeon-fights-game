package io.github.danielzyla.dungeonFights.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class AudioPlayer {

    private Clip clip;

    public static void playSingleSound(String soundFileName) {
        Thread playSound = new Thread(() -> {
            try (AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(AudioPlayer.class.getResource("/sound/" + soundFileName)))) {
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.start();
                Thread.sleep(2000);
                clip.close();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        playSound.setDaemon(true);
        playSound.start();
    }

    public void playSoundInLoop(String soundFileName) {
        Thread playSound = new Thread(() -> {
            try (AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(AudioPlayer.class.getResource("/sound/" + soundFileName)))) {
                clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
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
