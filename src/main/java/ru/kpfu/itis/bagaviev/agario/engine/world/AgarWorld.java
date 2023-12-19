package ru.kpfu.itis.bagaviev.agario.engine.world;

import ru.kpfu.itis.bagaviev.agario.engine.listeners.WorldMessagesListener;
import ru.kpfu.itis.bagaviev.agario.engine.managers.AgarFeedManager;
import ru.kpfu.itis.bagaviev.agario.engine.managers.AgarFightsManager;
import ru.kpfu.itis.bagaviev.agario.engine.managers.AgarWorldManager;
import ru.kpfu.itis.bagaviev.agario.engine.managers.FoodManager;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.objects.AgarOwner;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;
import ru.kpfu.itis.bagaviev.agario.engine.util.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AgarWorld {

    //For indexing agars id
    private final AgarStorage agarStorage;

    //For indexing agar owners
    private final AgarOwnerStorage agarOwnerStorage;
    private final Map<Integer, Food> feedMap;

    //Listeners
    private WorldMessagesListener worldMessagesListener;

    //Utils
    private final FoodManager foodManager;
    private final AgarPairCombinationsList agarPairCombinationsList;
    private final List<AgarItem> agarItemsToRemove;
    private final List<AgarItem> agarItemsToAdd;

    //Split

    private void stepFeed(AgarItem agarItem) {
        Agar agar = agarItem.getAgar();
        for (var foodPair : feedMap.entrySet()) {
            Integer foodId = foodPair.getKey();
            Food food = foodPair.getValue();
            if (AgarFeedManager.canEat(agar, food)) {
                AgarFeedManager.eat(agar, food);
                foodManager.respawnFood(food);
                worldMessagesListener.onFoodUpdate(foodId, food);
            }
        }
    }

    private void stepAgars() {
        //Updating agars positions and checking all feed for eating by agars
        agarStorage.forEachItems(agarItem -> {
            AgarWorldManager.updateAgarPosition(agarItem);
            AgarWorldManager.handleAgarBeyondWorld(agarItem);
            stepFeed(agarItem);
        });
        handleAgarsFights();
        //Notify that agars were updated
        agarStorage.forEachItems(worldMessagesListener::onAgarUpdate);
    }

    private void handleAgarsFights() {
        agarPairCombinationsList.forEach(agarItemPair ->
            AgarFightsManager.handleAgarsFight(
                    this, agarItemPair.getAgarItemA(), agarItemPair.getAgarItemB()
            )
        );
        //Removing all lost agars
        agarItemsToRemove.forEach((agarItem -> removeAgar(agarItem.getAgarOwnerId(), agarItem.getAgarId())));
        agarItemsToRemove.clear();
    }

    public AgarWorld() {

        //Game maps
        agarStorage = new AgarStorage();
        agarOwnerStorage = new AgarOwnerStorage();
        feedMap = new HashMap<>();

        //Util
        foodManager = new FoodManager(AgarWorldConstants.WORLD_WIDTH, AgarWorldConstants.WORLD_HEIGHT);
        agarPairCombinationsList = new AgarPairCombinationsList();
        agarItemsToRemove = new LinkedList<>();
        agarItemsToAdd = new LinkedList<>();

        //Initializing feed
        for (int i = 0; i < AgarWorldConstants.FEED_SPAWN_COUNT; i++) {
            feedMap.put(i, foodManager.generateFood());
        }
    }

    public Integer createAgarOwner(String nickname) {
        return agarOwnerStorage.createAgarOwner(nickname);
    }

    public AgarItem createAgarItem(Integer agarOwnerId) {

        Agar agar = AgarWorldManager.createAgar(0f, 0f);
        Integer agarId = agarStorage.add(agarOwnerId, agar);
        AgarItem agarItem = new AgarItem(agarOwnerId, agarId, agar);

        AgarOwner agarOwner = agarOwnerStorage.get(agarOwnerId);
        agarOwner.addAgarItem(agarItem);

        agarPairCombinationsList.add(agarItem);

        //Notify listener
        worldMessagesListener.onAgarCreate(agarItem);

        return agarItem;
    }

    public void removeAgar(Integer agarOwnerId, Integer agarId) {

        AgarOwner agarOwner = agarOwnerStorage.get(agarOwnerId);

        agarOwner.remove(agarId);
        agarStorage.remove(agarId);
        agarPairCombinationsList.remove(agarId);

        worldMessagesListener.onAgarRemove(agarId);

        if (agarOwner.hasNoAgars()) {
            worldMessagesListener.onAllAgarsRemoved(agarOwnerId);
        }

    }

    public void splitAgars(Integer agarOwnerId) {
        AgarOwner agarOwner = agarOwnerStorage.get(agarOwnerId);
        agarOwner.forEachAgarItem(agarItem -> {
            AgarWorldManager.handleAgarSplitting(this, agarItem);
        });
        agarItemsToAdd.forEach(agarOwner::addAgarItem);
        agarItemsToAdd.clear();
    }

    public void updateAgar(Integer agarId, float newDirX, float newDirY, float velocity) {
        AgarItem agarItem = agarStorage.get(agarId);
        if (agarItem != null) {
            Agar agar = agarItem.getAgar();
            agar.setDirection(newDirX, newDirY);
            agar.setVelocity(velocity);
            //Notify listener
            worldMessagesListener.onAgarUpdate(agarItem);
        }
    }

    public void removeLater(Integer agarId) {
        agarItemsToRemove.add(agarStorage.get(agarId));
    }

    public void addLater(AgarItem agarItem) {
        agarItemsToAdd.add(agarItem);
    }

    public void step() {
        stepAgars();
    }

    //Listeners
    public void setWorldMessagesListener(WorldMessagesListener worldMessagesListener) {
        this.worldMessagesListener = worldMessagesListener;
    }

    public void forEachAgarItem(Consumer<AgarItem> consumer) {
        agarStorage.forEachItems(consumer);
    }

    public void forEachFood(BiConsumer<Integer, Food> biConsumer) {
        feedMap.forEach(biConsumer);
    }

    public void forEachAgarOwner(BiConsumer<Integer, AgarOwner> biConsumer) {
        agarOwnerStorage.forEachAgarOwner(biConsumer);
    }

}
