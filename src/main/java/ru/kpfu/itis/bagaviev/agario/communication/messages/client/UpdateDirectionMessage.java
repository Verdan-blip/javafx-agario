package ru.kpfu.itis.bagaviev.agario.communication.messages.client;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;

public class UpdateDirectionMessage extends Message {

    private final Integer agarId;
    private final float newDirX;
    private final float newDirY;

    private void fillBuffer() {
        buffer.put((byte) getMessageType());
        buffer.putInt(agarId);
        buffer.putFloat(newDirX);
        buffer.putFloat(newDirY);
    }

    public UpdateDirectionMessage(Integer agarId, float newDirX, float newDirY) {
        super();
        this.agarId = agarId;
        this.newDirX = newDirX;
        this.newDirY = newDirY;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.UPDATE_DIRECTION;
    }

    public Integer getAgarId() {
        return agarId;
    }

    public float getNewDirX() {
        return newDirX;
    }

    public float getNewDirY() {
        return newDirY;
    }

}
