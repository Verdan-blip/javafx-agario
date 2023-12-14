package ru.kpfu.itis.bagaviev.agario.engine.managers;

import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarFightConclusion;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorldConstants;

public class AgarManager {

    private static float sqr(float number) {
        return number * number;
    }

    private static boolean isInCircle(float pointX, float pointY, float centerX, float centerY, float radius) {
        return sqr(pointX - centerX) + sqr(pointY - centerY) <= sqr(radius);
    }

    private static boolean circlesPenetrate(
            float centerX1, float centerY1, float radius1,
            float centerX2, float centerY2, float radius2
    ) {
        float distance = (float) Math.sqrt(sqr(centerX1 - centerX2) + sqr(centerY1 - centerY2));
        float penetration = radius1 + radius2 - distance;
        float minRadius = Math.min(radius1, radius2);
        return Double.compare(penetration, minRadius * AgarWorldConstants.WINNING_PENETRATION_RATIO) > 0;
    }

    private static boolean hasMassDominance(AgarItem suspectItem,  AgarItem victimItem) {
        float suspectAgarMass = suspectItem.getAgar().getMass();
        float victimAgarMass = victimItem.getAgar().getMass();
        return Double.compare(suspectAgarMass, victimAgarMass * AgarWorldConstants.WINNING_MASS_RATIO) > 0;
    }

    private static AgarItem findMassDominant(AgarItem agarItemA, AgarItem agarItemB) {
        if (hasMassDominance(agarItemA, agarItemB))
            return agarItemA;
        if (hasMassDominance(agarItemA, agarItemB))
            return agarItemB;
        else
            return null;
    }

    public static void eat(Agar agar, Food food) {
        float newMass = agar.getMass() + (food.getMass() * AgarWorldConstants.AGAR_MASS_INCREASING_RATIO);
        float newVelocity = (float) (newMass / Math.pow(newMass, 1.44) * 10);
        agar.setMass(newMass);
        agar.setVelocity(newVelocity);
    }

    public static void eat(Agar dominant, Agar subordinate) {
        float newMass = dominant.getMass() + (subordinate.getMass() * AgarWorldConstants.AGAR_MASS_INCREASING_RATIO);
        float newVelocity = (float) (newMass / Math.pow(newMass, 1.44) * 10);
        dominant.setMass(newMass);
        dominant.setVelocity(newVelocity);
    }

    public static boolean canEat(Agar agar, Food food) {
        return isInCircle(food.getX(), food.getY(), agar.getX(), agar.getY(), agar.getMass());
    }

    public static boolean canFight(AgarItem agarItemA, AgarItem agarItemB) {
        Agar agarA = agarItemA.getAgar();
        Agar agarB = agarItemB.getAgar();
        return circlesPenetrate(
                agarA.getX(), agarA.getY(), agarA.getMass(),
                agarB.getX(), agarB.getY(), agarB.getMass()
        );
    }

    public static void putFightConclusion(AgarFightConclusion conclusion, AgarItem agarItemA, AgarItem agarItemB) {
        AgarItem winner = null, loser = null;
        if (canFight(agarItemA, agarItemB)) {
            winner = findMassDominant(agarItemA, agarItemB);
            loser = agarItemA == winner ? agarItemB : agarItemA;
            if (winner != null) eat(winner.getAgar(), loser.getAgar());
        }
        conclusion.setWinner(winner);
        conclusion.setLoser(loser);
    }

    public static void handleAgarBeyondWorld(Agar agar) {
        float rightWall = AgarWorldConstants.WORLD_WIDTH / 2f;
        float leftWall = -AgarWorldConstants.WORLD_WIDTH / 2f;
        float upWall = AgarWorldConstants.WORLD_HEIGHT / 2f;
        float downWall = -AgarWorldConstants.WORLD_HEIGHT / 2f;

        if (agar.getX() > rightWall) {
            agar.setX(rightWall);
        } else if (agar.getX() < leftWall) {
            agar.setX(leftWall);
        }

        if (agar.getY() > upWall) {
            agar.setY(upWall);
        } else if (agar.getY() < downWall) {
            agar.setY(downWall);
        }

    }

}
