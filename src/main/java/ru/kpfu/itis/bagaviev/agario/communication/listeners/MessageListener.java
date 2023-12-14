package ru.kpfu.itis.bagaviev.agario.communication.listeners;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;

public interface MessageListener {
    void onMessagePerformed(Message message);
}
