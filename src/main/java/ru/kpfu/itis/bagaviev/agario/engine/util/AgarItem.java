package ru.kpfu.itis.bagaviev.agario.engine.util;

import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;

public class AgarItem {
    private final Integer agarOwnerId;
    private final Integer agarId;
    private final Agar agar;

    public AgarItem(Integer agarOwnerId, Integer agarId, Agar agar) {
        this.agarOwnerId = agarOwnerId;
        this.agarId = agarId;
        this.agar = agar;
    }

    public Integer getAgarOwnerId() {
        return agarOwnerId;
    }

    public Integer getAgarId() {
        return agarId;
    }

    public Agar getAgar() {
        return agar;
    }
}
