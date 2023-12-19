package ru.kpfu.itis.bagaviev.agario.engine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AgarPairCombinationsList {

    private final List<AgarItemPair> agarItemPairs;
    private final List<AgarItem> agarItems;

    //Utils
    private final List<AgarItemPair> agarItemsToRemove;

    public AgarPairCombinationsList() {
        agarItemPairs = new ArrayList<>();
        agarItems = new ArrayList<>();

        agarItemsToRemove = new ArrayList<>();
    }

    public void add(AgarItem newAgarItem) {
        agarItems.forEach(agarItem -> agarItemPairs.add(new AgarItemPair(agarItem, newAgarItem)));
        agarItems.add(newAgarItem);
    }

    public void remove(Integer agarId) {
        //Save all agar items to remove
        agarItemPairs.forEach(agarItemPair -> {
            if (agarItemPair.containsId(agarId)) {
                agarItemsToRemove.add(agarItemPair);
            }
        });
        //Removing all saved items
        agarItemPairs.removeAll(agarItemsToRemove);
        //Removing specified item
        agarItems.removeIf(agarItem -> agarItem.getAgarId().equals(agarId));
        //Clear buffer
        agarItemsToRemove.clear();
    }

    public void forEach(Consumer<AgarItemPair> consumer) {
        for (AgarItemPair agarItemPair : agarItemPairs) {
            if (agarItemPair != null) {
                consumer.accept(agarItemPair);
            }
        }
    }

    public int size() {
        return agarItemPairs.size();
    }


}
