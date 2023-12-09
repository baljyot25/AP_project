package com.example.ap_project;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle implements Collidable {
    private boolean crashed;
    private Image image;
    private ImageView imageView;


    public ImageView getImageView() {
        return this.imageView;
    }

    public boolean didCrash() {
        return this.crashed;
    }

    public Obstacle(){
        this.crashed = false;
        this.image=new Image(getClass().getResourceAsStream("images/obstacle_javafx.png"));
        this.imageView= new ImageView(image);
        this.imageView.setFitWidth(25);
        this.imageView.setFitHeight(25);
        setRandomPosition();
    }

    public void setRandomPosition() {
        this.imageView.setX(50);
    }


    @Override
    public boolean didCollide(Collidable o) {
        return this.crashed;
    }

    @Override
    public void onCollision() {
        this.crashed = true;
        this.imageView.setVisible(false);
    }
}

