package ru.kpfu.itis.bagaviev.agario.server.net.listeners;

import ru.kpfu.itis.bagaviev.agario.communication.messages.server.PlayerWasEatenMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.UpdateAgarMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.UpdateFoodMessage;
import ru.kpfu.itis.bagaviev.agario.engine.listeners.WorldMessagesListener;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;
import ru.kpfu.itis.bagaviev.agario.server.net.Server;

public class WorldMessagesListenerImpl implements WorldMessagesListener {

    private final Server server;

    public WorldMessagesListenerImpl(Server server) {
        this.server = server;
    }

    @Override
    public void onAgarUpdate(Integer agarId, Agar agar) {
        server.sendBroadcastMessage(new UpdateAgarMessage(agarId, agar));
    }

    @Override
    public void onFoodUpdate(Integer foodId, Food food) {
        server.sendBroadcastMessage(new UpdateFoodMessage(foodId, food));
    }

    @Override
    public void onAgarLost(Integer agarId) {
        server.sendBroadcastMessage(new PlayerWasEatenMessage(agarId));
    }

}
