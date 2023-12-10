package com.example.ap_project;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import java.util.HashMap;
import java.util.Map;

public class GameSceneControllerTest extends ApplicationTest {

    private Rectangle rectangle;
    private TranslateTransition t;
    private TranslateTransition tY;
    private static final double SPEEDMS = 100;

    @Override
    public void start(Stage stage) {
        rectangle = new Rectangle(0, 0, 10, 10);
        t = new TranslateTransition(Duration.seconds(2), rectangle);
        t.setByX(10);
        tY = new TranslateTransition(Duration.seconds(2), rectangle);
        tY.setByY(10);
        stage.setScene(new Scene(new StackPane(rectangle), 100, 100));
//        stage.show();
    }

//    @BeforeEach
//    public void init() {
//        rectangle.relocate(0, 0);
//    }

    @Test
    public void testMoveX() {
        t.play();
        t.setOnFinished(e -> {
            assertEquals(rectangle.getX(), 10);
        });
    }

    @Test
    public void testMoveY() {
        tY.play();
        tY.setOnFinished(e -> {
            assertEquals(rectangle.getY(), 10);
        });
    }

}