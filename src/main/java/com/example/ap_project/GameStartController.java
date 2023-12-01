package com.example.ap_project;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameStartController {


    @FXML
    private Pane rootPane;

    @FXML
    private void initialize() {

    }
    @FXML
    private void handleButtonClick(MouseEvent event){
        SceneLoader s=SceneLoader.getInstance();
        s.loadscene(rootPane,"game_scene.fxml",event);


    }



}