package ru.kpfu.itis.bagaviev.agario.server.net.listeners;

import ru.kpfu.itis.bagaviev.agario.communication.messages.server.*;
import ru.kpfu.itis.bagaviev.agario.engine.listeners.WorldMessagesListener;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;
import ru.kpfu.itis.bagaviev.agario.server.net.Server;

public class WorldMessagesListenerImpl implements WorldMessagesListener {

    private final Server server;

    public WorldMessagesListenerImpl(Server server) {
        this.server = server;
    }

    @Override
    public void onAgarCreate(AgarItem agarItem) {
        server.sendBroadcastMessage(new AgarCreatedMessage(agarItem));
    }

    @Override
    public void onAgarUpdate(AgarItem agarItem) {
        server.sendBroadcastMessage(new UpdateAgarMessage(agarItem));
    }

    @Override
    public void onAgarRemove(Integer agarId) {
        server.sendBroadcastMessage(new AgarWasEatenMessage(agarId));
    }

    @Override
    public void onAllAgarsRemoved(Integer agarOwnerId) {
        server.sendBroadcastMessage(new YouLostMessage(agarOwnerId));
    }

    @Override
    public void onFoodUpdate(Integer foodId, Food food) {
        server.sendBroadcastMessage(new UpdateFoodMessage(foodId, food));
    }

}
