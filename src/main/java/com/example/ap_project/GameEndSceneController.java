package com.example.ap_project;

import javafx.scene.input.MouseEvent;

public class GameEndSceneController {
    Cherry cherry;
    public void loadhomescreen(MouseEvent event) {
        //load the homescreen fxml
    }

    public void loadgamescreen(MouseEvent event) {
        //load the gamescreen fxml
    }

    public void revive(MouseEvent event) {
        //revive by loading last game screen
        cherry=new Cherry();
        if (cherry.revive_possible())
        {
            //load the game-scene.fxml
        }
        else {
            //handle
        }
    }
}
