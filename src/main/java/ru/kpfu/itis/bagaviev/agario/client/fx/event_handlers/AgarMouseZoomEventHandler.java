package ru.kpfu.itis.bagaviev.agario.client.fx.event_handlers;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import ru.kpfu.itis.bagaviev.agario.client.fx.camera.AgarFollowingCamera;

public class AgarMouseZoomEventHandler implements EventHandler<ScrollEvent> {

    private static final double CAMERA_ZOOMING_TO_SCROLL_ZOOMING_RATIO = 1 / 80d;

    private final AgarFollowingCamera camera;

    public AgarMouseZoomEventHandler(AgarFollowingCamera camera) {
        this.camera = camera;
    }

    @Override
    public void handle(ScrollEvent scrollEvent) {
        double deltaZoom = scrollEvent.getDeltaY() * CAMERA_ZOOMING_TO_SCROLL_ZOOMING_RATIO;
        camera.setZoom(camera.getZoomFactor() + deltaZoom);
    }

}
