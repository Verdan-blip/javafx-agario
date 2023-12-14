package ru.kpfu.itis.bagaviev.agario.client.fx.camera;

import javafx.scene.Parent;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;

public class AgarFollowingCamera {

    private static final float ZOOM_FACTOR_TO_AGAR_MASS_RATIO = 25f;
    private static final float MAX_ZOOM_FACTOR_TO_MIN_ZOOM_FACTOR_RATIO = 5f;

    private final Parent background;

    private float zoomFactorX;
    private float zoomFactorY;

    private float minZoomFactorX;
    private float minZoomFactorY;
    private float maxZoomFactorX;
    private float maxZoomFactorY;

    public AgarFollowingCamera(Parent background) {
        this.background = background;

        this.zoomFactorX = 1f;
        this.zoomFactorY = 1f;

        this.minZoomFactorX = 1f;
        this.minZoomFactorY = 1f;

        this.maxZoomFactorX = 1f;
        this.maxZoomFactorY = 1f;
    }

    public void set(float x, float y) {
        background.setTranslateX(-x);
        background.setTranslateY(y);
    }

    public void setZoom(float scaleX, float scaleY) {

        if (scaleX < minZoomFactorX) scaleX = minZoomFactorX;
        if (scaleY < minZoomFactorY) scaleY = minZoomFactorY;

        if (scaleX > maxZoomFactorX) scaleX = maxZoomFactorX;
        if (scaleY > maxZoomFactorY) scaleY = maxZoomFactorY;

        this.zoomFactorX = scaleX;
        this.zoomFactorY = scaleY;

        background.setScaleX(scaleX);
        background.setScaleY(scaleY);
    }

    public void update(Agar agar) {

        minZoomFactorX = 1 / agar.getMass() * ZOOM_FACTOR_TO_AGAR_MASS_RATIO;
        minZoomFactorY = 1 / agar.getMass() * ZOOM_FACTOR_TO_AGAR_MASS_RATIO;

        maxZoomFactorX = minZoomFactorX * MAX_ZOOM_FACTOR_TO_MIN_ZOOM_FACTOR_RATIO;
        maxZoomFactorY = minZoomFactorY * MAX_ZOOM_FACTOR_TO_MIN_ZOOM_FACTOR_RATIO;

        background.setTranslateX(-agar.getX() * zoomFactorX);
        background.setTranslateY(agar.getY() * zoomFactorY);
    }

    public float getZoomFactorX() {
        return zoomFactorX;
    }

    public float getZoomFactorY() {
        return zoomFactorY;
    }

}
