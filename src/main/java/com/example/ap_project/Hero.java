
package com.example.ap_project;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Hero extends PositionDimension implements Collidable , MousePress {
    private boolean positionInverted;
    private Sound death_sound ; /*="sample_audio.mp3";*/;
    Image image;
    ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public Hero(){
        image = new Image("C:\\Users\\baljyot\\OneDrive\\Desktop\\Ap_project\\src\\main\\resources\\images\\character_stickhero_javafx.png");
        imageView= new ImageView(image);
        imageView.setFitWidth(33);
        imageView.setFitHeight(30);

    }




    public TranslateTransition  returnTransition(int position, Rectangle r)
    {
        imageView.setX(r.getX()+r.getWidth()-36);
        imageView.setY(454);
        TranslateTransition imageTransition = new TranslateTransition(Duration.seconds(0.6 ), imageView);
        imageTransition.setFromX(position);
        return imageTransition;

    }


    @Override
    public boolean didCollide(Collidable c) {
        // Check if c is a cherry or a pillar
        return false;
    }

    @Override
    public void onCollision() {

    }

    private void invertHero() {

    }

    public void handleMouseReleased(MouseEvent event) {
        this.invertHero();
    }

    public void handleMousePress(MouseEvent event){
        this.invertHero();
    }

    public void run() {

    }

    public void onDeath() {

    }
    private void deathSound()
    {

    }

    @Override
    public void add_to_screen(Pane pane,int start_position,int end_position,Hero hero) {


    }

    private void moveHeroToPillarCenter(Pillar p) {

    }
}
