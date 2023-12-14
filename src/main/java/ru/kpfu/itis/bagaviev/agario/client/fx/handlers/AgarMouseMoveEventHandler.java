package ru.kpfu.itis.bagaviev.agario.client.fx.handlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ru.kpfu.itis.bagaviev.agario.client.fx.objects.AgarTexture;
import ru.kpfu.itis.bagaviev.agario.client.fx.objects.Player;
import ru.kpfu.itis.bagaviev.agario.client.net.Client;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.UpdateDirectionMessage;

public class AgarMouseMoveEventHandler implements EventHandler<MouseEvent> {
    private final Client client;
    private final Player player;

    public AgarMouseMoveEventHandler(Client client, Player player) {
        this.client = client;
        this.player = player;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        float mousePosX = (float) mouseEvent.getX();
        float mousePosY = (float) mouseEvent.getY();

        Integer agarId = player.getAgarId();
        AgarTexture agarTexture = player.getAgarTexture();

        float dirVectorX = (mousePosX - agarTexture.getPositionX());
        float dirVectorY = (agarTexture.getPositionY() - mousePosY);

        float len = (float) Math.sqrt(dirVectorX * dirVectorX + dirVectorY * dirVectorY);

        float dirX = dirVectorX / len;
        float dirY = dirVectorY / len;

        client.sendMessage(new UpdateDirectionMessage(agarId, dirX, dirY));
    }


}
