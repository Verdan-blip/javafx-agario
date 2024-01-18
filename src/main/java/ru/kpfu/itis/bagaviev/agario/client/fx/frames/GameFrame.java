package ru.kpfu.itis.bagaviev.agario.client.fx.frames;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import ru.kpfu.itis.bagaviev.agario.client.fx.camera.GameCamera;
import ru.kpfu.itis.bagaviev.agario.client.fx.controllers.GameController;
import ru.kpfu.itis.bagaviev.agario.client.fx.event_handlers.AgarKeyEventHandler;
import ru.kpfu.itis.bagaviev.agario.client.fx.event_handlers.AgarMouseMoveEventHandler;
import ru.kpfu.itis.bagaviev.agario.client.fx.math.CoordinateSystem;
import ru.kpfu.itis.bagaviev.agario.client.fx.objects.AgarTextureItem;
import ru.kpfu.itis.bagaviev.agario.client.fx.textures.AgarTexture;
import ru.kpfu.itis.bagaviev.agario.client.fx.textures.FoodTexture;
import ru.kpfu.itis.bagaviev.agario.client.net.Client;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;
import ru.kpfu.itis.bagaviev.agario.engine.util.AgarItem;

import java.nio.file.Paths;
import java.util.*;


public class GameFrame extends Frame<GameController> {

    private static final String DEFAULT_NICKNAME = "Noobie";
    private static final float SCREEN_WIDTH = 960;
    private static final float SCREEN_HEIGHT = 960;
    private static final float MAP_WIDTH = 2048f;
    private static final float MAP_HEIGHT = 2048f;

    private final Client client;

    //Game Objects
    private final Map<Integer, AgarTextureItem> agarTextureItemMap;
    private final Map<Integer, FoodTexture> feedTextureMap;

    private final Map<Integer, String> agarOwnerMap;
    private Integer myAgarOwnerId;

    //Game control
    private AgarMouseMoveEventHandler agarMouseMoveEventHandler;
    private AgarKeyEventHandler agarKeyEventHandler;

    //Camera
    private final GameCamera camera;

    //Util
    private final CoordinateSystem coordinateSystem;

    //UI
    private final Scene scene;
    private final ConnectFrame connectFrame;
    private final LeaderboardFrame leaderboardFrame;
    private final Pane gameFieldPane;
    private final Pane uiPane;

    private void addToGameField(Node node) {
        gameFieldPane.getChildren().add(node);
    }

    private void removeFromGameField(Node node) {
        gameFieldPane.getChildren().remove(node);
    }

    private void updateAgarTextureItem(AgarItem newAgarItem) {

        Integer agarId = newAgarItem.getAgarId();

        AgarTextureItem agarTextureItem = agarTextureItemMap.get(agarId);
        AgarTexture agarTexture = agarTextureItem.getAgarTexture();

        Agar newAgar = newAgarItem.getAgar();

        agarTexture.setPosition(
                coordinateSystem.xToFxCoordinates(newAgar.getX()),
                coordinateSystem.yToFxCoordinates(newAgar.getY())
        );
        agarTexture.setMass(newAgar.getMass());
    }

    private void updateCamera() {
        float x = 0f, y = 0f;
        float sumMass = 0f;
        float myAgarTextureItemsCount = 0f;
        for (AgarTextureItem agarTextureItem : agarTextureItemMap.values()) {
            Integer agarOwnerId = agarTextureItem.getAgarOwnerId();
            AgarTexture agarTexture = agarTextureItem.getAgarTexture();
            if (agarOwnerId.equals(myAgarOwnerId)) {
                x += agarTexture.getPositionX();
                y += agarTexture.getPositionY();
                sumMass += agarTexture.getMass();
                myAgarTextureItemsCount++;
            }
        }
        x /= myAgarTextureItemsCount;
        y /= myAgarTextureItemsCount;
        float zoom = 10f / (float) Math.sqrt(sumMass) / (Math.min(myAgarTextureItemsCount, 2) * 0.25f) * 0.5f;
        camera.setCenter(x, y);
        camera.setZoom(zoom);
    }

