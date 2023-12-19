package ru.kpfu.itis.bagaviev.agario.communication.messages.server;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;

public class AgarCreatedMessage extends Message {
    private final AgarItem agarItem;

    private void fillBuffer() {
        Integer agarOwnerId = agarItem.getAgarOwnerId();
        Integer agarId = agarItem.getAgarId();
        Agar agar = agarItem.getAgar();

        buffer.put((byte) getMessageType());
        buffer.putInt(agarOwnerId);
        buffer.putInt(agarId);
        buffer.putFloat(agar.getX());
        buffer.putFloat(agar.getY());
        buffer.putFloat(agar.getDirX());
        buffer.putFloat(agar.getDirY());
        buffer.putFloat(agar.getMass());
        buffer.putFloat(agar.getVelocity());
        buffer.putFloat(agar.getMaxVelocity());
    }

    public AgarCreatedMessage(AgarItem agarItem) {
        super();
        this.agarItem = agarItem;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.AGAR_CREATED;
    }

    public AgarItem getAgarItem() {
        return agarItem;
    }
}
