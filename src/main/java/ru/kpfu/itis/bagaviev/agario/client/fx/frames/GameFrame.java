package ru.kpfu.itis.bagaviev.agario.client.fx.frames;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import ru.kpfu.itis.bagaviev.agario.client.fx.camera.AgarFollowingCamera;
import ru.kpfu.itis.bagaviev.agario.client.fx.controllers.GameController;
import ru.kpfu.itis.bagaviev.agario.client.fx.handlers.AgarMouseMoveEventHandler;
import ru.kpfu.itis.bagaviev.agario.client.fx.handlers.AgarMouseZoomEventHandler;
import ru.kpfu.itis.bagaviev.agario.client.fx.math.CoordinateSystem;
import ru.kpfu.itis.bagaviev.agario.client.fx.objects.AgarTexture;
import ru.kpfu.itis.bagaviev.agario.client.fx.objects.FoodTexture;
import ru.kpfu.itis.bagaviev.agario.client.fx.objects.Player;
import ru.kpfu.itis.bagaviev.agario.client.net.Client;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;
import ru.kpfu.itis.bagaviev.agario.engine.objects.Food;

import java.nio.file.Paths;
import java.util.*;

public class GameFrame extends Frame<GameController> {

    private static final String DEFAULT_NICKNAME = "Noobie";

    private final ConnectFrame connectFrame;

    private final Client client;

    //Game Objects
    private final Map<Integer, AgarTexture> agarTextureMap;
    private final Map<Integer, FoodTexture> feedTextureMap;
    private Integer myAgarId;

    //Game control
    private AgarMouseMoveEventHandler agarMouseMoveEventHandler;
    private AgarMouseZoomEventHandler agarMouseZoomEventHandler;

    //Util
    private final CoordinateSystem coordinateSystem;
    private final BorderPane borderPaneGameField;
    private final AgarFollowingCamera camera;

    public GameFrame(Client client) {
        super(Paths.get("src/main/resources/fxml/game.fxml"));
        this.client = client;

        this.connectFrame = new ConnectFrame(client);
        this.connectFrame.controller.getTextFieldNickname().setText(DEFAULT_NICKNAME);
        this.connectFrame.getRoot().setViewOrder(-1);

        this.agarTextureMap = new HashMap<>();
        this.feedTextureMap = new HashMap<>();

        this.coordinateSystem = new CoordinateSystem(960, 640);
        this.borderPaneGameField = controller.getBorderPaneGameField();

        this.camera = new AgarFollowingCamera(borderPaneGameField);
    }

    public void startGameWith(Integer agarId, Agar agar) {

        addAgar(agarId, connectFrame.getTextFieldNickname().getText(), agar);

        //Saving id
        myAgarId = agarId;

        //Setting mouse events listener
        agarMouseMoveEventHandler = new AgarMouseMoveEventHandler(client, new Player(agarId, agarTextureMap.get(agarId)));
        agarMouseZoomEventHandler = new AgarMouseZoomEventHandler(camera);

        //Adding handlers
        borderPaneGameField.addEventHandler(MouseEvent.MOUSE_MOVED, agarMouseMoveEventHandler);
        borderPaneGameField.addEventHandler(ScrollEvent.SCROLL, agarMouseZoomEventHandler);

        camera.setZoom(3f, 3f);

    }

    public void finishGame() {
        myAgarId = null;

        borderPaneGameField.removeEventHandler(MouseEvent.MOUSE_MOVED, agarMouseMoveEventHandler);
        borderPaneGameField.removeEventHandler(ScrollEvent.SCROLL, agarMouseZoomEventHandler);

        camera.setZoom(1f, 1f);
        camera.set(0, 0);

        showConnectFrame();
    }

    public void addAgar(Integer agarId, String nickname, Agar agar) {
        //Creating texture for agar
        AgarTexture agarTexture = new AgarTexture(nickname, Color.GREEN);
        agarTexture.setPosition(
                coordinateSystem.xToFxCoordinates(agar.getX()),
                coordinateSystem.yToFxCoordinates(agar.getY())
        );
        agarTexture.setScale(agar.getMass());

        //Adding texture to map
        agarTextureMap.put(agarId, agarTexture);

        //Adding texture to frame
        borderPaneGameField.getChildren().add(agarTexture.getCircle());
        borderPaneGameField.getChildren().add(agarTexture.getText());
    }

    public void updateAgar(Integer id, Agar agar) {
        AgarTexture agarTexture = agarTextureMap.get(id);
        agarTexture.setPosition(
              coordinateSystem.xToFxCoordinates(agar.getX()),
              coordinateSystem.yToFxCoordinates(agar.getY())
        );

        agarTexture.setScale(agar.getMass());

        //Updating camera position
        if (id.equals(myAgarId)) {
            camera.update(agar);
        }

    }

    public void removeAgar(Integer agarId) {
        AgarTexture agarTexture = agarTextureMap.get(agarId);

        borderPaneGameField.getChildren().remove(agarTexture.getCircle());
        borderPaneGameField.getChildren().remove(agarTexture.getText());

        agarTextureMap.remove(agarId);
        if (agarId.equals(myAgarId)) {
            finishGame();
        }
    }

    public void updateFood(Integer id, Food food) {
        FoodTexture foodTexture = feedTextureMap.get(id);
        if (foodTexture == null) {
            foodTexture = new FoodTexture(food.getColor());
            feedTextureMap.put(id, foodTexture);
            borderPaneGameField.getChildren().add(foodTexture.getCircle());
        }
        foodTexture.setPosition(
                coordinateSystem.xToFxCoordinates(food.getX()),
                coordinateSystem.yToFxCoordinates(food.getY())
        );
        foodTexture.setMass(food.getMass());
    }

    public void showConnectFrame() {
        borderPaneGameField.setCenter(connectFrame.getRoot());
    }

    public void hideConnectFrame() {
        borderPaneGameField.setCenter(null);
    }

    @Override
    public Parent getRoot() {
        return borderPaneGameField;
    }

}
