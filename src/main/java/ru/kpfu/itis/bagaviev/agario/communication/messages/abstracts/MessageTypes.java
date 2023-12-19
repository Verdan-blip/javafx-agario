package ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts;

public class MessageTypes {

    //Messages from Client
    public static final int REGISTER_ME = 0;
    public static final int UPDATE_DIRECTION = 3;

    //Messages from server
    public static final int OTHER_REGISTERED = 1;
    public static final int YOU_REGISTERED = 4;
    public static final int YOU_LOST = 9;
    public static final int AGAR_CREATED = 8;
    public static final int AGAR_WAS_EATEN = 6;
    public static final int SPLIT_AGAR = 7;
    public static final int UPDATE_FOOD = 5;
    public static final int UPDATE_AGAR = 2;
}
