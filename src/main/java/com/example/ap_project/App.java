package com.example.ap_project;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

import java.io.IOException;

public class App extends Application{
    private Sound audioClip;//we might create a thread so that this bg will run continously.
    private Sound bgMusic;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("start_screne.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 314, 665);

        new Thread(() -> {
            System.out.println("Thread created!\n");
            bgMusic = new Sound("bgMusic.mp3");
            bgMusic.playMusic();
        }).start();



        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();

    }



    private void handleMousePress(MouseEvent event) {
        double mouseX = event.getScreenX();
        double mouseY = event.getScreenY();
        // Perform your desired actions here
    }

    private void playsoundThread()
    {


    }


}