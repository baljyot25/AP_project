package com.example.ap_project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
public class RotateTester extends ApplicationTest{

    private Timeline t;
    private int a;

    @Override
    public void start(Stage stage) {
        a = 0;
        t = new Timeline(new KeyFrame(Duration.seconds(2), event -> incrementA()));
//        stage.setScene(new Scene());
//        stage.show();
    }

    private void incrementA() {
        a += 2;
    }

//    @BeforeEach
//    public void init() {
//        rectangle.relocate(0, 0);
//    }

    @Test
    public void testTimeline() throws InterruptedException {
        t.play();
        sleep(3000);
        assertEquals(2, a);
    }


}