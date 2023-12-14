package ru.kpfu.itis.bagaviev.agario.engine.objects;

public class Agar {

    private float x, y;
    private float dirX, dirY;
    private float mass;
    private float velocity;

    public Agar(float x, float y, float dirX, float dirY, float velocity, float mass) {
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
        this.mass = mass;
        this.velocity = velocity;
    }

    public void move() {
        float dx = dirX * velocity;
        float dy = dirY * velocity;
        x += dx;
        y += dy;
    }

    public void setDirection(float dirX, float dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
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

    public float getDirX() {
        return dirX;
    }

    public float getDirY() {
        return dirY;
    }

    public float getMass() {
        return mass;
    }

    public float getVelocity() {
        return velocity;
    }
}
