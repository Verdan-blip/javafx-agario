package ru.kpfu.itis.bagaviev.agario.engine.world;

import ru.kpfu.itis.bagaviev.agario.engine.listeners.WorldMessagesListener;
import ru.kpfu.itis.bagaviev.agario.engine.managers.AgarManager;
import ru.kpfu.itis.bagaviev.agario.engine.managers.FoodManager;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarFightConclusion;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarPairCombinationsList;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarStorage;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AgarWorld {

    private final AgarStorage agarStorage;
    private final Map<Integer, Food> feedMap;

    //Listeners
    private WorldMessagesListener worldMessagesListener;

    //Utils
    private final FoodManager foodManager;
    private final AgarPairCombinationsList agarPairCombinationsList;
    private final List<AgarItem> agarItemsToRemove;
    private final AgarFightConclusion agarFightConclusion;

    private void stepFeed(Agar agar) {
        for (var foodPair : feedMap.entrySet()) {
            Integer foodId = foodPair.getKey();
            Food food = foodPair.getValue();
            if (AgarManager.canEat(agar, food)) {
                AgarManager.eat(agar, food);
                foodManager.respawnFood(food);
                worldMessagesListener.onFoodUpdate(foodId, food);
            }
        }
    }

    private void stepAgars() {
        //Updating agars positions and checking all feed for eating by agars
        agarStorage.forEachItems(agarItem -> {
            agarItem.getAgar().move();
            AgarManager.handleAgarBeyondWorld(agarItem.getAgar());
            worldMessagesListener.onAgarUpdate(agarItem.getId(), agarItem.getAgar());
            stepFeed(agarItem.getAgar());
        });
        handleAgarsFights();
    }

    private void handleAgarsFights() {
        agarPairCombinationsList.forEach(agarItemPair -> {
            AgarManager.putFightConclusion(
                    agarFightConclusion,
                    agarItemPair.getAgarItemA(),
                    agarItemPair.getAgarItemB()
            );
            if (!agarFightConclusion.isDraw()) {
                agarItemsToRemove.add(agarFightConclusion.getLoser());
                worldMessagesListener.onAgarLost(agarFightConclusion.getLoser().getId());
            }
        });
        //Removing all lost agars
        agarItemsToRemove.forEach((agarItem -> removeAgar(agarItem.getId())));
        agarItemsToRemove.clear();
    }

    public AgarWorld() {

        //Game maps
        agarStorage = new AgarStorage();
        feedMap = new HashMap<>();

        //Util
        foodManager = new FoodManager(AgarWorldConstants.WORLD_WIDTH, AgarWorldConstants.WORLD_HEIGHT);
        agarPairCombinationsList = new AgarPairCombinationsList();
        agarItemsToRemove = new LinkedList<>();
        agarFightConclusion = new AgarFightConclusion();

        //Initializing feed
        for (int i = 0; i < AgarWorldConstants.FEED_SPAWN_COUNT; i++) {
            feedMap.put(i, foodManager.generateFood());
        }
    }

    public Integer addAgar(String nickname, Agar agar) {
        Integer agarId = agarStorage.add(nickname, agar);
        agarPairCombinationsList.add(new AgarItem(agarId, nickname, agar));
        return agarId;
    }

    public AgarItem createAgar(String nickname) {
        float mass = AgarWorldConstants.AGAR_INITIAL_MASS;
        Agar agar = new Agar(
                0, 0, 1, 0,
                (float) (mass / Math.pow(mass, 1.44) * 10),
                AgarWorldConstants.AGAR_INITIAL_MASS
        );
        Integer agarId = addAgar(nickname, agar);
        return new AgarItem(agarId, nickname, agar);
    }

    public void removeAgar(Integer agarId) {
        agarStorage.remove(agarId);
        agarPairCombinationsList.remove(agarId);
    }

    public void updateAgarDirection(Integer agarId, float newDirX, float newDirY) {
        AgarItem agar = agarStorage.get(agarId);
        if (agar != null) agarStorage.get(agarId).getAgar().setDirection(newDirX, newDirY);
    }

    public void step() {
        stepAgars();
    }

    //Listeners
    public void setWorldMessagesListener(WorldMessagesListener worldMessagesListener) {
        this.worldMessagesListener = worldMessagesListener;
    }

    public void forAllAgarItems(Consumer<AgarItem> consumer) {
        agarStorage.forEachItems(consumer);
    }

    public void forAllFeed(BiConsumer<Integer, Food> biConsumer) {
        feedMap.forEach(biConsumer);
    }

}
