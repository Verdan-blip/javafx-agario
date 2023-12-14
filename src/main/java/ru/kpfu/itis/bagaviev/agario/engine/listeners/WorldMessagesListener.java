package ru.kpfu.itis.bagaviev.agario.engine.listeners;

import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;

public interface WorldMessagesListener {
    void onAgarUpdate(Integer agarId, Agar agar);
    void onFoodUpdate(Integer foodId, Food food);
    void onAgarLost(Integer agarId);
}
