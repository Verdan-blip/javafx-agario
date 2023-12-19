package ru.kpfu.itis.bagaviev.agario.server.net.listeners;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.RegisterMeMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.SplitAgarMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.UpdateDirectionMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.OtherRegisteredMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.YouRegisteredMessage;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorld;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorldConstants;
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

                Integer agarOwnerId = agarWorld.createAgarOwner(registerMeMessage.getNickname());
                String nickname = registerMeMessage.getNickname();

                //Returning result to client which wanted to register
                server.sendMessage(sessionId, new YouRegisteredMessage(agarOwnerId, nickname));

                //Notifying all clients that new client joined
                server.sendBroadcastMessage(sessionId, new OtherRegisteredMessage(agarOwnerId, nickname));

                //Agar owner needs at least one agar
                agarWorld.createAgarItem(agarOwnerId);
            }

            case MessageTypes.UPDATE_DIRECTION -> {
                UpdateDirectionMessage updateDirectionMessage = (UpdateDirectionMessage) message;
                Integer agarId = updateDirectionMessage.getAgarId();
                float newDirX = updateDirectionMessage.getNewDirX();
                float newDirY = updateDirectionMessage.getNewDirY();
                float velocity = (float) Math.sqrt(newDirX * newDirX + newDirY * newDirY) *
                        AgarWorldConstants.AGAR_VELOCITY_COEFFICIENT;
                agarWorld.updateAgar(agarId, newDirX, newDirY, velocity);
            }

            case MessageTypes.SPLIT_AGAR -> {
                SplitAgarMessage splitAgarMessage = (SplitAgarMessage) message;
                Integer agarOwnerId = splitAgarMessage.getAgarOwnerId();
                agarWorld.splitAgars(agarOwnerId);
            }

        }
    }

    public void onMessagePerformed(Integer sessionId, Message message) {
        handleMessage(sessionId, message);
    }

}
