package ru.kpfu.itis.bagaviev.agario.engine.util;

public class AgarItemPair {

    private final AgarItem agarItemA;
    private final AgarItem agarItemB;

    public AgarItemPair(AgarItem agarItemA, AgarItem agarItemB) {
        this.agarItemA = agarItemA;
        this.agarItemB = agarItemB;
    }

    public AgarItem getAgarItemA() {
        return agarItemA;
    }

    public AgarItem getAgarItemB() {
        return agarItemB;
    }

    public boolean contains(AgarItem agarItem) {
        return agarItemA.equals(agarItem) || agarItemB.equals(agarItem);
    }

    public boolean containsId(Integer id) {
        return agarItemA.getId().equals(id) || agarItemB.getId().equals(id);
    }

}
