package ru.kpfu.itis.bagaviev.agario.client.fx.handlers;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import ru.kpfu.itis.bagaviev.agario.client.fx.camera.AgarFollowingCamera;

public class AgarMouseZoomEventHandler implements EventHandler<ScrollEvent> {

    private static final float CAMERA_ZOOMING_TO_SCROLL_ZOOMING_RATIO = 1 / 80f;

    private final AgarFollowingCamera camera;

    public AgarMouseZoomEventHandler(AgarFollowingCamera camera) {
        this.camera = camera;
    }

    @Override
    public void handle(ScrollEvent scrollEvent) {
        float deltaZoom = (float) scrollEvent.getDeltaY() * CAMERA_ZOOMING_TO_SCROLL_ZOOMING_RATIO;
        camera.setZoom(camera.getZoomFactorX() + deltaZoom, camera.getZoomFactorY() + deltaZoom);
    }

}
