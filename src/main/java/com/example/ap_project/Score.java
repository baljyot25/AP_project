package com.example.ap_project;

import javafx.scene.control.Label;

public class Score {
    private static int best_score;
    private static int current_score = 0;

    public Score() {
        current_score = 0;
    }

    public static int getBest_score() {
        return best_score;
    }

    public static void setBest_score(int best_score) {
        Score.best_score = best_score;
    }

    public static int getCurrent_score() {
        return current_score;
    }

    public static void setCurrent_score(int current_score) {
        Score.current_score = current_score;
    }

    public static void is_best() {
        if (current_score >= best_score) {
            best_score = current_score;
        }
        //checks if current score is the best score;
    }

    public int getScore() {
        return current_score;
    }

    public void increment_current_score() {
        current_score += 1;
    }

    public void set_score(Label label, int score) {
        label.setText(String.valueOf(score));
    }
}
