package ru.kpfu.itis.bagaviev.agario.server.app;

import ru.kpfu.itis.bagaviev.agario.communication.messages.server.AgarCreatedMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.OtherRegisteredMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.UpdateFoodMessage;
import ru.kpfu.itis.bagaviev.agario.engine.world.AgarWorld;
import ru.kpfu.itis.bagaviev.agario.server.net.Server;
import ru.kpfu.itis.bagaviev.agario.server.net.listeners.ClientMessageListener;
import ru.kpfu.itis.bagaviev.agario.server.net.listeners.WorldMessagesListenerImpl;

import java.util.Timer;
import java.util.TimerTask;

public class ServerApplication {

    private static final int FPS = 60;

    public static void main(String[] args) {

        Server server = new Server();

        AgarWorld agarWorld = new AgarWorld();
        agarWorld.setWorldMessagesListener(new WorldMessagesListenerImpl(server));

        server.setOnConnectListener(sessionId -> {
            agarWorld.forEachAgarOwner((agarOwnerId, agarOwner) -> {
                server.sendMessage(sessionId, new OtherRegisteredMessage(agarOwnerId, agarOwner.getNickname()));
                agarOwner.forEachAgarItem(agarItem -> {
                    server.sendMessage(sessionId, new AgarCreatedMessage(agarItem));
                });
            });
            agarWorld.forEachFood((id, food) ->
                    server.sendMessage(sessionId, new UpdateFoodMessage(id, food))
            );
        });
        server.setOnClientMessageListener(new ClientMessageListener(server, agarWorld));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                agarWorld.step();
            }
        }, 0L, 1000L / FPS);

        server.start();
    }

}
