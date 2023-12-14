package ru.kpfu.itis.bagaviev.agario.communication.io;

import ru.kpfu.itis.bagaviev.agario.communication.protocol.Protocol;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;

import java.io.IOException;
import java.io.OutputStream;

public class MessageOutputStream extends OutputStream {

    private final OutputStream out;

    public MessageOutputStream(OutputStream out) {
        this.out = out;
    }

    public void writeMessage(Message message) throws IOException {
        out.write(Protocol.encode(message));
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

}
