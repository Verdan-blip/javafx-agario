package ru.kpfu.itis.bagaviev.agario.communication.protocol;

import javafx.scene.paint.Color;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.RegisterMeMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.UpdateDirectionMessage;
import ru.kpfu.itis.bagaviev.agario.communication.messages.server.*;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;

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

            case MessageTypes.UPDATE_DIRECTION_MESSAGE -> {
                return new UpdateDirectionMessage(buffer.getInt(), buffer.getFloat(), buffer.getFloat());
            }

            //Server messages
            case MessageTypes.PLAYER_REGISTERED -> {
                Integer id = buffer.getInt();
                //Getting nickname
                byte[] nicknameBytes = new byte[buffer.get()];
                buffer.get(nicknameBytes);
                String nickname = new String(nicknameBytes);

                return new PlayerRegisteredMessage(id, nickname, new Agar(
                        buffer.getFloat(), buffer.getFloat(), buffer.getFloat(),
                        buffer.getFloat(), buffer.getFloat(), buffer.getFloat()
                ));
            }
            case MessageTypes.YOU_REGISTERED -> {
                return new YouRegisteredMessage(buffer.getInt(), new Agar(
                        buffer.getFloat(), buffer.getFloat(), buffer.getFloat(),
                        buffer.getFloat(), buffer.getFloat(), buffer.getFloat()
                ));
            }
            case MessageTypes.UPDATE_AGAR -> {
                return new UpdateAgarMessage(buffer.getInt(), new Agar(
                        buffer.getFloat(), buffer.getFloat(), buffer.getFloat(),
                        buffer.getFloat(), buffer.getFloat(), buffer.getFloat())
                );
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
            case MessageTypes.PLAYER_WAS_EATEN -> {
                return new PlayerWasEatenMessage(
                        buffer.getInt()
                );
            }

        }
        return null;
    }

}
