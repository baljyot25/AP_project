package com.example.ap_project;

import javafx.scene.control.Label;

public class Score {
    private static int best_score;

    public static int getBest_score() {
        return best_score;
    }

    public static void setBest_score(int best_score) {
        Score.best_score = best_score;
    }

    public static void setCurrent_score(int current_score) {
        Score.current_score = current_score;
    }

    public static int getCurrent_score() {
        return current_score;
    }

    private static int current_score=0;

    public void increment_current_score()
    {
        current_score+=1;
    }

    public void  is_best()
    {
        //checks if current score is the best score;
    }

    public void set_score(Label label,int score)
    {
        label.setText(String.valueOf(score));
    }
}
