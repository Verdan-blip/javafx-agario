package ru.kpfu.itis.bagaviev.agario.engine.listeners;

import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;

public interface WorldMessagesListener {
    void onAgarCreate(AgarItem agarItem);
    void onAgarUpdate(AgarItem agarItem);
    void onAgarRemove(Integer agarId);
    void onAllAgarsRemoved(Integer agarOwnerId);
    void onFoodUpdate(Integer foodId, Food food);
}
