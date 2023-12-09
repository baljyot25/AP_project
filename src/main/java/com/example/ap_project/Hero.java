
package com.example.ap_project;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Hero extends PositionDimension implements Collidable , MousePress {
    private boolean positionInverted;
    private Sound death_sound ; /*="sample_audio.mp3";*/;
    Image image;
    ImageView imageView;
    Rectangle leg1;
    Rectangle leg2;
    Group group;
    public ImageView getImageView() {
        return imageView;
    }


    public Hero(){


//        image = new Image(String.valueOf(currentPath.resolve("src").resolve("main").resolve("resources").resolve("images").resolve("character_stickhero_javafx.png")));
        image=new Image(getClass().getResourceAsStream("images/hero_draft2.png"));
        imageView= new ImageView(image);
        imageView.setFitWidth(33);
        imageView.setFitHeight(30);
        leg1=new Rectangle(3,7);
        leg2=new Rectangle(3,7);

    }

    private static Path convertToRelativePath(Path absolutePath) {
        // Assuming there is a base directory, for example, the current working directory
        Path baseDirectory = Paths.get(System.getProperty("user.dir"));

        // Resolve the relative path against the base directory
        Path relativePath = baseDirectory.relativize(absolutePath);

        return relativePath;
    }



    public Pair<TranslateTransition,Group>  returnTransition(int position, Rectangle r)
    {
        imageView.setX(r.getX()+r.getWidth()-36);
        imageView.setY(454);
        leg1.setX(r.getX()+r.getWidth()-36+11);
        leg1.setY(454+25);
        leg2.setY(454+25);
        leg2.setX(r.getX()+r.getWidth()-36+20);

        group= new Group(imageView,leg1,leg2);

        TranslateTransition imageTransition = new TranslateTransition(Duration.seconds(0.6 ), group);
        imageTransition.setFromX(position);
        imageTransition.setToX(0);
        return new Pair<>(imageTransition,group);

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

    public Rectangle getLeg1() {
        return leg1;
    }

    public Rectangle getLeg2() {
        return leg2;
    }

//    public double getGroupX() {
////        this.group.get
//    }

    public Pair<TranslateTransition,ParallelTransition> move(double distanceToMove,Boolean b) {
        TranslateTransition translate = new TranslateTransition();

//        System.out.println("hero x is " + hero.getX());

        //shifting the X coordinate of the centre of the circle by 400
        if (b==false)
        {
            translate.setToX(distanceToMove );
        }
        else {
            translate.setByX(distanceToMove+18);
        }


        //setting the duration for the Translate transition
        translate.setDuration(Duration.millis(distanceToMove*10));

        //setting cycle count for the Translate transition
        translate.setCycleCount(1);


        //setting Circle as the node onto which the transition will be applied
        translate.setNode(group);

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), leg1);
        rotateTransition.setAutoReverse(true); // Rotate back and forth
        rotateTransition.setToAngle(15); // Rotate by 45 degrees
        rotateTransition.setFromAngle(-45);








        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        RotateTransition rotateTransition2 = new RotateTransition(Duration.seconds(0.5), leg2);

        rotateTransition2.setAutoReverse(true); // Rotate back and forth
        rotateTransition2.setToAngle(-15); // Rotate by 45 degrees
        rotateTransition2.setFromAngle(45);
        rotateTransition2.setCycleCount(Timeline.INDEFINITE);

        // Set an onFinished handler for the TranslateTransition


        // Create a ParallelTransition to play both transitions simultaneously

        ParallelTransition parallelTransition = new ParallelTransition(translate, rotateTransition2,rotateTransition);
        return new Pair<>(translate,parallelTransition);






    }

    public Group getGroup() {
        return group;
    }

    public TranslateTransition onDeath() {

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(2), group);
        translateTransition.setByY(454);
        return translateTransition;

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
