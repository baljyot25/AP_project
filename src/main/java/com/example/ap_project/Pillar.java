package com.example.ap_project;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Pillar extends PositionDimension implements Collidable,Cloneable {
    protected Rectangle rectangle;
    public Pillar(){}
    public Pillar(int height,int width, Color color,int xcord,int ycord)
    {
        rectangle = new Rectangle();
        rectangle.setWidth(width);  // Set the width
        rectangle.setHeight(height);  // Set the height
        rectangle.setFill(color);  // Set the fill color

        // Set the position of the rectangle
        rectangle.setLayoutX(xcord);  // X-coordinate
        rectangle.setLayoutY(ycord);
        this.color=Color.BLACK;
        this.height=height;
        this.width=width;
        this.xcordinate=xcord;
        this.ycordinate=ycord;
    }

    public  void set_coordinate(int x , int y)
    {
        xcordinate=x;
        ycordinate=y;
        rectangle.setLayoutX(xcordinate);  // X-coordinate
        rectangle.setLayoutY(ycordinate);

    }

    public Pair<TranslateTransition,Rectangle> Transition(int start_position ,int end_position)

    {
//        System.out.println(this.rectangle.getX());
        this.set_coordinate(start_position,ycordinate);
        TranslateTransition transition = new TranslateTransition(Duration.millis(600), rectangle);

        transition.setByX(-xcordinate+end_position);
        return new Pair<>(transition,rectangle);
    }
    public void add_to_screen(Pane pane, int start_position,int end_position,Hero hero) {
//        //it will add itself to the given screen
//        pane.getChildren().add(rectangle);
//        //setting the xcord , ycord back to normal for next iteration
//        xcordinate=0;
//        ycordinate=484;
//        rectangle.setLayoutX(xcordinate);  // X-coordinate
//        rectangle.setLayoutY(ycordinate);
        this.set_coordinate(start_position,ycordinate);
        TranslateTransition transition = new TranslateTransition(Duration.millis(600), rectangle);

        transition.setByX(-xcordinate+end_position);

//        TranslateTransition imageTransition=hero.returnTransition(xcordinate,rectangle);
//        imageTransition.setByX(-xcordinate);
//        transition.setCycleCount((int) 4f);
//        transition.setAutoReverse(true);
//        set_coordinate(114,484);

//        transition.setNode(rectangle);
//
//        transition.setFromX(114-(1.4*width)); // Starting X position (right side of the screen)
//        transition.setToX(-100);   // Ending X position
//        transition.setDuration(Duration.seconds(1)); // Animation duration
//        // Add the rectangle to a Pane
//
        pane.getChildren().add(rectangle);
        pane.getChildren().add(hero.imageView);//need to add getter here

//        ParallelTransition parallelTransition = new ParallelTransition(transition, imageTransition);

        // Play the parallel transition
        transition.play();
//        imageTransition.play();
//        parallelTransition.play();
    }

    public Pillar clone()
    {
        try {
            return (Pillar) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean didCollide(Collidable o) {
        return false;
    }

    @Override
    public void onCollision() {

    }
}
