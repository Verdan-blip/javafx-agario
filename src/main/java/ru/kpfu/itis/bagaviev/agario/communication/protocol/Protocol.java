package ru.kpfu.itis.bagaviev.agario.communication.protocol;

import javafx.scene.paint.Color;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.RegisterMeMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.SplitAgarMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.UpdateDirectionMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.*;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;

import java.nio.ByteBuffer;

public class Protocol {

    public static byte[] encode(Message message) {
        return message.getBytes();
    }

    public static Message decode(byte[] bytes) {

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int messageType = buffer.get();

        switch (messageType) {

            //Client messages
            case MessageTypes.REGISTER_ME -> {
                int length = buffer.get();
                return new RegisterMeMessage(new String(bytes, 2, length));
            }

            case MessageTypes.UPDATE_DIRECTION -> {
                return new UpdateDirectionMessage(buffer.getInt(), buffer.getFloat(), buffer.getFloat());
            }

            case MessageTypes.SPLIT_AGAR -> {
                Integer agarOwnerId = buffer.getInt();
                return new SplitAgarMessage(agarOwnerId);
            }

            //Server messages
            case MessageTypes.OTHER_REGISTERED -> {
                Integer agarOwnerId = buffer.getInt();
                int length = buffer.get();
                return new OtherRegisteredMessage(agarOwnerId, new String(bytes, 4 + 2, length));
            }
            case MessageTypes.YOU_REGISTERED -> {
                Integer agarOwnerId = buffer.getInt();
                int length = buffer.get();
                return new YouRegisteredMessage(agarOwnerId, new String(bytes, 4 + 2, length));
            }
            case MessageTypes.YOU_LOST -> {
                Integer agarOwnerId = buffer.getInt();
                return new YouLostMessage(agarOwnerId);
            }

            case MessageTypes.AGAR_CREATED -> {
                Integer agarOwnerId = buffer.getInt();
                Integer agarId = buffer.getInt();
                return new AgarCreatedMessage(new AgarItem(agarOwnerId, agarId, new Agar(
                        buffer.getFloat(), buffer.getFloat(), buffer.getFloat(),
                        buffer.getFloat(), buffer.getFloat(), buffer.getFloat(), buffer.getFloat()
                )));
            }

            case MessageTypes.UPDATE_AGAR -> {
                Integer agarOwnerId = buffer.getInt();
                Integer agarId = buffer.getInt();
                return new UpdateAgarMessage(new AgarItem(agarOwnerId, agarId, new Agar(
                        buffer.getFloat(), buffer.getFloat(), buffer.getFloat(),
                        buffer.getFloat(), buffer.getFloat(), buffer.getFloat(), buffer.getFloat()
                )));
            }
            case MessageTypes.UPDATE_FOOD -> {
                return new UpdateFoodMessage(
                        buffer.getInt(),
                        new Food(
                                buffer.getFloat(), buffer.getFloat(), buffer.getFloat(),
                                Color.color(buffer.getFloat(), buffer.getFloat(), buffer.getFloat())
                        )
                );
            }
            case MessageTypes.AGAR_WAS_EATEN -> {
                return new AgarWasEatenMessage(
                        buffer.getInt()
                );
            }

        }
        return null;
    }

}
