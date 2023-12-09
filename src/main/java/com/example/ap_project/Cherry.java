
package com.example.ap_project;


import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cherry implements Collidable {
    private static int cherries = 0;//will never be reset to zero
    private boolean claimed;
    private Image image;
    private ImageView imageView;

    private int cherriesToRevive=1;

    public ImageView getImageView() {
        return this.imageView;
    }

    public ImageView getNewImageView() {
        ImageView newIV = new ImageView(image);
        return newIV;
    }

    public Image getImage() {
        return this.image;
    }


    public Cherry(){
        this.claimed = false;
        this.image=new Image(getClass().getResourceAsStream("images/cherry_javafx.png"));
        this.imageView= new ImageView(image);
        this.imageView.setFitWidth(25);
        this.imageView.setFitHeight(25);
        setRandomPosition();
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

    public boolean revive_possible()
    {
        if (cherries>=cherriesToRevive)
        {
            return true;
        }
        return false;
    }
    public void set_cherry_score(Label label)
    {
        label.setText(String.valueOf(cherries));
    }

    @Override
    public boolean didCollide(Collidable o) {
        return false;
    }

    public int getCherriesToRevive() {
        return cherriesToRevive;
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

    @Override
    public void onCollision() {
        this.claimed = true;
        this.imageView.setVisible(false);
    }
}
