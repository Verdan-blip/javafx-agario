package ru.kpfu.itis.bagaviev.agario.engine.managers;

import javafx.scene.paint.Color;
import ru.kpfu.itis.bagaviev.agario.engine.util.RandomUtil;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;

import java.util.Random;

public class FoodManager {

    private static final float FOOD_MIN_MASS = 0.5f;
    private static final float FOOD_MAX_MASS = 1f;

    private final int worldWidth;
    private final int worldHeight;

    private final Random random;

    public FoodManager(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        this.random = new Random();
    }

    public Food generateFood() {
        float x = random.nextFloat(-worldWidth / 2f, worldWidth / 2f);
        float y = random.nextFloat(-worldHeight / 2f, worldHeight / 2f);
        float mass = random.nextFloat(FOOD_MIN_MASS, FOOD_MAX_MASS);
        Color color = RandomUtil.choose(Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE);
        return new Food(x, y, mass, color);
    }

    public void respawnFood(Food food) {
        float x = random.nextFloat(-worldWidth / 2f, worldWidth / 2f);
        float y = random.nextFloat(-worldHeight / 2f, worldHeight / 2f);
        float mass = random.nextFloat(FOOD_MIN_MASS, FOOD_MAX_MASS);
        food.setX(x);
        food.setY(y);
        food.setMass(mass);
    }

}
