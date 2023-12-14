package ru.kpfu.itis.bagaviev.agario.client.fx.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AgarTexture {

    private static final float RADIUS_TO_MASS_RATIO = 1f;
    private static final float TEXT_SIZE_TO_MASS_RATIO = 0.5f;

    private final Circle circle;
    private final Text text;

    public AgarTexture(String nickname, Color color) {
        circle = new Circle();
        circle.setFill(color);

        text = new Text(nickname);
        text.setFill(Color.WHITE);
    }

    public void setPosition(float x, float y) {
        circle.setCenterX(x);
        circle.setCenterY(y);
        text.setTranslateX(x - text.getLayoutBounds().getWidth() / 2);
        text.setTranslateY(y + text.getLayoutBounds().getHeight() / 4);
    }

    public void setScale(float newAgarMass) {
        circle.setRadius(newAgarMass * RADIUS_TO_MASS_RATIO);
        text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, newAgarMass * TEXT_SIZE_TO_MASS_RATIO));
    }

    public Circle getCircle() {
        return circle;
    }

    public Text getText() {
        return text;
    }

    public float getPositionX() {
        return (float) circle.getCenterX();
    }

    public float getPositionY() {
        return (float) circle.getCenterY();
    }

    public float getScaleValue() {
        return (float) circle.getRadius();
    }

}
