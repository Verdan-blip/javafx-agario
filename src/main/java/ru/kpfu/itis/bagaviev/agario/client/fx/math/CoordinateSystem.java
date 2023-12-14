package ru.kpfu.itis.bagaviev.agario.client.fx.math;

public class CoordinateSystem {

    private final float screenWidth;
    private final float screenHeight;

    public CoordinateSystem(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public float xToFxCoordinates(float x) {
        return x + screenWidth / 2;
    }

    public float yToFxCoordinates(float y) {
        return -y + screenHeight / 2;
    }

    public float fxXtoDefaultCoordinates(float fxX) {
        return fxX - screenWidth / 2;
    }

    public float fxYtoDefaultCoordinates(float fxY) {
        return -fxY + screenHeight / 2;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

}
