package com.example.ap_project;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Sound {
    private static final Map<String, Sound> instances = new HashMap<String, Sound>();
    private final Media media;
    private final MediaPlayer mediaPlayer;

    public Sound(String soundName) {
        this.media = new Media(Objects.requireNonNull(getClass().getResource("audio/" + soundName)).toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }


    public Sound(String soundName, int cycleCount) {
        this.media = new Media(Objects.requireNonNull(getClass().getResource("audio/" + soundName)).toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.setCycleCount(cycleCount);
    }

    public static Sound getInstance(String soundFileName) {
        if (!instances.containsKey(soundFileName)) {
            instances.put(soundFileName, new Sound(soundFileName));
        }
        return instances.get(soundFileName);
    }

    public void setCycleTo(int newCycleCount) {
        this.mediaPlayer.setCycleCount(newCycleCount);
    }

    public void setCycleToInfinite() {
        this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void toggleMusic() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
    }

    public boolean isPlaying() {
        return this.mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public void playMusic() {
        if (this.mediaPlayer.getCycleCount() == 1) {
            mediaPlayer.seek(Duration.ZERO);
        }
        mediaPlayer.play();
    }

    public void stop() {
        mediaPlayer.stop();
    }
}
