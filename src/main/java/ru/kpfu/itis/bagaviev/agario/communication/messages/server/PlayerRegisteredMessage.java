package ru.kpfu.itis.bagaviev.agario.communication.messages.server;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;

public class PlayerRegisteredMessage extends Message {

    private final Integer id;
    private final String nickname;
    private final Agar agar;

    private void fillBuffer() {
        buffer.put((byte) MessageTypes.PLAYER_REGISTERED);
        buffer.putInt(id);

        buffer.put((byte) nickname.length());
        buffer.put(nickname.getBytes());

        buffer.putFloat(agar.getX());
        buffer.putFloat(agar.getY());
        buffer.putFloat(agar.getDirX());
        buffer.putFloat(agar.getDirY());
        buffer.putFloat(agar.getVelocity());
        buffer.putFloat(agar.getMass());
    }

    public PlayerRegisteredMessage(Integer id, String nickname, Agar agar) {
        super();
        this.id = id;
        this.agar = agar;
        this.nickname = nickname;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.PLAYER_REGISTERED;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getId() {
        return id;
    }

    public Agar getAgar() {
        return agar;
    }

}
