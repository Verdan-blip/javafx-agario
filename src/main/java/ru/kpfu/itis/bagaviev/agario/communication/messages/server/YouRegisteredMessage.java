package ru.kpfu.itis.bagaviev.agario.communication.messages.server;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;

public class YouRegisteredMessage extends Message {

    private final Integer id;
    private final Agar agar;

    private void fillBuffer() {
        buffer.put((byte) getMessageType());
        buffer.putInt(id);
        buffer.putFloat(agar.getX());
        buffer.putFloat(agar.getY());
        buffer.putFloat(agar.getDirX());
        buffer.putFloat(agar.getDirY());
        buffer.putFloat(agar.getMass());
        buffer.putFloat(agar.getVelocity());
    }

    public YouRegisteredMessage(Integer id, Agar agar) {
        this.id = id;
        this.agar = agar;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.YOU_REGISTERED;
    }

    public Integer getId() {
        return id;
    }

    public Agar getAgar() {
        return agar;
    }

}
