package ru.kpfu.itis.bagaviev.agario.client.net;

import ru.kpfu.itis.bagaviev.agario.communication.listeners.MessageListener;
import ru.kpfu.itis.bagaviev.agario.communication.io.MessageInputStream;
import ru.kpfu.itis.bagaviev.agario.communication.io.MessageOutputStream;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private MessageInputStream in;
    private MessageOutputStream out;

    //Message listeners
    private MessageListener serverMessageListener;

    private void startListeningMessagesFromServer() {
        new Thread(() -> {
            try {
                while (!clientSocket.isClosed()) {
                    serverMessageListener.onMessagePerformed(in.readMessage());
                }
            } catch (IOException ignored) { }
        }).start();
    }

    public void connect(InetAddress inetAddress, int port) {
        try {
            clientSocket = new Socket(inetAddress, port);
            in = new MessageInputStream(clientSocket.getInputStream());
            out = new MessageOutputStream(clientSocket.getOutputStream());
            startListeningMessagesFromServer();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void sendMessage(Message message) {
        try {
            out.writeMessage(message);
            out.flush();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void disconnect() {
        try {
            clientSocket.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setServerMessageListener(MessageListener serverMessageListener) {
        this.serverMessageListener = serverMessageListener;
    }

}
