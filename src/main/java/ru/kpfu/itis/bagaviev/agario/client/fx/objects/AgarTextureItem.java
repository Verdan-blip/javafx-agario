package ru.kpfu.itis.bagaviev.agario.client.fx.objects;

import ru.kpfu.itis.bagaviev.agario.client.fx.textures.AgarTexture;

public class AgarTextureItem {

    private Integer agarOwnerId;
    private AgarTexture agarTexture;

    public AgarTextureItem(Integer agarOwnerId, AgarTexture agarTexture) {
        this.agarOwnerId = agarOwnerId;
        this.agarTexture = agarTexture;
    }

    public Integer getAgarOwnerId() {
        return agarOwnerId;
    }

    public void setAgarOwnerId(Integer agarOwnerId) {
        this.agarOwnerId = agarOwnerId;
    }

    public AgarTexture getAgarTexture() {
        return agarTexture;
    }

    public void setAgarTexture(AgarTexture agarTexture) {
        this.agarTexture = agarTexture;
    }
}
