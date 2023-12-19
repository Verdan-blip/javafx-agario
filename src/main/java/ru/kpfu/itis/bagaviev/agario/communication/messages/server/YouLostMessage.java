package ru.kpfu.itis.bagaviev.agario.communication.messages.server;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;

public class YouLostMessage extends Message {

    private final Integer agarOwnerId;

    private void fillBuffer() {
        buffer.put((byte) getMessageType());
        buffer.putInt(agarOwnerId);
    }

    public YouLostMessage(Integer agarOwnerId) {
        super();
        this.agarOwnerId = agarOwnerId;
        fillBuffer();
    }

    public Integer getAgarOwnerId() {
        return agarOwnerId;
    }

    @Override
    public int getMessageType() {
        return MessageTypes.YOU_LOST;
    }

}
