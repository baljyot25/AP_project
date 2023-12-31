package com.example.ap_project;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SceneLoader {
    //singleton design pattern used here
    private static SceneLoader s = null;

    private SceneLoader() {
    }

    public static SceneLoader getInstance() {
        if (s == null) {
            s = new SceneLoader();
        }
        return s;
    }

    // Singleton & Factory Design Pattern Used
    public void loadscene(Pane pane, String s, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
            Parent root = loader.load();
            System.out.println("prevpillar " + GameSceneController.getPrevpillar());
//            GameSceneController controller = loader.getController();
//            controller.initialize_first_pillar(first_rectangle);
//            System.out.println(controller);
            // Create a new scene using the root node
            Scene scene = new Scene(root, 314, 665);

            // Get the current stage (window)


            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.4), pane);
            transition.setToX(-stage.getWidth());


            transition.setOnFinished(e -> {
                // Set the new scene
                stage.setScene(scene);

                // Reset the translation for future transitions
                pane.setTranslateX(0);
            });

            // Play the transition
            transition.play();

        } catch (IOException e) {
            e.printStackTrace();

        }


    }
}
