package com.example.ap_project;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GameSceneController implements MousePress {


    @FXML
    private Pane pane;
    private MediaPlayer mediaPlayer;
    private Map<String,Sound> sounds;

    @FXML
    private Label score_label;
    private Sound stickSound, cherrySound, bgMusic, dropSound;
    private Score score;
    private Cherry cherry;
    private Obstacle obstacle;
    private Stick stick;

    private static final int SCREENHEIGHT=668;
    private static final int SCREENWIDTH=314;

    private Timeline increaseTimeline;
    private static final double INCREASE_AMOUNT = 2.0;

    private ScaleTransition scaleTransition;

    private int mousePressed = 0;
    private boolean heroInverted = false;
    private boolean heroReachedPillar = false;

    private boolean collidedWithPillar = false;
    private static int prevpillar=0;
    private Hero hero;

    private ArrayList<Pillar> pillars=new ArrayList<>();
    public void initializeSounds() {
        bgMusic = new Sound("bgMusic.mp3");
        cherrySound = new Sound("cherrySound.mp3", 1);
        stickSound = new Sound("increaseStick.mp3");
        dropSound = new Sound("perfectDrop.mp3", 1);
    }


    public static void setPrevpillar (int prevpillar) {
        GameSceneController.prevpillar = prevpillar;
    }



    public static int getPrevpillar() {
        return prevpillar;
    }
    private double curStickY;
    private double curStickX;
    private class Stick extends PositionDimension implements Collidable
    {
        Line rod;

        public Line getRod() {
            return rod;
        }

        private double stickLength = 0.0;
        public Stick(double curStickX,double curStickY)
        {
            rod=new Line(curStickX,curStickY,curStickX,curStickY);
            pane.getChildren().add(rod);
            rod.setVisible(false);
            rod.setStrokeWidth(3);

        }
        public void setVisible(Boolean b)
        {
            rod.setVisible(b);}

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
//            System.out.println("Increase stick length called!");
            stickLength += INCREASE_AMOUNT;
//        stick.setEndY(stickLength);
            // curStic
            rod.setEndY(curStickY - stickLength);

        }
        public void stickFall()
        {
            stickSound.toggleMusic();
            Rotate rotation = new Rotate();
            rotation.pivotXProperty().bind(rod.startXProperty());
            rotation.pivotYProperty().bind(rod.startYProperty());
            rod.getTransforms().add(rotation);
            Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 0)),
                    new KeyFrame(Duration.seconds(0.5), new KeyValue(rotation.angleProperty(), 90)));
            timeline.setOnFinished(e -> makeHeroMove());
            timeline.play();
            System.out.println("TIME LINE PLAYED");
            System.out.println("TIME LINE OVER");

        }
    }


    public Pillar AddRandomPillar()
    {
        //316 max reached
        Random r=new Random();
        int ra=r.nextInt(pillars.size());
        Pillar p=pillars.get(ra);
//        pillars.remove(ra);


//        p.rectangle=new Rectangle();
        return p;

    }
    Rectangle r1;
    Rectangle r2;
    Pillar pillar1;
    Pillar pillar2;


    AnimationTimer collisionTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            checkCollision();
        }
    };

    public void handleCherryCollision() {
        cherry.onCollision();
        cherrySound.playMusic();
        System.out.println("Score is : " + score);
//        score.increment_current_score();
       //   to update cherry score
        System.out.println("Score is : " + score);
    }

    public void handleObstacleCollision() {
        obstacle.onCollision();
//        obstacleSound.playMusic();
        TranslateTransition deathTransition=hero.onDeath();
        deathTransition.setOnFinished(Event->
        {
            SceneLoader s=SceneLoader.getInstance();
            s.loadscene(pane,"game_end_scene.fxml", (Stage) r1.getScene().getWindow());
        });
        deathTransition.play();
    }

    public void handlePillarCollision() {
        TranslateTransition deathTransition=hero.onDeath();
        deathTransition.setOnFinished(Event->
        {
            SceneLoader s=SceneLoader.getInstance();
            s.loadscene(pane,"game_end_scene.fxml", (Stage) r1.getScene().getWindow());
        });
        deathTransition.play();
    }

    public void checkCollision() {
        if (hero.getGroup().getBoundsInParent().intersects(cherry.getImageView().getBoundsInParent()) && !cherry.isClaimed()) {
            System.out.println("Collision Detected!");
            System.out.println("bound intersection val is " + hero.group.getBoundsInParent().intersects(cherry.getImageView().getBoundsInParent()));
            System.out.println("cherry claimed val is " + cherry.isClaimed());
            handleCherryCollision();
            System.out.println("cherry claimed val is " + cherry.isClaimed());
        }
        if (hero.getGroup().getBoundsInParent().intersects(obstacle.getImageView().getBoundsInParent()) && !obstacle.didCrash()) {
            System.out.println("Collision Detected!");
            System.out.println("bound intersection val is " + hero.group.getBoundsInParent().intersects(obstacle.getImageView().getBoundsInParent()));
            System.out.println("obstacle claimed val is " + obstacle.didCrash());
            handleObstacleCollision();
            System.out.println("obstacle claimed val is " + obstacle.didCrash());
        }
        if (hero.getGroup().getBoundsInParent().intersects(r2.getBoundsInParent())) {
            System.out.println("Pillar Collision Detected!");
            handlePillarCollision();
        }
    }



    int secondpos;
    double secwidth;
    double firstwidth;
    @FXML
    private void initialize() {
        this.initializeSounds();
//        bgMusic.playMusic();
        score_label.setText(String.valueOf(Score.getCurrent_score()));


        //every pillar has a default cordinates at the bottom of the screen , whcih will be changes in future

        pillars.add(new Pillar(184,87,Color.BLACK,114,484));
        pillars.add(new Pillar(184,30,Color.BLACK,SCREENWIDTH,484));
        pillars.add(new Pillar(184,50,Color.BLACK,SCREENWIDTH,484));
        pillars.add(new Pillar(184,70,Color.BLACK,SCREENWIDTH,484));
        System.out.println(pillars);
        hero=new Hero();
        cherry = new Cherry();
        obstacle = new Obstacle();



        Pair<TranslateTransition,Rectangle> pillarpair=pillars.get(0).Transition(pillars.get(0).getXcordinate(),0);
        Pair<TranslateTransition, Group> heropair=hero.returnTransition(pillars.get(0).getXcordinate(),pillarpair.second());
        pillar1=pillars.get(0);
        firstwidth=pillarpair.second().getWidth();
//        pillars.remove(0);

        Pillar p2=AddRandomPillar();
//        while(p2==pillars.get(0))
//        {
//            p2=this.AddRandomPillar();
//        }
//        pillar2=p2;
//        pillars.add(new Pillar(pillar1.height,pillar1.width,Color.BLACK,SCREENWIDTH,484));


//        System.out.println(pane.snapSizeX());
        Random r=new Random();
        int min= (int) pillarpair.second().getWidth() + 5;
        int max=SCREENWIDTH-(int)p2.width();
        System.out.println(p2);
        System.out.println(r.nextInt((max - min) ) + min);
        secondpos=r.nextInt((max - min) ) + min; // second pillar





        Pair<TranslateTransition,Rectangle> pillarpair2=p2.Transition(314,secondpos);
        secwidth=pillarpair2.second().getWidth();
//        Pair<TranslateTransition,Rectangle> pillarpair2=p2.Transition(p2.getXcordinate(),314-p2.width());



        pane.getChildren().add(pillarpair2.second());
        pane.getChildren().add(heropair.second());

        pane.getChildren().add(cherry.getImageView());
        pane.getChildren().add(obstacle.getImageView());
        cherry.getImageView().setX(150);
        cherry.getImageView().setY(450);
        obstacle.getImageView().setX(170);
        obstacle.getImageView().setY(450);

        pane.getChildren().add(pillarpair.second());

        heropair.first().play();
        pillarpair.first().play();
        pillarpair2.first().play();
        r1=pillarpair.second();
        r2=pillarpair2.second();

        curStickX = pillarpair.second().getWidth();
        curStickY = SCREENHEIGHT - pillarpair.second().getHeight();
        stick=new Stick(curStickX,curStickY);
//        sticks.add(stick);
//        System.out.println(pillarpair.second().getX());

        //adding timelines
        increaseTimeline = new Timeline(new KeyFrame(Duration.millis(16), event -> stick.extendStick()));
        increaseTimeline.setCycleCount(Timeline.INDEFINITE);

        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, this::handleMousePress);
        pane.addEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
        pane.addEventFilter(MouseEvent.MOUSE_CLICKED,this :: handleMouseClick);

