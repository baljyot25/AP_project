package com.example.ap_project;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class PositionDimension {
    protected int width, height, xcordinate, ycordinate;
    protected Color color;

    public int getXcordinate() {
        return xcordinate;
    }

    public abstract void add_to_screen(Pane pane, int start_position, int end_position, Hero hero);

    public int width() {
        return width;
    }
}
