package ru.kpfu.itis.bagaviev.agario.client.fx.objects;

public class Player {

    private Integer agarId;
    private AgarTexture agarTexture;

    public Player(Integer agarId, AgarTexture agarTexture) {
        this.agarId = agarId;
        this.agarTexture = agarTexture;
    }

    public void setAgarId(Integer agarId) {
        this.agarId = agarId;
    }

    public void setAgarTexture(AgarTexture agarTexture) {
        this.agarTexture = agarTexture;
    }

    public Integer getAgarId() {
        return agarId;
    }

    public AgarTexture getAgarTexture() {
        return agarTexture;
    }

}
