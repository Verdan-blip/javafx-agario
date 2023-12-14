package ru.kpfu.itis.bagaviev.agario.server.net.listeners;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.RegisterMeMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.UpdateDirectionMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.PlayerRegisteredMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.UpdateAgarMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.UpdateFoodMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.YouRegisteredMessage;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorld;
import ru.kpfu.itis.bagaviev.agario.server.net.Server;

public class ClientMessageListener {

    private final Server server;
    private final AgarWorld agarWorld;

    public ClientMessageListener(Server server, AgarWorld agarWorld) {
        this.server = server;
        this.agarWorld = agarWorld;
    }

    private void handleMessage(Integer sessionId, Message message) {
        switch (message.getMessageType()) {
            case MessageTypes.REGISTER_ME -> {
                RegisterMeMessage registerMeMessage = (RegisterMeMessage) message;

                AgarItem agarItem = agarWorld.createAgar(registerMeMessage.getNickname());

                //Returning result to client which wanted to register
                server.sendMessage(sessionId, new YouRegisteredMessage(
                        agarItem.getId(),
                        agarItem.getAgar())
                );
                //Notifying all clients that new client joined
                server.sendBroadcastMessage(sessionId, new PlayerRegisteredMessage(
                        agarItem.getId(),
                        agarItem.getNickname(),
                        agarItem.getAgar())
                );

                //Sending to client information about agars
                agarWorld.forAllAgarItems(otherAgarItem ->
                    server.sendMessage(sessionId, new UpdateAgarMessage(otherAgarItem.getId(), otherAgarItem.getAgar()))
                );

                //Sending to client information about feed
                agarWorld.forAllFeed((id, food) ->
                    server.sendMessage(sessionId, new UpdateFoodMessage(id, food))
                );

            }
            case MessageTypes.UPDATE_DIRECTION_MESSAGE -> {
                UpdateDirectionMessage updateDirectionMessage = (UpdateDirectionMessage) message;
                Integer id = updateDirectionMessage.getId();
                float newDirX = updateDirectionMessage.getNewDirX();
                float newDirY = updateDirectionMessage.getNewDirY();
                agarWorld.updateAgarDirection(id, newDirX, newDirY);
            }
        }
    }

    public void onMessagePerformed(Integer sessionId, Message message) {
        handleMessage(sessionId, message);
    }

}
