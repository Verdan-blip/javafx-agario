package ru.kpfu.itis.bagaviev.agario.engine.util;

import ru.kpfu.itis.bagaviev.agario.engine.objects.AgarOwner;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorldConstants;

import java.util.function.BiConsumer;

public class AgarOwnerStorage {

    private final AgarOwner[] agarOwners;

    public AgarOwnerStorage() {
        this.agarOwners = new AgarOwner[AgarWorldConstants.WORLD_MAX_AGAR_COUNT];
    }

    public int findAvailableIndex() {
        for (int i = 0; i < agarOwners.length; i++) {
            if (agarOwners[i] == null) return i;
        }
        return -1;
    }

    public Integer createAgarOwner(String nickname) {
        int index = findAvailableIndex();
        agarOwners[index] = new AgarOwner(nickname);
        return index;
    }

    public AgarOwner get(Integer agarOwnerId) {
        return agarOwners[agarOwnerId];
    }

    public void remove(Integer agarOwnerId) {
        agarOwners[agarOwnerId] = null;
    }

    public void forEachAgarOwner(BiConsumer<Integer, AgarOwner> consumer) {
        for (int i = 0; i < agarOwners.length; i++) {
            if (agarOwners[i] != null) consumer.accept(i, agarOwners[i]);
        }
    }

}
