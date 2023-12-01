package com.example.ap_project;

import javafx.scene.media.AudioClip;

public class Sound {
    private AudioClip audioClip;
    public void set_audioclip(String s)
    {
        String audioFilePath = getClass().getResource("path/to/your/audiofile.mp3").toString();
        audioClip=new AudioClip(audioFilePath);
    }
    public void play_clip()
    {
        audioClip.play();
    }
    public void stop_clip()
    {
        audioClip.stop();
    }

}
