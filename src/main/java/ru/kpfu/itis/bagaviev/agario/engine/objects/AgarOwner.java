package ru.kpfu.itis.bagaviev.agario.engine.objects;

import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AgarOwner {

    private final String nickname;
    private final List<AgarItem> agarItems;

    public AgarOwner(String nickname) {
        this.nickname = nickname;
        this.agarItems = new ArrayList<>();
    }

    public void addAgarItem(AgarItem agarItem) {
        agarItems.add(agarItem);
    }

    public void remove(Integer agarId) {
        agarItems.removeIf(agarItem -> agarItem.getAgarId().equals(agarId));
    }

    public void forEachAgarItem(Consumer<AgarItem> consumer) {
        for (int i = 0; i < agarItems.size(); i++) {
            consumer.accept(agarItems.get(i));
        }
    }

    public String getNickname() {
        return nickname;
    }

    public boolean hasNoAgars() {
        return agarItems.isEmpty();
    }

    public int agarsCount() {
        return agarItems.size();
    }

}
