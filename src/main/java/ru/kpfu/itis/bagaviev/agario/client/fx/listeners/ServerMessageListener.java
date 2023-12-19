package ru.kpfu.itis.bagaviev.agario.client.fx.listeners;

import javafx.application.Platform;
import ru.kpfu.itis.bagaviev.agario.client.fx.frames.GameFrame;
import ru.kpfu.itis.bagaviev.agario.communication.listeners.MessageListener;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.*;

public class ServerMessageListener implements MessageListener {
    private final GameFrame gameFrame;

    public ServerMessageListener(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    private void handleMessage(Message message) {
        switch (message.getMessageType()) {
            case MessageTypes.YOU_REGISTERED -> {
                YouRegisteredMessage youRegisteredMessage = (YouRegisteredMessage) message;
                gameFrame.startGame(youRegisteredMessage.getAgarOwnerId(), youRegisteredMessage.getNickname());
            }
            case MessageTypes.OTHER_REGISTERED -> {
                OtherRegisteredMessage otherRegisteredMessage = (OtherRegisteredMessage) message;
                gameFrame.addAgarOwner(otherRegisteredMessage.getAgarOwnerId(), otherRegisteredMessage.getNickname());
            }
            case MessageTypes.YOU_LOST -> {
                YouLostMessage youLostMessage = (YouLostMessage) message;
                gameFrame.removeAgarOwner(youLostMessage.getAgarOwnerId());
            }
            case MessageTypes.AGAR_CREATED -> {
                AgarCreatedMessage agarCreatedMessage = (AgarCreatedMessage) message;
                gameFrame.addAgar(agarCreatedMessage.getAgarItem());
            }
            case MessageTypes.UPDATE_AGAR -> {
                UpdateAgarMessage updateAgarMessage = (UpdateAgarMessage) message;
                gameFrame.updateAgar(updateAgarMessage.getAgarItem());
            }
            case MessageTypes.UPDATE_FOOD -> {
                UpdateFoodMessage updateFoodMessage = (UpdateFoodMessage) message;
                gameFrame.updateFood(
                        updateFoodMessage.getFoodId(),
                        updateFoodMessage.getFood()
                );
            }
            case MessageTypes.AGAR_WAS_EATEN -> {
                AgarWasEatenMessage agarWasEatenMessage = (AgarWasEatenMessage) message;
                gameFrame.removeAgar(agarWasEatenMessage.getAgarId());
            }
        }
    }

    @Override
    public void onMessagePerformed(Message message) {
        Platform.runLater(() -> handleMessage(message));
    }

}
