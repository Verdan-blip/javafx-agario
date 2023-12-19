package ru.kpfu.itis.bagaviev.agario.engine.managers;

import ru.kpfu.itis.bagaviev.agario.engine.geometrics.CircleGeometrics;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorld;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorldConstants;

public class AgarFightsManager {

    private static final float ELASTIC_COEFFICIENT = 1 / 16f;

    private static boolean hasMassDominance(AgarItem suspectItem,  AgarItem victimItem) {
        float suspectAgarMass = suspectItem.getAgar().getMass();
        float victimAgarMass = victimItem.getAgar().getMass();
        return Double.compare(suspectAgarMass, victimAgarMass * AgarWorldConstants.WINNING_MASS_RATIO) > 0;
    }

    private static void eat(AgarItem dominantAgarItem, AgarItem subordinateAgarItem) {
        Agar dominateAgar = dominantAgarItem.getAgar();
        Agar subordinateAgar = subordinateAgarItem.getAgar();
        float newMass = dominateAgar.getMass() + (subordinateAgar.getMass() * AgarWorldConstants.AGAR_MASS_INCREASING_RATIO);
        float newMaxVelocity = (float) (newMass / Math.pow(newMass, 1.44) * 10);
        dominateAgar.setMass(newMass);
        dominateAgar.setMaxVelocity(newMaxVelocity);
    }

    private static void handleEnemiesFight(AgarWorld agarWorld, AgarItem agarItemA, AgarItem agarItemB) {
        Agar agarA = agarItemA.getAgar();
        Agar agarB = agarItemB.getAgar();
        if (CircleGeometrics.circlesPenetrate(
                agarA.getX(), agarA.getY(), agarA.getMass(),
                agarB.getX(), agarB.getY(), agarB.getMass(),
                AgarWorldConstants.WINNING_PENETRATION_RATIO)
        ) {
            if (hasMassDominance(agarItemA, agarItemB)) {
                eat(agarItemA, agarItemB);
                agarWorld.removeLater(agarItemB.getAgarId());
            } else if (hasMassDominance(agarItemB, agarItemA)) {
                eat(agarItemB, agarItemA);
                agarWorld.removeLater(agarItemA.getAgarId());
            }
        }
    }

    private static void handleTeammatesFight(AgarItem agarItemA, AgarItem agarItemB) {
        Agar agarA = agarItemA.getAgar();
        Agar agarB = agarItemB.getAgar();
        if (CircleGeometrics.circlesIntersect(
                agarA.getX(), agarA.getY(), agarA.getMass(),
                agarB.getX(), agarB.getY(), agarB.getMass())) {
            float penetrationProjX = CircleGeometrics.getCircleIntersectionProjectionX(
                    agarA.getX(), agarA.getY(), agarB.getX(), agarB.getY(), agarB.getMass()
            );
            float penetrationProjY = CircleGeometrics.getCirclesIntersectionProjectionY(
                    agarA.getX(), agarA.getY(), agarB.getX(), agarB.getY(), agarB.getMass()
            );
            agarB.setX(agarB.getX() + penetrationProjX * ELASTIC_COEFFICIENT);
            agarB.setY(agarB.getY() - penetrationProjY * ELASTIC_COEFFICIENT);
        }
    }

    public static void handleAgarsFight(AgarWorld agarWorld, AgarItem agarItemA, AgarItem agarItemB) {
        if (AgarRelationsManager.areEnemies(agarItemA, agarItemB)) {
            handleEnemiesFight(agarWorld, agarItemA, agarItemB);
        } else {
            handleTeammatesFight(agarItemA, agarItemB);
        }
    }

}
