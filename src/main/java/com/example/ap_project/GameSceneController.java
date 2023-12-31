package com.example.ap_project;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GameSceneController implements MousePress {


    private static final int SCREENHEIGHT = 668;
    private static final int SCREENWIDTH = 314;
    private static final double INCREASE_AMOUNT = 2.0;
    private static int prevpillar = 0;
    private final boolean heroReachedPillar = false;
    private final ArrayList<Pillar> pillars = new ArrayList<>();
    private final ArrayList<Stick> sticks = new ArrayList<>();
    private ImageView cherryimageView, obstacleimageView;
    private ParallelTransition heroTransition;
    private Rectangle r1;
    private Rectangle r2;
    private Pillar pillar1;
    private Pillar pillar2;
    int secondpos;
    double secwidth;
    double firstwidth;
    @FXML
    private Pane pane;
    private MediaPlayer mediaPlayer;
    private Map<String, Sound> sounds;
    private boolean gamePaused = false;
    private boolean gameJustResumed = false;
    @FXML
    private Label score_label;
    private Sound stickSound, cherrySound, dropSound;
    private Score score;
    private Cherry cherry;
    private Obstacle obstacle;
    private Stick stick;
    private Timeline increaseTimeline;
    private ScaleTransition scaleTransition;
    private int mousePressed = 0;
    private boolean heroInverted = false;
    private boolean collidedWithPillar = false;
    private Hero hero;
    @FXML
    private Label cherryScore;
    private double curStickY;
    private double curStickX;
    @FXML
    private ImageView pause_button;
    private Double heroOffset = 0.0;
    private boolean pillarTranslationOver = true;
    AnimationTimer collisionTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            checkCollision();
        }
    };

    public static int getPrevpillar() {
        return prevpillar;
    }

    public static void setPrevpillar(int prevpillar) {
        GameSceneController.prevpillar = prevpillar;
    }

    public void initializeSounds() {
        cherrySound = Sound.getInstance("cherrySound.mp3");
        cherrySound.setCycleTo(1);
        stickSound = Sound.getInstance("increaseStick.mp3");
        dropSound = Sound.getInstance("perfectDrop.mp3");
        dropSound.setCycleTo(1);
    }

    public void pausegame() {
        System.out.println("Pause game called");
        if (heroTransition != null) {
            heroTransition.pause();
        }

        Rectangle re = new Rectangle(0, 0, 314, SCREENHEIGHT);
        Rectangle score = new Rectangle(240, 183, Color.WHITE);
        score.setLayoutY(164);
        score.setLayoutX(36);
        re.setOpacity(0.5);

        Label currentScoreLabel = new Label("CURRENT SCORE");
        Label bestLabel = new Label("BEST");
        Label currentScoreValueLabel = new Label(String.valueOf(Score.getCurrent_score()));
        Label bestValueLabel = new Label(String.valueOf(Score.getBest_score()));

        currentScoreLabel.setLayoutX(93.0);
        currentScoreLabel.setLayoutY(175.0);

        bestLabel.setLayoutX(131.0);
        bestLabel.setLayoutY(255.0);

        currentScoreValueLabel.setLayoutX(131.0);
        currentScoreValueLabel.setLayoutY(206.0);

        bestValueLabel.setLayoutX(131.0);
        bestValueLabel.setLayoutY(285.0);
        Font boldFont = Font.font("System Bold", 16.0);

        currentScoreLabel.setFont(boldFont);
        bestLabel.setFont(boldFont);
        currentScoreValueLabel.setFont(new Font(32.0));
        bestValueLabel.setFont(new Font(32.0));

        ImageView playImageView = new ImageView(new Image(getClass().getResourceAsStream("images/play_button_javafx.png")));
        ImageView homeImageView = new ImageView(new Image(getClass().getResourceAsStream("images/home_javafx.png")));
        ImageView restartImageView = new ImageView(new Image(getClass().getResourceAsStream("images/restart_javafx.png")));
        homeImageView.setLayoutX(34.0);
        homeImageView.setLayoutY(460.0);

        restartImageView.setLayoutX(218.0);
        restartImageView.setLayoutY(468.0);

        playImageView.setLayoutX(127.0);
        playImageView.setLayoutY(470.0);

        homeImageView.setFitWidth(73);
        homeImageView.setFitHeight(74);

        restartImageView.setFitWidth(63);
        restartImageView.setFitHeight(65);

        playImageView.setFitWidth(63);
        playImageView.setFitHeight(56);
        homeImageView.setOnMouseClicked(this::handleHomeClick);
        restartImageView.setOnMouseClicked(this::handleRestartClick);
        playImageView.setOnMouseClicked(event -> {
            // Handle play image click
            System.out.println("Play image clicked");
            // Remove nodes related to the Play image
            pane.getChildren().removeAll(re, score, currentScoreLabel, bestLabel, currentScoreValueLabel, bestValueLabel, homeImageView, restartImageView, playImageView);
            if (heroTransition != null) {
                heroTransition.play();
            }
            gamePaused = false;
            gameJustResumed = true;
        });

        pane.getChildren().add(re);
        pane.getChildren().add(score);
        pane.getChildren().addAll(currentScoreLabel, bestLabel, currentScoreValueLabel, bestValueLabel);
        pane.getChildren().addAll(homeImageView, restartImageView, playImageView);
    }

    private void handleHomeClick(MouseEvent event) {
        // Handle home image click
        System.out.println("Home image clicked");
        Score.setCurrent_score(0);
        SceneLoader s = SceneLoader.getInstance();

        s.loadscene(pane, "start_screne.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    private void handleRestartClick(MouseEvent event) {
        // Handle restart image click
        System.out.println("Restart image clicked");
        Score.setCurrent_score(0);
        SceneLoader s = SceneLoader.getInstance();

        s.loadscene(pane, "game_scene.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public Pillar AddRandomPillar() {
        //316 max reached
        Random r = new Random();
        int ra = r.nextInt(pillars.size());
        Pillar p = pillars.get(ra);
//        pillars.remove(ra);


//        p.rectangle=new Rectangle();
        return p;

    }

    public void handleCherryCollision() {
        cherry.onCollision();
        cherryimageView.setVisible(false);
        cherrySound.playMusic();
        System.out.println("Score is : " + Cherry.getCherries());
        Cherry.incrementCherries();
        cherryScore.setText(String.valueOf(Cherry.getCherries()));
        System.out.println("Score is : " + Cherry.getCherries());
    }

    public void handleObstacleCollision() {
        obstacle.onCollision();
//        obstacleSound.playMusic();
        TranslateTransition deathTransition = hero.onDeath();
        deathTransition.setOnFinished(Event -> {
            SceneLoader s = SceneLoader.getInstance();
            s.loadscene(pane, "game_end_scene.fxml", (Stage) pane.getScene().getWindow());
        });
        hero.playDeathSound();
        deathTransition.play();
    }

    public void handlePillarCollision() {
        System.out.println("Handling pillar collision!\n");
        TranslateTransition deathTransition = hero.onDeath();
        deathTransition.setOnFinished(Event -> {
            SceneLoader s = SceneLoader.getInstance();
            s.loadscene(pane, "game_end_scene.fxml", (Stage) pane.getScene().getWindow());
        });
        hero.playDeathSound();
        deathTransition.play();
    }

    public void checkCollision() {
        if (hero.getGroup().getBoundsInParent().intersects(cherryimageView.getBoundsInParent()) && !cherry.isClaimed()) {
            System.out.println("Collision Detected!");
            System.out.println("bound intersection val is " + hero.getGroup().getBoundsInParent().intersects(cherry.getImageView().getBoundsInParent()));
            System.out.println("cherry claimed val is " + cherry.isClaimed());
            handleCherryCollision();
            System.out.println("cherry claimed val is " + cherry.isClaimed());
        }
        if (hero.getGroup().getBoundsInParent().intersects(obstacleimageView.getBoundsInParent()) && !obstacle.didCrash()) {
            heroTransition.pause();
            System.out.println("Collision Detected!");
            System.out.println("bound intersection val is " + hero.getGroup().getBoundsInParent().intersects(obstacle.getImageView().getBoundsInParent()));
            System.out.println("obstacle claimed val is " + obstacle.didCrash());
            handleObstacleCollision();
            System.out.println("obstacle claimed val is " + obstacle.didCrash());
        }
        if (hero.getGroup().getBoundsInParent().intersects(r2.getBoundsInParent()) && !collidedWithPillar && heroInverted) {
            collidedWithPillar = true;
            heroTransition.stop();
            System.out.println("Pillar Collision Detected!");
            handlePillarCollision();
            System.out.println("Pillar collision handled!");
        }
    }

    @FXML
    private void initialize() {
        System.out.println("INIITALIZE CALLED!\n");
        cherryScore.setText(String.valueOf(Cherry.getCherries()));
        this.collidedWithPillar = false;
        pause_button.setOnMouseClicked(event -> {
            System.out.println("Image clicked");
            // Consume the event to prevent it from being propagated to the parent
            System.out.println("bruhhhhh");
            event.consume();
            // Call the specific function to handle the image click
        });
        this.initializeSounds();
//        bgMusic.playMusic();
        score_label.setText(String.valueOf(Score.getCurrent_score()));


        //every pillar has a default cordinates at the bottom of the screen , whcih will be changes in future

        pillars.add(new Pillar(184, 87, Color.BLACK, 114, 484));
        pillars.add(new Pillar(184, 30, Color.BLACK, SCREENWIDTH, 484));
        pillars.add(new Pillar(184, 50, Color.BLACK, SCREENWIDTH, 484));
        pillars.add(new Pillar(184, 70, Color.BLACK, SCREENWIDTH, 484));
        System.out.println(pillars);
        hero = new Hero();
        cherry = new Cherry();
        cherryimageView = cherry.getImageView();

        obstacle = new Obstacle();
        obstacleimageView = obstacle.getImageView();

        Pair<TranslateTransition, Rectangle> pillarpair = pillars.get(0).Transition(pillars.get(0).getXcordinate(), 0);
        Pair<TranslateTransition, Group> heropair = hero.returnTransition(pillars.get(0).getXcordinate(), pillarpair.second());
        pillar1 = pillars.get(0);
        firstwidth = pillarpair.second().getWidth();
//        pillars.remove(0);

        Pillar p2 = AddRandomPillar();
//        while(p2==pillars.get(0))
//        {
//            p2=this.AddRandomPillar();
//        }
//        pillar2=p2;
//        pillars.add(new Pillar(pillar1.height,pillar1.width,Color.BLACK,SCREENWIDTH,484));


//        System.out.println(pane.snapSizeX());
        Random r = new Random();
        int min = (int) pillarpair.second().getWidth() + 5;
        int max = SCREENWIDTH - p2.width();
        System.out.println(p2);
        System.out.println(r.nextInt((max - min)) + min);
        secondpos = r.nextInt((max - min)) + min; // second pillar


        Pair<TranslateTransition, Rectangle> pillarpair2 = p2.Transition(314, secondpos);
        secwidth = pillarpair2.second().getWidth();
//        Pair<TranslateTransition,Rectangle> pillarpair2=p2.Transition(p2.getXcordinate(),314-p2.width());


        pane.getChildren().add(pillarpair2.second());
        pane.getChildren().add(heropair.second());


        heropair.first().play();
        pillarpair.first().play();
        pillarpair2.first().play();
        r1 = pillarpair.second();
        r2 = pillarpair2.second();

        Random rVar = new Random();

        int min2 = (int) r1.getWidth() + 10;
        int max2 = secondpos - 25;
        int cherryPosition = 0;
        int obstaclePosition = 0;

        if (secondpos - r1.getWidth() >= 35) {
            System.out.println("Distance bw pillars > 35");
            cherryPosition = rVar.nextInt((max2 - min2)) + min2;
            obstaclePosition = rVar.nextInt((max2 - min2)) + min2;
            pane.getChildren().add(cherry.getImageView());
            cherryimageView = cherry.getImageView();
            obstacleimageView = obstacle.getImageView();
            cherry.getImageView().setX(cherryPosition);
            obstacle.getImageView().setX(obstaclePosition);
//            pane.getChildren().add(obstacle.getImageView());
        } else {
            System.out.println("DISTANCE LESS THAN 35!");
        }

        System.out.println("MAX2 " + max2 + " MIN2 " + min2);

        System.out.println("Cherry position is " + cherryPosition);


        // 450 is safe
//        cherry.getImageView().setY(450);
        cherry.getImageView().setY(500);

        pane.getChildren().add(pillarpair.second());

        curStickX = pillarpair.second().getWidth();
        curStickY = SCREENHEIGHT - pillarpair.second().getHeight();
        stick = new Stick(curStickX, curStickY);
//        sticks.add(stick);
//        System.out.println(pillarpair.second().getX());

        //adding timelines
        increaseTimeline = new Timeline(new KeyFrame(Duration.millis(16), event -> stick.extendStick()));
        increaseTimeline.setCycleCount(Timeline.INDEFINITE);

        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, this::handleMousePress);
        pane.addEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
        pane.addEventFilter(MouseEvent.MOUSE_CLICKED, this::handleMouseClick);

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

    private void handleMouseClick(MouseEvent event) {
        System.out.println("[Event] : Mouse Clicked");
        if (event.getX() <= 35 && event.getY() <= 35) {
        }
    }

    public void handleMouseReleased(MouseEvent event) {
        if (gamePaused) {
            return;
        }


        if (gameJustResumed) {
            System.out.println("Mouse released and game just resumed toggled");
            gameJustResumed = false;
            return;
        }

        if (mousePressed == 1) {
            System.out.println("[Event] : Mouse Released");
            stick.stickFall();
            increaseTimeline.pause();

        }


    }

    private void getHeroOffset() {
        ArrayList<Double> pillarDetails = getPillarDetails();
        Double heroX = hero.getImageView().localToScreen(0, 0).getX();
        heroOffset = heroX - pillarDetails.get(1);
    }

    public void setPillarTransition(boolean b) {
        this.pillarTranslationOver = b;
    }

    public boolean isPillarTranslating() {
        return this.pillarTranslationOver;
    }

    private void pillarTransition() {
        System.out.println("pillar transition");
        System.out.println(pillar1);
        System.out.println(r1);
        pane.getChildren().remove(r1);
        TranslateTransition transition = new TranslateTransition(Duration.millis(600), r2);
        transition.setOnFinished(e -> setPillarTransition(true));
        System.out.println(r2.getX());
        System.out.println(r2.getLayoutX());
        System.out.println(r2.localToScreen(0, 0).getX());
        System.out.println(secondpos);
        transition.setByX(-secondpos);
        firstwidth = r2.getWidth();

        Group group = hero.getGroup();
        TranslateTransition imageTransition = new TranslateTransition(Duration.seconds(0.6), group);
//        imageTransition.setFromX(secondpos);
//        imageTransition.setToX(0);
        imageTransition.setByX(-secondpos);
//        Pair<TranslateTransition,Rectangle> pillarpair=pillar2.Transition((int)r2.getX(),0);
//        pillarpair.first().play();
        Line rod = stick.getRod();
        TranslateTransition rodtransition = new TranslateTransition(Duration.seconds(0.6), rod);
        rodtransition.setByX(-secondpos);
        System.out.println(pillars);
        System.out.println(pillar2);
        Pillar p2 = AddRandomPillar();
//        Pillar p2=pillars.get(0);
        secwidth = r2.getWidth();
        System.out.println(p2);

//        System.out.println(p2);
        if (p2 == pillar1) {
            System.out.println("sdmfsd");
        }
        Random r = new Random();
        int min = (int) r2.getWidth() + 5;
        int max = SCREENWIDTH - p2.width();
        System.out.println("max" + max + "min" + min);
        secondpos = r.nextInt((max - min)) + min;
        System.out.println(p2.getXcordinate());
        Pair<TranslateTransition, Rectangle> pillarpair2 = p2.Transition(314, secondpos);
        System.out.println(pillarpair2.second());
//        secwidth=pillarpair2.second().getWidth();

        pane.getChildren().add(pillarpair2.second());

        setPillarTransition(false);
        transition.play();
        imageTransition.play();
        rodtransition.play();
        pillarpair2.first().play();
        Score.setCurrent_score(Score.getCurrent_score() + 1);
        score_label.setText(String.valueOf(Score.getCurrent_score()));
        r1 = r2;
        pillar1 = pillar2;
        pillar2 = p2;
        r2 = pillarpair2.second();
        mousePressed = 0;
        curStickX = r1.getWidth();
        curStickY = SCREENHEIGHT - r1.getHeight();
        sticks.add(stick);
        System.out.println(sticks);
        if (sticks.size() >= 2) {

            sticks.get(sticks.size() - 2).setVisible(false);
        }

//        stick.setVisible(false);
        stick = new Stick(curStickX, curStickY);
//        sticks.add(stick);

        Random rVar = new Random();

        transition.setOnFinished(e -> {
            int min3 = (int) r1.getWidth() + 40;
            int max3 = secondpos - 60;
            int obstaclePosition = 0;

            if (secondpos - r1.getWidth() >= 100) {
                System.out.println("Pillar dist " + secondpos + " , " + r1.getWidth());
                obstaclePosition = rVar.nextInt((max3 - min3)) + min3;
                pane.getChildren().add(obstacleimageView);
                obstacleimageView.setX(obstaclePosition);
                obstacleimageView.setY(450);
            }
        });

        cherry.resetClaimed();
        Cherry cherry1 = new Cherry();
        pane.getChildren().remove(cherryimageView);
        cherryimageView = cherry1.getNewImageView();

        obstacle.resetCrashed();
        Obstacle obstacle1 = new Obstacle();
        pane.getChildren().remove(obstacleimageView);
        obstacleimageView = obstacle1.getNewImageView();




        int min2 = (int) r1.getWidth() + 10;
        int max2 = secondpos - 25;
        int cherryPosition = 0;
        int obstaclePosition = 0;


        ImageView newIV = new ImageView();
        double pillarDist = secondpos - r1.getWidth();
        if (pillarDist >= 35) {
            System.out.println("Distance bw pillars > 35");
            cherryPosition = rVar.nextInt((max2 - min2)) + min2;
            pane.getChildren().add(cherryimageView);
            cherryimageView.setX(cherryPosition);
        } else {
            System.out.println("DISTANCE LESS THAN 35!");
        }

        System.out.println("MAX2 " + max2 + " MIN2 " + min2);

        System.out.println("Cherry position is " + cherryPosition);
        System.out.println("Obstacle position is " + obstaclePosition);


        // 450 is safe
//        cherry.getImageView().setY(450);
        cherryimageView.setY(500);

        // 450 is safe
//        obstacle.getImageView().setY(450);


    }

    public ArrayList<Double> getPillarDetails() {
        double rec1X = r1.localToScreen(0, 0).getX();
        double rec1Y = r1.localToScreen(0, 0).getY();
        double rec2X = r2.localToScreen(0, 0).getX();
        double rec2Y = r2.localToScreen(0, 0).getY();
        double rec1width = r1.getWidth();
        double rec2width = r2.getWidth();
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
        hero.setHeroInMotion(true);
        stickSound.stop();
        getHeroOffset();
        dropSound.playMusic();
        collisionTimer.start();
        double rec1X = r1.localToScreen(0, 0).getX();
        double rec1Y = r1.localToScreen(0, 0).getY();
        double rec2X = r2.localToScreen(0, 0).getX();
        double rec2Y = r2.localToScreen(0, 0).getY();
        double rec1width = r1.getWidth();
        double rec2width = r2.getWidth();
        double rightTopX = r2.getBoundsInParent().getMaxX();

        System.out.println(r1.getWidth());
        System.out.println("Rectangle R1");
        System.out.println("Rectangle position on screen: X=" + rec1X + ", Y=" + rec1Y);
        System.out.println("Rectangle position on screen: X=" + (rec1X + r1.getWidth()) + ", Y=" + rec1Y);

        System.out.println("Rectangle R2");
        System.out.println("Rectangle position on screen: X=" + rec2X + ", Y=" + rec2Y);
        System.out.println("Rectangle position on screen: X=" + (rec2X + r2.getWidth()) + ", Y=" + rec2Y);
        System.out.println(stick.stickLength);
        TranslateTransition deathTransition = hero.onDeath();
        deathTransition.setOnFinished(Event -> {
            SceneLoader s = SceneLoader.getInstance();
            s.loadscene(pane, "game_end_scene.fxml", (Stage) pane.getScene().getWindow());
        });

        if (rec1X + rec1width + stick.stickLength + 1.9 >= rec2X && rec1X + rec1width + stick.stickLength + 1.9 <= rec2X + rec2width) {


            System.out.println("Second pas " + secondpos);
            System.out.println("r2 pos " + r2.getLayoutX());
            System.out.println("r2 pos " + (r2.localToScreen(0, 0).getX() - r1.localToScreen(0, 0).getX()));
            System.out.println("chalega");


            Pair<TranslateTransition, ParallelTransition> p = hero.move(rightTopX - hero.getImageView().getX() - 30, false);
            TranslateTransition translate = p.first();
            heroTransition = p.second();
            Rectangle leg1 = hero.getLeg1();
            Rectangle leg2 = hero.getLeg2();
            translate.setOnFinished(event -> {
                // Stop the RotateTransition when translation is finished
                heroTransition.stop();
                hero.setHeroInMotion(false);
                RotateTransition r = new RotateTransition(Duration.seconds(0.0001), leg1);
                r.setToAngle(0);
//            r.setAutoReverse(true); // Rotate back and forth
//            r.setCycleCount(Timeline.INDEFINITE);
                r.play();
                r = new RotateTransition(Duration.seconds(0.0001), leg2);
                r.setToAngle(0);
//            r.setAutoReverse(true); // Rotate back and forth
//            r.setCycleCount(Timeline.INDEFINITE);
                r.play();
                r.setOnFinished(event1 -> {
                    pillarTransition();
                });
            });
            heroTransition.play();

        } else {
            System.out.println("nahi chalega");
            Pair<TranslateTransition, ParallelTransition> p = hero.move(stick.stickLength, true);
            TranslateTransition translate = p.first();
            heroTransition = p.second();
            Rectangle leg1 = hero.getLeg1();
            Rectangle leg2 = hero.getLeg2();
            translate.setOnFinished(event -> {
                // Stop the RotateTransition when translation is finished

                heroTransition.stop();
                RotateTransition r = new RotateTransition(Duration.seconds(0.0001), leg1);
                r.setToAngle(0);
//            r.setAutoReverse(true); // Rotate back and forth
//            r.setCycleCount(Timeline.INDEFINITE);
                r.play();
                r = new RotateTransition(Duration.seconds(0.0001), leg2);
                r.setToAngle(0);
//            r.setAutoReverse(true); // Rotate back and forth
//            r.setCycleCount(Timeline.INDEFINITE);
                r.play();
                r.setOnFinished(event1 -> {
                    hero.playDeathSound();
                    deathTransition.play();
                });
            });

            heroTransition.play();


        }


    }

    public void handleMousePress(MouseEvent event) {
        if (gamePaused) {
            return;
        }

        System.out.println("[Event] : Mouse Pressed");
        if (event.getX() <= 35 && event.getY() <= 35) {
            System.out.println("Pause Button Pressed");
            gamePaused = true;
            pausegame();
            return;
        }

        if (gameJustResumed) {
            System.out.println("Game just resumed going from true to false");
            gameJustResumed = false;
            return;
        }

        if (heroReachedPillar) {
            return;
        }


//        System.out.println("Mouse pressed val is " + mousePressed);
//        System.out.println("Mouse Pressed at X: " + event.getX() + ", Y: " + event.getY());
//
//        if (event.getX() <= 35 && event.getY() <= 35) {
//            System.out.println("Pause Button Pressed");
//            return;
//        }

        mousePressed += 1;


        if (mousePressed == 1) {
            stick.setVisible(true);
            if (!stickSound.isPlaying()) {
                stickSound.playMusic();
            }
            increaseTimeline.play();
        } else if (mousePressed > 1) {
            System.out.println("rotatiin called");

//
            System.out.println("Rotating!");
            Group group = hero.getGroup();
            ArrayList<Double> pillarDetails = getPillarDetails();
            Double heroX = hero.getImageView().localToScreen(0, 0).getX();
            Double groupX = hero.getGroup().localToScreen(0, 0).getX();

//            System.out.println("INVERT DETAILS");
//            System.out.println("hero x is " + heroX);
//            System.out.println("hero group x is " + groupX);
//            System.out.println("Pillar details are " + pillarDetails);
//            heroX = heroX + heroOffset;
//
//            if (heroX > pillarDetails.get(0) && heroX < pillarDetails.get(1)) {
//                System.out.println("Hero is on pillar1 so won't rotate");
//                return;
//            } else if (heroX > pillarDetails.get(2) && heroX < pillarDetails.get(3)){
//                System.out.println("Hero is on pillar2 so won't rotate");
//                return;
//            }

            group.setRotate(group.getRotate() + 180);
            heroInverted = !heroInverted;
            if (!heroInverted) {
                group.setLayoutY(group.getLayoutY() - 30);
            } else {
                group.setLayoutY(group.getLayoutY() + 30);
            }
        }

    }

    private class Stick extends PositionDimension implements Collidable {
        Line rod;
        private double stickLength = 0.0;

        public Stick(double curStickX, double curStickY) {
            rod = new Line(curStickX, curStickY, curStickX, curStickY);
            pane.getChildren().add(rod);
            rod.setVisible(false);
            rod.setStrokeWidth(3);

        }

        public Line getRod() {
            return rod;
        }

        public void setVisible(Boolean b) {
            rod.setVisible(b);
        }

        @Override
        public void add_to_screen(Pane pane, int start_position, int end_position, Hero hero) {

        }

        @Override
        public boolean didCollide(Collidable o) {
            return false;
        }

        @Override
        public void onCollision() {

        }

        public void extendStick() {
            System.out.println("Increase stick length called!");
            stickLength += INCREASE_AMOUNT;
//        stick.setEndY(stickLength);
            // curStic
            rod.setEndY(curStickY - stickLength);

        }

        public void stickFall() {
            if (hero.isHeroInMotion()) {
                return;
            }
            Rotate rotation = new Rotate();
            rotation.pivotXProperty().bind(rod.startXProperty());
            rotation.pivotYProperty().bind(rod.startYProperty());
            rod.getTransforms().add(rotation);
            Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 0)), new KeyFrame(Duration.seconds(0.5), new KeyValue(rotation.angleProperty(), 90)));
            timeline.setOnFinished(e -> makeHeroMove());
            timeline.play();
            System.out.println("TIME LINE PLAYED");
            System.out.println("TIME LINE OVER");

        }
    }


}