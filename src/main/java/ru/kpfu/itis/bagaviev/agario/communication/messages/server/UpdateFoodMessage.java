package ru.kpfu.itis.bagaviev.agario.communication.messages.server;

import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.Message;
import ru.kpfu.itis.bagaviev.agario.communication.messages.abstracts.MessageTypes;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;

public class UpdateFoodMessage extends Message {

    private final Integer foodId;
    private final Food food;

    private void fillBuffer() {
        buffer.put((byte) getMessageType());
        buffer.putInt(foodId);
        buffer.putFloat(food.getX());
        buffer.putFloat(food.getY());
        buffer.putFloat(food.getMass());

        buffer.putFloat((float) food.getColor().getRed());
        buffer.putFloat((float) food.getColor().getGreen());
        buffer.putFloat((float) food.getColor().getBlue());
    }

    public UpdateFoodMessage(Integer foodId, Food food) {
        this.food = food;
        this.foodId = foodId;
        fillBuffer();
    }

    @Override
    public int getMessageType() {
        return MessageTypes.UPDATE_FOOD;
    }

    public Food getFood() {
        return food;
    }

    public Integer getFoodId() {
        return foodId;
    }

}
