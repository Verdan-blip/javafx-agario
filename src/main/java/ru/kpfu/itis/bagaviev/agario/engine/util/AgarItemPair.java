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

    public boolean containsId(Integer id) {
        return agarItemA.getAgarId().equals(id) || agarItemB.getAgarId().equals(id);
    }

}
