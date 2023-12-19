package ru.kpfu.itis.bagaviev.agario.communication.messages.server;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;

public class AgarWasEatenMessage extends Message {

    private final Integer agarId;

    private void fillBuffer() {
        buffer.put((byte) getMessageType());
        buffer.putInt(agarId);
    }

    public AgarWasEatenMessage(Integer agarId) {
        super();
        this.agarId = agarId;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.AGAR_WAS_EATEN;
    }

    public Integer getAgarId() {
        return agarId;
    }

}
