package ru.kpfu.itis.bagaviev.agario.server.net;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.server.net.listeners.ClientMessageListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.function.Consumer;

public class Server {

    public static final int PORT = 5454;
    public static final InetAddress ADDRESS;

    private final ServerSocket serverSocket;
    private final Map<Integer, ClientHandlingThread> session = new HashMap<>();

    //Listeners
    private ClientMessageListener clientMessageListener;
    private Consumer<Integer> onClientConnect;

    static {
        try {
            ADDRESS = InetAddress.getLocalHost();
        } catch (UnknownHostException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void start() {
        int counter = 0;
        try {
            while (!serverSocket.isClosed()) {

                //Awaiting new client
                Socket clientSocket = serverSocket.accept();

                //Generating id for new actor
                Integer sessionId = counter;
                ClientHandlingThread clientHandlingThread = new ClientHandlingThread(sessionId, clientSocket, clientMessageListener);

                //Saving actor
                session.put(sessionId, clientHandlingThread);

                //Starting actor work
                clientHandlingThread.start();

                onClientConnect.accept(sessionId);

                counter++;
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void sendBroadcastMessage(Integer excludingSessionId, Message message) {
        for (var pair : session.entrySet()) {
            if (!pair.getKey().equals(excludingSessionId)) {
                pair.getValue().sendMessage(message);
            }
        }
    }

    public void sendBroadcastMessage(Message message) {
        for (var pair : session.entrySet()) {
            pair.getValue().sendMessage(message);
        }
    }

    public void sendMessage(Integer sessionId, Message message) {
        session.get(sessionId).sendMessage(message);
    }

    //Listeners
    public void setOnClientMessageListener(ClientMessageListener clientMessageListener) {
        this.clientMessageListener = clientMessageListener;
    }

    public void setOnConnectListener(Consumer<Integer> onConnect) {
        this.onClientConnect = onConnect;
    }

}
