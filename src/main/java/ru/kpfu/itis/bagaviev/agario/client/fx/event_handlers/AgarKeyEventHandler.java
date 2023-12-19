package ru.kpfu.itis.bagaviev.agario.client.fx.event_handlers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.kpfu.itis.bagaviev.agario.client.net.Client;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.SplitAgarMessage;

public class AgarKeyEventHandler implements EventHandler<KeyEvent> {

    private final Integer agarOwnerId;
    private final Client client;

    public AgarKeyEventHandler(Integer agarOwnerId, Client client) {
        this.agarOwnerId = agarOwnerId;
        this.client = client;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            client.sendMessage(new SplitAgarMessage(agarOwnerId));
        }
    }

}
