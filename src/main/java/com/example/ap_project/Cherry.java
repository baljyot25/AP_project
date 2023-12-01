package com.example.ap_project;


import javafx.scene.control.Label;

public class Cherry implements Collidable {
    private static int cherries;//will never be reset to zero
    private int cherriesToRevive=10;

    public Cherry()
    {}

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
