package ru.kpfu.itis.bagaviev.agario.engine.objects;

import javafx.scene.paint.Color;

public class Food {

    private float x;
    private float y;
    private float mass;
    private Color color;

    public Food(float x, float y, float mass, Color color) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getMass() {
        return mass;
    }
}
