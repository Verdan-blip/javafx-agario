package ru.kpfu.itis.bagaviev.agario.engine.managers;

import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorld;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorldConstants;

public class AgarWorldManager {

    public static float calculateMaxVelocity(float mass) {
        return (float) (mass / Math.pow(mass, 1.44) * 10);
    }

    public static boolean canSplit(AgarItem agarItem) {
        return (agarItem.getAgar().getMass() > AgarWorldConstants.AGAR_MIN_MASS_FOR_SPLITTING);
    }

    public static void cloneInto(Agar agar, Agar agarClone) {
        agarClone.setPosition(agar.getX(), agar.getY());
        agarClone.setDirection(agar.getDirX(), agar.getDirY());
        agarClone.setVelocity(agar.getVelocity());
        agarClone.setMaxVelocity(agar.getMaxVelocity());
        agarClone.setMass(agar.getMass());
    }

    public static void handleAgarSplitting(AgarWorld agarWorld, AgarItem agarItem) {
        if (AgarWorldManager.canSplit(agarItem)) {

            Agar agarToClone = agarItem.getAgar();
            float oldMass = agarToClone.getMass();
            float newMass = oldMass / 2f;
            float newMaxVelocity = AgarWorldManager.calculateMaxVelocity(newMass);
            agarToClone.setMass(oldMass / 2f);
            agarToClone.setMaxVelocity(newMaxVelocity);

            AgarItem agarItemClone = agarWorld.createAgarItem(agarItem.getAgarOwnerId());
            Agar agarClone = agarItemClone.getAgar();

            AgarWorldManager.cloneInto(agarToClone, agarClone);
            agarClone.setPosition(
                    agarToClone.getX() + agarToClone.getMass() * agarClone.getDirX(),
                    agarToClone.getY() + agarToClone.getMass() * agarClone.getDirY());
            agarClone.setVelocity(newMaxVelocity);

            agarWorld.addLater(agarItemClone);
        }
    }

    public static Agar createAgar(float x, float y) {
        return new Agar(
                x, y, AgarWorldConstants.AGAR_INITIAL_DIRECTION_X, AgarWorldConstants.AGAR_INITIAL_DIRECTION_Y,
                AgarWorldConstants.AGAR_INITIAL_VELOCITY, AgarWorldConstants.AGAR_INITIAL_MASS,
                calculateMaxVelocity(AgarWorldConstants.AGAR_INITIAL_MASS)
        );
    }

    public static void handleAgarBeyondWorld(AgarItem agarItem) {

        Agar agar = agarItem.getAgar();

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

    public static void updateAgarPosition(AgarItem agarItem) {
        Agar agar = agarItem.getAgar();
        agar.move();
    }

}
