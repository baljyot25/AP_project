package com.example.ap_project;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private Sound audioClip;
    private Sound bgMusic;

    public static void main(String[] args) {
        launch();

    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("start_screne.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 314, 665);

        new Thread(() -> {
            System.out.println("Thread created!\n");
            bgMusic = Sound.getInstance("bgMusic.mp3");
        }).start();


        stage.setScene(scene);
        stage.show();
    }

    private void handleMousePress(MouseEvent event) {
        double mouseX = event.getScreenX();
        double mouseY = event.getScreenY();
    }

    private void playsoundThread() {


    }


}
