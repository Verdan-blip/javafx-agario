package ru.kpfu.itis.bagaviev.agario.communication.messages.client;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;

public class RegisterMeMessage extends Message {

    private final String nickname;
    private void fillBuffer() {

        //Putting header
        buffer.put((byte) MessageTypes.REGISTER_ME);

        //Putting length
        buffer.put((byte) nickname.length());

        //Putting player name data
        buffer.put(nickname.getBytes());
    }

    public RegisterMeMessage(String nickname) {
        super();

        if (nickname.length() > MESSAGE_SIZE - 4)
            throw new StringIndexOutOfBoundsException(String.format("Name must contain less than %s chars", (MESSAGE_SIZE - 2) / 2));

        this.nickname = nickname;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.REGISTER_ME;
    }

    public String getNickname() {
        return nickname;
    }

}
