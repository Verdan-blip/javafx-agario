package ru.kpfu.itis.bagaviev.agario.client.fx.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.kpfu.itis.bagaviev.agario.client.fx.frames.GameFrame;
import ru.kpfu.itis.bagaviev.agario.client.fx.listeners.ServerMessageListener;
import ru.kpfu.itis.bagaviev.agario.client.net.Client;
import ru.kpfu.itis.bagaviev.agario.server.net.Server;


public class JavaFxApplication extends Application {

    public static final String SCREEN_TITLE = "Agario";
    public static final float SCREEN_WIDTH = 960;
    public static final float SCREEN_HEIGHT = 960;

    @Override
    public void start(Stage primaryStage) {
        Client client = new Client();

        GameFrame gameFrame = new GameFrame(client);

        client.setServerMessageListener(new ServerMessageListener(gameFrame));
        client.connect(Server.ADDRESS, Server.PORT);

        Scene scene = gameFrame.getScene();

        primaryStage.setScene(scene);
        primaryStage.setTitle(SCREEN_TITLE);
        primaryStage.setWidth(SCREEN_WIDTH);
        primaryStage.setHeight(SCREEN_HEIGHT);
        primaryStage.show();
        primaryStage.setOnCloseRequest((windowEvent -> client.disconnect()));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
