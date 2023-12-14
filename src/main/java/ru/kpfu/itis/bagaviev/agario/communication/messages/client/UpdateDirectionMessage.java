package ru.kpfu.itis.bagaviev.agario.communication.messages.client;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;

public class UpdateDirectionMessage extends Message {

    private final Integer id;
    private final float newDirX;
    private final float newDirY;

    private void fillBuffer() {
        buffer.put((byte) MessageTypes.UPDATE_DIRECTION_MESSAGE);
        buffer.putInt(id);
        buffer.putFloat(newDirX);
        buffer.putFloat(newDirY);
    }

    public UpdateDirectionMessage(Integer id, float newDirX, float newDirY) {
        this.id = id;
        this.newDirX = newDirX;
        this.newDirY = newDirY;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.UPDATE_DIRECTION_MESSAGE;
    }

    public Integer getId() {
        return id;
    }

    public float getNewDirX() {
        return newDirX;
    }

    public float getNewDirY() {
        return newDirY;
    }

}
