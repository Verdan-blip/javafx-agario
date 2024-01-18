package ru.kpfu.itis.bagaviev.agario.client.fx.camera;

import javafx.scene.Parent;

public class GameCamera {

    private final Parent background;
    private final double width;
    private final double height;

    private double centerX;
    private double centerY;
    private double zoomFactor;

    private double backgroundOffsetX;
    private double backgroundOffsetY;

    public GameCamera(Parent background, double width, double height) {
        this.background = background;
        this.zoomFactor = 1f;
        this.width = width;
        this.height = height;
    }

    public void setCenter(double x, double y) {
        background.setTranslateX((width / 2 - x) * zoomFactor + backgroundOffsetX / 2);
        background.setTranslateY((height / 2 - y) * zoomFactor + backgroundOffsetY / 2);
        centerX = x;
        centerY = y;
    }

    public void setZoom(double zoomFactor) {

        backgroundOffsetX = (width * zoomFactor - width) + 128 * zoomFactor;
        backgroundOffsetY = (height * zoomFactor - height) + 128 * zoomFactor;

        background.setScaleX(zoomFactor);
        background.setScaleY(zoomFactor);
        this.zoomFactor = zoomFactor;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

}
