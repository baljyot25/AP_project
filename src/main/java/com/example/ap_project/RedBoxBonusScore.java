package com.example.ap_project;

import javafx.scene.layout.Pane;

public class RedBoxBonusScore extends Pillar {
    //this class is a special case of a pillar with fixed dimensions and its position is calculated
    //by the second pillar in the game screen.
    public RedBoxBonusScore() {
        super();
        this.height = 1;//some constant
        this.width = 1;
//        rectangle=new Rectangle();
//        rectangle.setHeight(height);
//        rectangle.setWidth(width);
        //position will be calculated in the add_to_screen.
    }


    public void add_to_screen(Pane pane, Pillar p) {

    }
}
