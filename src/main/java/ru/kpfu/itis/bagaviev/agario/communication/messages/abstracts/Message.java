package ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts;

import java.nio.ByteBuffer;

public abstract class Message {
    public static final int MESSAGE_SIZE = 36;
    protected final ByteBuffer buffer;

    public Message() {
        buffer = ByteBuffer.allocate(MESSAGE_SIZE);
    }

    public abstract int getMessageType();

    public byte[] getBytes() {
        return buffer.array();
    }

}
