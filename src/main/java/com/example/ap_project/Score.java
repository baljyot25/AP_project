package com.example.ap_project;

import javafx.scene.control.Label;

public class Score {
    private static int best_score;
    private int current_score;

    public int getScore() {
        return this.current_score;
    }

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

    public Score() {
        this.current_score = 0;
    }
}
