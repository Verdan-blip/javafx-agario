package ru.kpfu.itis.bagaviev.agario.communication.messages.server;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;

public class UpdateAgarMessage extends Message {
    private final Integer agarId;
    private final Agar agar;

    private void fillBuffer() {
        buffer.put((byte) MessageTypes.UPDATE_AGAR);
        buffer.putInt(agarId);
        buffer.putFloat(agar.getX());
        buffer.putFloat(agar.getY());
        buffer.putFloat(agar.getDirX());
        buffer.putFloat(agar.getDirY());
        buffer.putFloat(agar.getVelocity());
        buffer.putFloat(agar.getMass());
    }

    public UpdateAgarMessage(Integer agarId, Agar agar) {
        this.agarId = agarId;
        this.agar = agar;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.UPDATE_AGAR;
    }

    public Agar getAgar() {
        return agar;
    }

    public Integer getAgarId() {
        return agarId;
    }
}
