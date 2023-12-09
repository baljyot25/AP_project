package com.example.ap_project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle implements Collidable {
    private final Image image;
    private final ImageView imageView;
    private boolean crashed;


    public Obstacle() {
        this.crashed = false;
        this.image = new Image(getClass().getResourceAsStream("images/obstacle_javafx.png"));
        this.imageView = new ImageView(image);
        this.imageView.setFitWidth(20);
        this.imageView.setFitHeight(20);
        setRandomPosition();
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public boolean didCrash() {
        return this.crashed;
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

    public void resetCrashed() {
        this.crashed = false;
    }

    public ImageView getNewImageView() {
        Image image = new Image(getClass().getResourceAsStream("images/obstacle_javafx.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        imageView.setX(50);

        return imageView;
    }
}