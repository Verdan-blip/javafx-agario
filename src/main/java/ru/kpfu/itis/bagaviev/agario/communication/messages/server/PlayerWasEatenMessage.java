package ru.kpfu.itis.bagaviev.agario.communication.messages.server;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;

public class PlayerWasEatenMessage extends Message {

    private final Integer agarId;

    private void fillBuffer() {
        buffer.put((byte) getMessageType());
        buffer.putInt(agarId);
    }

    public PlayerWasEatenMessage(Integer agarId) {
        this.agarId = agarId;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.PLAYER_WAS_EATEN;
    }

    public Integer getAgarId() {
        return agarId;
    }

}
