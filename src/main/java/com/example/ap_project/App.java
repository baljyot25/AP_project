package com.example.ap_project;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

import java.io.IOException;

public class App extends Application {
    private Sound audioClip;//we might create a thread so that this bg will run continously.
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("start_screne.fxml"));
        System.out.println("fxml load");
        Scene scene = new Scene(fxmlLoader.load(), 314, 665);
        System.out.println("idhar hua");
        stage.setTitle("Stick Hero");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();

    }

    private void handleMousePress(MouseEvent event) {
        double mouseX = event.getScreenX();
        double mouseY = event.getScreenY();

        System.out.println("Mouse Pressed at: (" + mouseX + ", " + mouseY + ")");
        // Perform your desired actions here
    }

    private void playsoundThread()
    {


    }
}