//        ArrayList<Double> pillarData = getPillarDetails();
//        System.out.println("Pillar Data: ");
//        System.out.println(pillarData);

//        printPillarDetails();


    }

    private void toggleMusic() {
        // Check if the music is currently playing
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            // Pause the music if it's playing
            mediaPlayer.pause();
        } else {
            // Resume the music if it's paused
            mediaPlayer.play();
        }
    }

    //    @Override
    public void stop() {
        // Release resources when the application is closed
        mediaPlayer.stop();
    }


    private void handleMouseClick(MouseEvent event)  {
        System.out.println("[Event] : Mouse Clicked");
    }



    public void handleMouseReleased(MouseEvent event) {
        if (mousePressed == 1) {
            System.out.println("[Event] : Mouse Released");
            stick.stickFall();
            increaseTimeline.pause();

        }

    }

    private Double heroOffset = 0.0;
    private void getHeroOffset() {
        ArrayList<Double> pillarDetails = getPillarDetails();
        Double heroX = hero.getImageView().localToScreen(0, 0).getX();
        heroOffset = heroX - pillarDetails.get(1);
    }

    private ArrayList<Stick> sticks=new ArrayList<>();

    private void pillarTransition()
    {
        System.out.println("pillar transition");
        System.out.println(pillar1);
        System.out.println(r1);
        pane.getChildren().remove(r1);
        TranslateTransition transition = new TranslateTransition(Duration.millis(600), r2);
        System.out.println(r2.getX());
        System.out.println(r2.getLayoutX());
        System.out.println(r2.localToScreen(0, 0).getX());
        System.out.println(secondpos);
        transition.setByX(-secondpos);
        firstwidth=r2.getWidth();

        Group group=hero.getGroup();
        TranslateTransition imageTransition = new TranslateTransition(Duration.seconds(0.6 ), group);
//        imageTransition.setFromX(secondpos);
//        imageTransition.setToX(0);
        imageTransition.setByX(-secondpos);
//        Pair<TranslateTransition,Rectangle> pillarpair=pillar2.Transition((int)r2.getX(),0);
//        pillarpair.first().play();
        Line rod=stick.getRod();
        TranslateTransition rodtransition=new TranslateTransition(Duration.seconds(0.6 ), rod);
        rodtransition.setByX(-secondpos);
        System.out.println(pillars);
        System.out.println(pillar2);
        Pillar p2=AddRandomPillar();
//        Pillar p2=pillars.get(0);
        secwidth=r2.getWidth();
        System.out.println(p2);

//        System.out.println(p2);
        if(p2==pillar1)
        {
            System.out.println("sdmfsd");
        }
        Random r=new Random();
        int min= (int) r2.getWidth() + 5;
        int max=SCREENWIDTH-(int)p2.width();
        System.out.println("max"+max+"min"+min);
        secondpos=r.nextInt((max - min) ) + min;
        System.out.println(p2.getXcordinate());
        Pair<TranslateTransition,Rectangle> pillarpair2=p2.Transition(314,secondpos);
        System.out.println(pillarpair2.second());
//        secwidth=pillarpair2.second().getWidth();

        pane.getChildren().add(pillarpair2.second());

        transition.play();
        imageTransition.play();
        rodtransition.play();
        pillarpair2.first().play();
        Score.setCurrent_score(Score.getCurrent_score()+1);
        score_label.setText(String.valueOf(Score.getCurrent_score()));
        r1=r2;
        pillar1=pillar2;
        pillar2=p2;
        r2=pillarpair2.second();
        mousePressed=0;
        curStickX = r1.getWidth();
        curStickY = SCREENHEIGHT - r1.getHeight();
        sticks.add(stick);
        System.out.println(sticks);
        if (sticks.size()>=2)
        {

            sticks.get(sticks.size()-2).setVisible(false);
        }

//        stick.setVisible(false);
        stick=new Stick(curStickX,curStickY);
//        sticks.add(stick);

    }

    public ArrayList<Double> getPillarDetails() {
        double rec1X= r1.localToScreen(0, 0).getX();
        double rec1Y = r1.localToScreen(0, 0).getY();
        double rec2X = r2.localToScreen(0, 0).getX();
        double rec2Y = r2.localToScreen(0, 0).getY();
        double rec1width=r1.getWidth();
        double rec2width=r2.getWidth();
        double rightTopX = r2.getBoundsInParent().getMaxX();

//        System.out.println(r1.getWidth());
//        System.out.println("Rectangle R1");
//        // rec1x can act as offset, so we can subtract that from all rectangles
//        System.out.println("Rectangle position on screen: X=" + rec1X + ", Y=" + rec1Y);
//        System.out.println("Rectangle position on screen: X=" + (rec1X+r1.getWidth()) + ", Y=" + rec1Y);
//
//        System.out.println("Rectangle R2");
//        System.out.println("Rectangle position on screen: X=" + rec2X + ", Y=" + rec2Y);
//        System.out.println("Rectangle position on screen: X=" + (rec2X+r2.getWidth()) + ", Y=" + rec2Y);

        ArrayList<Double> retVal = new ArrayList<>(4);
//        retVal.add(0.0);
//        retVal.add(rec1width);
//        retVal.add(rec2X - rec1X);
//        retVal.add(rec2width);
//        return retVal;
        retVal.add(rec1X);
        retVal.add(rec1X + rec1width);
        retVal.add(rec2X);
        retVal.add(rec2X + rec2width);
        return retVal;
    }

    private void makeHeroMove() {
        getHeroOffset();
        dropSound.playMusic();
        collisionTimer.start();
        double rec1X= r1.localToScreen(0, 0).getX();
        double rec1Y = r1.localToScreen(0, 0).getY();
        double rec2X = r2.localToScreen(0, 0).getX();
        double rec2Y = r2.localToScreen(0, 0).getY();
        double rec1width=r1.getWidth();
        double rec2width=r2.getWidth();
        double rightTopX = r2.getBoundsInParent().getMaxX();

        System.out.println(r1.getWidth());
        System.out.println("Rectangle R1");
        System.out.println("Rectangle position on screen: X=" + rec1X + ", Y=" + rec1Y);
        System.out.println("Rectangle position on screen: X=" + (rec1X+r1.getWidth()) + ", Y=" + rec1Y);

        System.out.println("Rectangle R2");
        System.out.println("Rectangle position on screen: X=" + rec2X + ", Y=" + rec2Y);
        System.out.println("Rectangle position on screen: X=" + (rec2X+r2.getWidth()) + ", Y=" + rec2Y);
        System.out.println(stick.stickLength);
        TranslateTransition deathTransition=hero.onDeath();
        deathTransition.setOnFinished(Event->
        {
            SceneLoader s=SceneLoader.getInstance();
            s.loadscene(pane,"game_end_scene.fxml", (Stage) r1.getScene().getWindow());
        });

        if (rec1X+rec1width+stick.stickLength+1.9>=rec2X  && rec1X+rec1width+stick.stickLength+1.9<=rec2X+rec2width)
        {




            System.out.println("Second pas "+secondpos);
            System.out.println("r2 pos "+r2.getLayoutX());
            System.out.println("r2 pos "+(r2.localToScreen(0,0).getX()-r1.localToScreen(0,0).getX()));
            System.out.println("chalega");


            Pair<TranslateTransition,ParallelTransition>p=hero.move(rightTopX-hero.getImageView().getX()-30,false);
            TranslateTransition translate=p.first();
            ParallelTransition parallelTransition=p.second();
            Rectangle leg1=hero.getLeg1();
            Rectangle leg2=hero.getLeg2();
            translate.setOnFinished(event -> {
                // Stop the RotateTransition when translation is finished

                parallelTransition.stop();
                RotateTransition r=new RotateTransition(Duration.seconds(0.0001), leg1);
                r.setToAngle(0);
//            r.setAutoReverse(true); // Rotate back and forth
//            r.setCycleCount(Timeline.INDEFINITE);
                r.play();
                r=new RotateTransition(Duration.seconds(0.0001), leg2);
                r.setToAngle(0);
//            r.setAutoReverse(true); // Rotate back and forth
//            r.setCycleCount(Timeline.INDEFINITE);
                r.play();
                r.setOnFinished(event1->
                {
                    pillarTransition();
                });
            });
            parallelTransition.play();

        }
        else {
            System.out.println("nahi chalega");
            Pair<TranslateTransition,ParallelTransition>p=hero.move(stick.stickLength,true);
            TranslateTransition translate=p.first();
            ParallelTransition parallelTransition=p.second();
            Rectangle leg1=hero.getLeg1();
            Rectangle leg2=hero.getLeg2();
            translate.setOnFinished(event -> {
                // Stop the RotateTransition when translation is finished

                parallelTransition.stop();
                RotateTransition r=new RotateTransition(Duration.seconds(0.0001), leg1);
                r.setToAngle(0);
//            r.setAutoReverse(true); // Rotate back and forth
//            r.setCycleCount(Timeline.INDEFINITE);
                r.play();
                r=new RotateTransition(Duration.seconds(0.0001), leg2);
                r.setToAngle(0);
//            r.setAutoReverse(true); // Rotate back and forth
//            r.setCycleCount(Timeline.INDEFINITE);
                r.play();
                r.setOnFinished(event1->{

                    deathTransition.play();
                });
            });

            parallelTransition.play();


        }



//        System.out.println("Hero move called!");
//        //Instantiating TranslateTransition class
//        TranslateTransition translate = new TranslateTransition();
//
//        System.out.println("hero x is " + hero.getX());
//        int distToMove = (int) (stickLength + (87 - hero.getX()));
//        //shifting the X coordinate of the centre of the circle by 400
//        translate.setByX(stickLength + 18);
//
//        //setting the duration for the Translate transition
//        translate.setDuration(Duration.millis(2000));
//
//        //setting cycle count for the Translate transition
//        translate.setCycleCount(1);
//
//
//        //setting Circle as the node onto which the transition will be applied
//        translate.setNode(hero);
//
//        //playing the transition
//        translate.play();
//        translate.setOnFinished(e -> setHeroReachedPillar());
////        translate.stop();
    }
    public void handleMousePress(MouseEvent event) {
        if (heroReachedPillar) {
            return;
        }

        mousePressed += 1;
//        System.out.println("Mouse pressed val is " + mousePressed);
//        System.out.println("Mouse Pressed at X: " + event.getX() + ", Y: " + event.getY());
        if (mousePressed == 1) {
            stick.setVisible(true);
            stickSound.toggleMusic();
            increaseTimeline.play();
        } else if (mousePressed > 1){


//
            System.out.println("Rotating!");
            Group group =hero.getGroup();
            ArrayList<Double> pillarDetails = getPillarDetails();
            Double heroX = hero.getImageView().localToScreen(0, 0).getX();
            Double groupX = hero.getGroup().localToScreen(0, 0).getX();

            System.out.println("INVERT DETAILS");
            System.out.println("hero x is " + heroX);
            System.out.println("hero group x is " + groupX);
            System.out.println("Pillar details are " + pillarDetails);
            heroX = heroX + heroOffset;

            if (heroX > pillarDetails.get(0) && heroX < pillarDetails.get(1)) {
                System.out.println("Hero is on pillar1 so won't rotate");
                return;
            } else if (heroX > pillarDetails.get(2) && heroX < pillarDetails.get(3)){
                System.out.println("Hero is on pillar2 so won't rotate");
                return;
            }

            group.setRotate(group.getRotate() + 180);
            heroInverted = !heroInverted;
            if (!heroInverted) {
                group.setLayoutY(group.getLayoutY() - 30);
            } else {
                group.setLayoutY(group.getLayoutY() + 30);
            }
        }

    }


}
