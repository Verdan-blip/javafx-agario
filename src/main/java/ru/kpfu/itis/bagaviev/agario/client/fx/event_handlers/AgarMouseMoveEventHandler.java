package ru.kpfu.itis.bagaviev.agario.client.fx.event_handlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ru.kpfu.itis.bagaviev.agario.client.fx.objects.AgarTextureItem;
import ru.kpfu.itis.bagaviev.agario.client.fx.textures.AgarTexture;
import ru.kpfu.itis.bagaviev.agario.client.net.Client;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.UpdateDirectionMessage;

import java.util.Map;

public class AgarMouseMoveEventHandler implements EventHandler<MouseEvent> {

    private final float velocityRadius;
    private final Integer myAgarOwnerId;
    private final Map<Integer, AgarTextureItem> agarTextureItemMap;
    private final Client client;

    public AgarMouseMoveEventHandler(float screenWidth, float screenHeight,
                                     Integer myAgarOwnerId, Client client,
                                     Map<Integer, AgarTextureItem> agarTextureItemMap) {

        this.velocityRadius = Math.min(screenWidth, screenHeight) / 16;

        this.myAgarOwnerId = myAgarOwnerId;
        this.client = client;
        this.agarTextureItemMap = agarTextureItemMap;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        for (var pair : agarTextureItemMap.entrySet()) {

            Integer agarId = pair.getKey();
            AgarTextureItem agarTextureItem = pair.getValue();

            Integer agarOwnerId = agarTextureItem.getAgarOwnerId();

            if (agarOwnerId.equals(this.myAgarOwnerId)) {

                AgarTexture agarTexture = agarTextureItem.getAgarTexture();

                float mousePosX = (float) mouseEvent.getX();
                float mousePosY = (float) mouseEvent.getY();

                float dirVectorX = (mousePosX - agarTexture.getPositionX());
                float dirVectorY = (agarTexture.getPositionY() - mousePosY);

                float dirVectorLength = (float) Math.sqrt(dirVectorX * dirVectorX + dirVectorY * dirVectorY);

                if (dirVectorLength > velocityRadius) {
                    dirVectorX /= dirVectorLength;
                    dirVectorY /= dirVectorLength;
                } else {
                    dirVectorX /= velocityRadius;
                    dirVectorY /= velocityRadius;
                }

                client.sendMessage(new UpdateDirectionMessage(agarId, dirVectorX, dirVectorY));
            }
        }
    }


}
