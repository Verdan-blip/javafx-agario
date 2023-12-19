package ru.kpfu.itis.bagaviev.agario.communication.messages.client;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;

public class SplitAgarMessage extends Message {

    private final Integer agarOwnerId;

    private void fillBuffer() {
        buffer.put((byte) getMessageType());
        buffer.putInt(agarOwnerId);
    }

    public SplitAgarMessage(Integer agarOwnerId) {
        super();
        this.agarOwnerId = agarOwnerId;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.SPLIT_AGAR;
    }

    public Integer getAgarOwnerId() {
        return agarOwnerId;
    }

}
