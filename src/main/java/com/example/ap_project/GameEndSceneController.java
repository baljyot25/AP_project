package com.example.ap_project;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameEndSceneController {
    private Cherry cherry;
    @FXML
    private Pane pane;
    @FXML
    private Label current_score;
    @FXML
    private Label best_score;
    @FXML
    private Label not_enough;
    @FXML
    private Rectangle not_enough_rect;
    @FXML
    private Label already_revived;
    @FXML
    private Rectangle already_revived_rect;

    @FXML
    private void initialize() {
        current_score.setText(String.valueOf(Score.getCurrent_score()));
        Score.is_best();
        best_score.setText(String.valueOf(Score.getBest_score()));
        not_enough_rect.setVisible(false);
        not_enough.setVisible(false);
        already_revived_rect.setVisible(false);
        already_revived.setVisible(false);

    }

    public void loadhomescreen(MouseEvent event) {
        //load the homescreen fxml
        Score.setCurrent_score(0);
        SceneLoader s = SceneLoader.getInstance();
        Cherry.resetRevived();
        s.loadscene(pane, "start_screne.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public void loadgamescreen(MouseEvent event) {
        //load the gamescreen fxml
        Score.setCurrent_score(0);
        SceneLoader s = SceneLoader.getInstance();
        Cherry.resetRevived();
        s.loadscene(pane, "game_scene.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public void revive(MouseEvent event) {
        //revive by loading last game screen
        System.out.println("brubebvfajvadsbv;ads");
        cherry = new Cherry();
        if (cherry.revive_possible() && !Cherry.getRevived()) {
            Cherry.setRevived();
            Cherry.setCherries(Cherry.getCherries() - cherry.getCherriesToRevive());
            SceneLoader s = SceneLoader.getInstance();

            s.loadscene(pane, "game_scene.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());

            //load the game-scene.fxml
        } else {
            if (cherry.revive_possible()) {
                already_revived.setVisible(true);
                already_revived_rect.setVisible(true);
            } else {
                not_enough.setVisible(true);
                not_enough_rect.setVisible(true);
            }

            //handle
        }
    }
}
