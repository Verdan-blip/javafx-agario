package ru.kpfu.itis.bagaviev.agario.engine.util;

import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorldConstants;

import java.util.function.Consumer;

public class AgarStorage {

    private final AgarItem[] agarItems;

    public AgarStorage() {
        this.agarItems = new AgarItem[AgarWorldConstants.WORLD_MAX_AGAR_COUNT];
    }

    public int findAvailableIndex() {
        for (int i = 0; i < agarItems.length; i++) {
            if (agarItems[i] == null) return i;
        }
        return -1;
    }

    public Integer add(String nickname, Agar agar) {
        Integer agarItemId = findAvailableIndex();
        agarItems[agarItemId] = new AgarItem(agarItemId, nickname, agar);
        return agarItemId;
    }

    public AgarItem get(Integer agarId) {
        return agarItems[agarId];
    }

    public void remove(Integer agarId) {
        agarItems[agarId] = null;
    }

    public void forEachItems(Consumer<AgarItem> consumer) {
        for (AgarItem agarItem : agarItems) {
            if (agarItem != null) consumer.accept(agarItem);
        }
    }

}
