package ru.kpfu.itis.bagaviev.agario.engine.managers;

import ru.kpfu.itis.bagaviev.agario.engine.geometrics.CircleGeometrics;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorldConstants;

public class AgarFeedManager {

    public static void eat(Agar agar, Food food) {
        float newMass = agar.getMass() + (food.getMass() * AgarWorldConstants.AGAR_MASS_INCREASING_RATIO);
        float newMaxVelocity = (float) (newMass / Math.pow(newMass, 1.44) * 10);
        agar.setMass(newMass);
        agar.setMaxVelocity(newMaxVelocity);
    }

    public static boolean canEat(Agar agar, Food food) {
        return CircleGeometrics.isInCircle(food.getX(), food.getY(), agar.getX(), agar.getY(), agar.getMass());
    }

}
