package ru.kpfu.itis.bagaviev.agario.communication.messages.server;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;

public class YouRegisteredMessage extends Message {
    private final Integer agarOwnerId;
    private final String nickname;

    private void fillBuffer() {
        buffer.put((byte) getMessageType());
        buffer.putInt(agarOwnerId);

        buffer.put((byte) nickname.length());
        buffer.put(nickname.getBytes());
    }

    public YouRegisteredMessage(Integer agarOwnerId, String nickname) {
        super();
        this.agarOwnerId = agarOwnerId;
        this.nickname = nickname;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.YOU_REGISTERED;
    }

    public Integer getAgarOwnerId() {
        return agarOwnerId;
    }

    public String getNickname() {
        return nickname;
    }

}
