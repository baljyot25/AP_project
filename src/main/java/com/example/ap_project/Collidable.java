package com.example.ap_project;

public interface Collidable {
    public boolean didCollide(Collidable o);
    public void onCollision();
}
