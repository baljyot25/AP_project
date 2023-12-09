package com.example.ap_project;


import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cherry implements Collidable {
    private static int cherries;//will never be reset to zero
    private boolean claimed;
    private Image image;
    private ImageView imageView;

    private int cherriesToRevive=10;

    public ImageView getImageView() {
        return this.imageView;
    }


    public Cherry(){
        this.claimed = false;
        this.image=new Image(getClass().getResourceAsStream("images/cherry_javafx.png"));
        this.imageView= new ImageView(image);
        this.imageView.setFitWidth(25);
        this.imageView.setFitHeight(25);
    }

    public void setRandomPosition() {
//        this.imageView.setX
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

    @Override
    public void onCollision() {

    }
}