    public GameFrame(Client client) {
        super(Paths.get("src/main/resources/fxml/game.fxml"));
        this.client = client;

        this.agarTextureItemMap = new HashMap<>();
        this.feedTextureMap = new HashMap<>();

        this.agarOwnerMap = new HashMap<>();
        this.myAgarOwnerId = -1;

        this.connectFrame = new ConnectFrame(client);
        this.connectFrame.setNickname(DEFAULT_NICKNAME);
        this.connectFrame.getRoot().setViewOrder(-1);

        this.leaderboardFrame = new LeaderboardFrame(agarOwnerMap, agarTextureItemMap);

        this.gameFieldPane = controller.getBorderPaneGameField();
        this.gameFieldPane.setPrefSize(MAP_WIDTH, MAP_HEIGHT);

        this.uiPane = controller.getAnchorPaneUi();

        this.coordinateSystem = new CoordinateSystem(MAP_WIDTH, MAP_HEIGHT);

        this.camera = new GameCamera(gameFieldPane, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.camera.setCenter(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);

        this.scene = new Scene(uiPane);

        showConnectFrame();
    }

    public void startGame(Integer agarOwnerId, String nickname) {
        addAgarOwner(agarOwnerId, nickname);
        myAgarOwnerId = agarOwnerId;

        //Setting mouse events listener
        agarMouseMoveEventHandler = new AgarMouseMoveEventHandler(
                MAP_WIDTH, MAP_HEIGHT, agarOwnerId, client, agarTextureItemMap
        );
        agarKeyEventHandler = new AgarKeyEventHandler(agarOwnerId, client);

        //Adding handlers
        gameFieldPane.addEventHandler(MouseEvent.MOUSE_MOVED, agarMouseMoveEventHandler);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, agarKeyEventHandler);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(leaderboardFrame::update);
            }
        }, 0, 5_000);

        hideConnectFrame();
        showLeaderboard();
    }

    public void finishGame() {
        gameFieldPane.removeEventHandler(MouseEvent.MOUSE_MOVED, agarMouseMoveEventHandler);
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, agarKeyEventHandler);
        //camera.setZoom(1);
        showConnectFrame();
        hideLeaderboard();
    }

    public void updateFoodTexture(FoodTexture foodTexture, Food food) {
        foodTexture.setPosition(
                coordinateSystem.xToFxCoordinates(food.getX()),
                coordinateSystem.yToFxCoordinates(food.getY())
        );
        foodTexture.setMass(food.getMass());
    }

    public void addAgarOwner(Integer agarOwnerId, String nickname) {
        agarOwnerMap.put(agarOwnerId, nickname);
    }

    public void removeAgarOwner(Integer agarOwnerId) {
        agarOwnerMap.remove(agarOwnerId);
        if (agarOwnerId.equals(myAgarOwnerId)) {
            myAgarOwnerId = -1;
            finishGame();
        }
    }

    public void addAgar(AgarItem agarItem) {
        Integer agarOwnerId = agarItem.getAgarOwnerId();
        Integer agarId = agarItem.getAgarId();
        AgarTexture agarTexture = new AgarTexture(agarOwnerMap.get(agarOwnerId), Color.RED);

        agarTextureItemMap.put(agarId, new AgarTextureItem(agarOwnerId, agarTexture));

        updateAgarTextureItem(agarItem);

        addToGameField(agarTexture.getCircle());
        addToGameField(agarTexture.getText());
    }


    public void updateAgar(AgarItem agarItem) {
        updateAgarTextureItem(agarItem);
        if (myAgarOwnerId.equals(agarItem.getAgarOwnerId())) {
            updateCamera();
        }
    }

    public void removeAgar(Integer agarId) {
        AgarTextureItem agarTextureItem = agarTextureItemMap.get(agarId);
        AgarTexture agarTexture = agarTextureItem.getAgarTexture();

        removeFromGameField(agarTexture.getCircle());
        removeFromGameField(agarTexture.getText());

        agarTextureItemMap.remove(agarId);
    }


    public void updateFood(Integer id, Food food) {
        FoodTexture foodTexture = feedTextureMap.get(id);
        if (foodTexture == null) {
            foodTexture = new FoodTexture(food.getColor());
            feedTextureMap.put(id, foodTexture);
            addToGameField(foodTexture.getCircle());
        }
        updateFoodTexture(foodTexture, food);
    }

    public void showConnectFrame() {
        Parent connectFrameRoot = connectFrame.root;
        connectFrameRoot.setTranslateX(SCREEN_WIDTH / 2 - ConnectFrame.WIDTH / 2);
        connectFrameRoot.setTranslateY(SCREEN_HEIGHT / 2 - ConnectFrame.HEIGHT / 2);
        uiPane.getChildren().add(connectFrame.root);
    }

    public void showLeaderboard() {
        Parent leaderboardFrameRoot = leaderboardFrame.root;
        leaderboardFrameRoot.setTranslateX(SCREEN_WIDTH - LeaderboardFrame.WIDTH);
        leaderboardFrameRoot.setTranslateY(0);
        uiPane.getChildren().add(leaderboardFrame.root);
    }

    public void hideLeaderboard() {
        uiPane.getChildren().remove(leaderboardFrame.root);
    }

    public void hideConnectFrame() {
        uiPane.getChildren().remove(connectFrame.root);
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public Parent getRoot() {
        return gameFieldPane;
    }
}
