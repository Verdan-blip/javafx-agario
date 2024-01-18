package ru.kpfu.itis.bagaviev.agario.server.net;

import ru.kpfu.itis.bagaviev.agario.communication.io.MessageInputStream;
import ru.kpfu.itis.bagaviev.agario.communication.io.MessageOutputStream;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.server.net.listeners.ClientMessageListener;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class ClientHandlingThread extends Thread {

    private final Integer sessionId;
    private final Socket clientSocket;
    private final ClientMessageListener clientMessageListener;

    private final MessageInputStream in;
    private final MessageOutputStream out;

    //Listeners
    private Consumer<Integer> onDisconnect;

    public ClientHandlingThread(Integer sessionId, Socket clientSocket, ClientMessageListener clientMessageListener) {
        this.sessionId = sessionId;
        this.clientSocket = clientSocket;
        this.clientMessageListener = clientMessageListener;
        try {
            in = new MessageInputStream(clientSocket.getInputStream());
            out = new MessageOutputStream(clientSocket.getOutputStream());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void sendMessage(Message message) {
        try {
            out.writeMessage(message);
            out.flush();
        } catch (IOException exception) {
            onDisconnect.accept(sessionId);
            try {
                clientSocket.close();
            } catch (IOException exception2) {
                throw new RuntimeException(exception2);
            }
        }
    }

    @Override
    public void run() {
        try {
            while (!clientSocket.isClosed()) {
                Message message = in.readMessage();
                if (message != null) {
                    clientMessageListener.onMessagePerformed(sessionId, message);
                }
            }
            onDisconnect.accept(sessionId);
            clientSocket.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setOnDisconnect(Consumer<Integer> onDisconnect) {
        this.onDisconnect = onDisconnect;
    }

}
