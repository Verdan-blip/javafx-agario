package ru.kpfu.itis.bagaviev.agario.engine.managers;

import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;

public class AgarRelationsManager {

    public static boolean areEnemies(AgarItem agarItemA, AgarItem agarItemB) {
        return !agarItemA.getAgarOwnerId().equals(agarItemB.getAgarOwnerId());
    }

}
