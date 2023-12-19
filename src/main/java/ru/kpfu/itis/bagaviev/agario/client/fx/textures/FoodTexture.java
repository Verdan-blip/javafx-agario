package ru.kpfu.itis.bagaviev.agario.client.fx.textures;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class FoodTexture {

    private static final float RADIUS_TO_MASS_RATIO = 9f;

    private final Circle circle;
    private final Paint paint;

    public FoodTexture(Paint paint) {
        this.circle = new Circle();
        this.paint = paint;
        circle.setFill(paint);
    }

    public Circle getCircle() {
        return circle;
    }

    public void setPosition(float x, float y) {
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    public void setMass(float foodMass) {
        circle.setRadius(foodMass * RADIUS_TO_MASS_RATIO);
    }

    public Paint getPaint() {
        return paint;
    }

    public float getX() {
        return (float) circle.getCenterX();
    }

    public float getY() {
        return (float) circle.getCenterY();
    }

    public float getMas() {
        return (float) circle.getRadius();
    }

}
