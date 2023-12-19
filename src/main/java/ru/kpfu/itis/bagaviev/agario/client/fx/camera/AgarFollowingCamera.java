package ru.kpfu.itis.bagaviev.agario.client.fx.camera;

import javafx.scene.Parent;

public class AgarFollowingCamera {

    private final Parent background;

    private double centerX;
    private double centerY;
    private double zoomFactor;
    private double viewPortWidth;
    private double viewPortHeight;


    public AgarFollowingCamera(Parent background, double viewPortWidth, double viewPortHeight) {
        this.background = background;

        this.zoomFactor = 1f;

        this.viewPortWidth = viewPortWidth;
        this.viewPortHeight = viewPortHeight;
    }

    public void setCenter(double x, double y) {
        this.centerX = x;
        this.centerY = y;

        double deltaByScaleX = viewPortWidth - viewPortWidth * zoomFactor;
        double deltaByScaleY = viewPortHeight - viewPortHeight * zoomFactor;

        background.setTranslateX((viewPortWidth / 2 - x) * zoomFactor - deltaByScaleX / 4);
        background.setTranslateY((viewPortHeight / 2 - y) * zoomFactor - deltaByScaleY / 4);
    }

    public void setZoom(double zoomFactor) {
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
