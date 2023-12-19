package ru.kpfu.itis.bagaviev.agario.client.fx.frames;

import ru.kpfu.itis.bagaviev.agario.client.fx.controllers.LeaderboardController;
import ru.kpfu.itis.bagaviev.agario.client.fx.objects.AgarTextureItem;

import java.nio.file.Paths;
import java.util.*;

public class LeaderboardFrame extends Frame<LeaderboardController> {

    public static final float WIDTH = 96f;
    public static final float HEIGHT = 128f;

    private final Map<Integer, String> agarOwnerIdNicknameMap;
    private final Map<Integer, AgarTextureItem> agarTextureItemMap;

    private final SortedMap<Float, String> sortedMassAgarOwnerMap;
    private final Map<Integer, Float> agarOwnerIdMassMap;

    public LeaderboardFrame(Map<Integer, String> agarOwnerIdNicknameMap, Map<Integer, AgarTextureItem> agarTextureItemMap) {
        super(Paths.get("src/main/resources/fxml/leaderboard.fxml"));
        this.controller.initializeLeaderBoard();

        this.agarOwnerIdNicknameMap = agarOwnerIdNicknameMap;
        this.agarTextureItemMap = agarTextureItemMap;

        this.sortedMassAgarOwnerMap = new TreeMap<>((o1, o2) -> Float.compare(o2, o1));
        this.agarOwnerIdMassMap = new HashMap<>();
    }

    public void update() {
        agarOwnerIdMassMap.clear();

        agarTextureItemMap.forEach((agarId, agarTextureItem) -> {
            Integer agarOwnerId = agarTextureItem.getAgarOwnerId();
            if (!agarOwnerIdMassMap.containsKey(agarOwnerId)) {
                agarOwnerIdMassMap.put(agarTextureItem.getAgarOwnerId(), agarTextureItem.getAgarTexture().getMass());
            } else {
                agarOwnerIdMassMap.put(
                        agarTextureItem.getAgarOwnerId(),
                        agarOwnerIdMassMap.get(agarOwnerId) + agarTextureItem.getAgarTexture().getMass()
                );
            }
        });

        agarOwnerIdMassMap.forEach((agarOwnerId, mass) -> {
            sortedMassAgarOwnerMap.put(mass, agarOwnerIdNicknameMap.get(agarOwnerId));
        });

        int place = 1;
        for (var pair : sortedMassAgarOwnerMap.entrySet()) {
            controller.setPlace(place, pair.getValue() + " " + pair.getKey().toString());
            place++;
        }

        while (place <= 5) {
            controller.setPlace(place, String.format("%d. -- -- --", place));
            place++;
        }

        sortedMassAgarOwnerMap.clear();

    }

}
