package com.example.ap_project;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;


public class Sound {
    private Media media;
    private MediaPlayer mediaPlayer;

    public Sound() {
        this.media = new Media(Objects.requireNonNull(getClass().getResource("music.mp3")).toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void toggleMusic() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
    }

    public void stop() {
        mediaPlayer.stop();
    }
}
