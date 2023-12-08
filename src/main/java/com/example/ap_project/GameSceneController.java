package com.example.ap_project;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GameSceneController implements MousePress {
    //inner class
    private class Stick extends PositionDimension implements Collidable
    {

        @Override
        public void add_to_screen(Pane pane,int start_position,int end_position,Hero hero) {

        }

        @Override
        public boolean didCollide(Collidable o) {
            return false;
        }

        @Override
        public void onCollision() {

        }

        public void extendStick()
        {

        }
        public void stickFall()
        {

        }
    }
    @FXML
    private Pane pane;
    private Map<String,Sound> sounds;

    @FXML
    private Label score_label;

    private Score score;
    private Cherry cherry;
    private Stick stick;



    private Timeline increaseTimeline;
    private static final double INCREASE_AMOUNT = 2.0;

    private ScaleTransition scaleTransition;

    private static int prevpillar=0;
    private Hero hero;

    private ArrayList<Pillar> pillars=new ArrayList<>();

    public static void setPrevpillar(int prevpillar) {
        GameSceneController.prevpillar = prevpillar;
    }

    public static int getPrevpillar() {
        return prevpillar;
    }

    public void AddRandomPillar()
    {
        //316 max reached
        Random r=new Random();
        Pillar p=pillars.get(r.nextInt(pillars.size()));

    }

    @FXML
    private void initialize() {
        //every pillar has a default cordinates at the bottom of the screen , whcih will be changes in future
        pillars.add(new Pillar(184,87,Color.BLACK,114,484));
        pillars.add(new Pillar(184,30,Color.BLACK,316,484));
        pillars.add(new Pillar(184,40,Color.BLACK,316,484));
        pillars.add(new Pillar(184,50,Color.BLACK,316,484));
        pillars.add(new Pillar(184,70,Color.BLACK,316,484));
        System.out.println(pillars);
        hero=new Hero();


        Pair<TranslateTransition,Rectangle> pillarpair=pillars.get(0).Transition(pillars.get(0).getXcordinate(),0);

        pane.getChildren().add(pillarpair.second());
        pillarpair.first().play();

//        Pair<TranslateTransition,Rectangle> pillarpair

//        pillars.get(0).add_to_screen(pane,200,100,hero);
//        AddRandomPillar();


        //need to add pillars in the code

        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, this::handleMousePress);
        pane.addEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
        pane.addEventFilter(MouseEvent.MOUSE_CLICKED,this :: handleMouseClick);

    }



    private void handleMouseClick(MouseEvent event)  {
        System.out.println("aefewrf");
    }



    public void handleMouseReleased(MouseEvent event) {

    }

    public void handleMousePress(MouseEvent event) {
        

    }


}
