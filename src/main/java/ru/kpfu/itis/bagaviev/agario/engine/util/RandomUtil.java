package ru.kpfu.itis.bagaviev.agario.engine.util;

import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random();

    public static <T> T choose(T... variants) {
        return variants[random.nextInt(variants.length)];
    }

}
