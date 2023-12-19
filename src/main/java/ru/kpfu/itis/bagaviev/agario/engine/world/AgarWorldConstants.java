package ru.kpfu.itis.bagaviev.agario.engine.world;

public class AgarWorldConstants {

    //World settings
    public static final int FEED_SPAWN_COUNT = 500;
    public static final int WORLD_HEIGHT = 2048;
    public static final int WORLD_WIDTH = 2048;
    public static final int WORLD_MAX_AGAR_COUNT = 16;

    //Agar settings
    public static final float AGAR_INITIAL_MASS = 10f;
    public static final float AGAR_INITIAL_DIRECTION_X = 0f;
    public static final float AGAR_INITIAL_DIRECTION_Y = 1f;
    public static final float AGAR_INITIAL_VELOCITY = 0f;
    public static final float AGAR_MASS_INCREASING_RATIO = 1f;
    public static final float AGAR_MIN_MASS_FOR_SPLITTING = 25f;
    public static final float AGAR_VELOCITY_COEFFICIENT = 5f;

    public static final float WINNING_MASS_RATIO = 1.2f;
    public static final float WINNING_PENETRATION_RATIO = 0.75f;
}
