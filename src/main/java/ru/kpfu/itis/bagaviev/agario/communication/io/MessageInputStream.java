package ru.kpfu.itis.bagaviev.agario.communication.io;

import ru.kpfu.itis.bagaviev.agario.communication.protocol.Protocol;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;

import java.io.IOException;
import java.io.InputStream;

public class MessageInputStream extends InputStream {

    private final InputStream in;
    private final byte[] buffer;

    public MessageInputStream(InputStream in) {
        this.in = in;
        this.buffer = new byte[Message.MESSAGE_SIZE];
    }

    public Message readMessage() throws IOException {
        in.read(buffer);
        return Protocol.decode(buffer);
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

}
