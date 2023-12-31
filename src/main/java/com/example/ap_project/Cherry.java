package com.example.ap_project;


import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cherry implements Collidable {
    private static boolean revived = false;
    private static int cherries = 0;//will never be reset to zero
    private final Image image;
    private final ImageView imageView;
    private final int cherriesToRevive = 5;
    private boolean claimed;

    public Cherry() {
        this.claimed = false;
        this.image = new Image(getClass().getResourceAsStream("images/cherry_javafx.png"));
        this.imageView = new ImageView(image);
        this.imageView.setFitWidth(25);
        this.imageView.setFitHeight(25);
        setRandomPosition();
    }

    public static void setRevived() {
        revived = true;
    }

    public static void resetRevived() {
        revived = false;
    }

    public static boolean getRevived() {
        return revived;
    }

    public static int getCherries() {
        return cherries;
    }

    public static void setCherries(int cherries) {
        Cherry.cherries = cherries;
    }

    public static void incrementCherries() {
        ++Cherry.cherries;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public ImageView getNewImageView() {
        Image image = new Image(getClass().getResourceAsStream("images/cherry_javafx.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        imageView.setX(50);

        return imageView;
    }

    public Image getImage() {
        return this.image;
    }

    public boolean isClaimed() {
        return this.claimed;
    }

    public void reset() {
        this.claimed = false;
    }

    public void setRandomPosition() {
        this.imageView.setX(50);
    }

    public boolean revive_possible() {
        return cherries >= cherriesToRevive;
    }

    public void set_cherry_score(Label label) {
        label.setText(String.valueOf(cherries));
    }

    @Override
    public boolean didCollide(Collidable o) {
        return false;
    }

    public int getCherriesToRevive() {
        return cherriesToRevive;
    }

    @Override
    public void onCollision() {
        this.claimed = true;
        this.imageView.setVisible(false);
    }

    public void resetClaimed() {
        this.claimed = false;
    }
}