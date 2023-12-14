package ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts;

public class MessageTypes {

    //Messages from Client
    public static final int REGISTER_ME = 0;
    public static final int UPDATE_DIRECTION_MESSAGE = 3;

    //Messages from server
    public static final int PLAYER_REGISTERED = 1;
    public static final int YOU_REGISTERED = 4;
    public static final int PLAYER_WAS_EATEN = 6;
    public static final int UPDATE_FOOD = 5;
    public static final int UPDATE_AGAR = 2;
}
