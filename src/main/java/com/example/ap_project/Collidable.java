package com.example.ap_project;

public interface Collidable {
    boolean didCollide(Collidable o);

    void onCollision();
}
