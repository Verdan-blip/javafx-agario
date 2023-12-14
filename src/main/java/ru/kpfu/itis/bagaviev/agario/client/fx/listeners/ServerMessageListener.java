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
                gameFrame.startGameWith(
                        youRegisteredMessage.getId(),
                        youRegisteredMessage.getAgar()
                );
                gameFrame.hideConnectFrame();
            }
            case MessageTypes.PLAYER_REGISTERED -> {
                PlayerRegisteredMessage playerRegisteredMessage = (PlayerRegisteredMessage) message;
                gameFrame.addAgar(
                        playerRegisteredMessage.getId(),
                        playerRegisteredMessage.getNickname(),
                        playerRegisteredMessage.getAgar()
                );
            }
            case MessageTypes.UPDATE_AGAR -> {
                UpdateAgarMessage updateAgarMessage = (UpdateAgarMessage) message;
                gameFrame.updateAgar(updateAgarMessage.getAgarId(), updateAgarMessage.getAgar());
            }
            case MessageTypes.UPDATE_FOOD -> {
                UpdateFoodMessage updateFoodMessage = (UpdateFoodMessage) message;
                gameFrame.updateFood(
                        updateFoodMessage.getFoodId(),
                        updateFoodMessage.getFood()
                );
            }
            case MessageTypes.PLAYER_WAS_EATEN -> {
                PlayerWasEatenMessage playerWasEatenMessage = (PlayerWasEatenMessage) message;
                gameFrame.removeAgar(playerWasEatenMessage.getAgarId());
            }
        }
    }

    @Override
    public void onMessagePerformed(Message message) {
        Platform.runLater(() -> handleMessage(message));
    }

}
