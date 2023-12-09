package com.example.ap_project;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameStartController {


    @FXML
    private Pane rootPane;

    @FXML
    private void initialize() {
//        String soundPath = "audio/sample_audio.mp3";
//
//        // Load sound using getResourceAsStream
//        Media sound = new Media(getClass().getResource(soundPath).toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//
//        Button playButton = new Button("Play Sound");
//        mediaPlayer.play();

    }

    @FXML
    private void handleButtonClick(MouseEvent event) {
        SceneLoader s = SceneLoader.getInstance();

        s.loadscene(rootPane, "game_scene.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());


    }


}