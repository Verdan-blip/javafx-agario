package ru.kpfu.itis.bagaviev.agario.client.fx.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import ru.kpfu.itis.bagaviev.agario.client.fx.frames.GameFrame;
import ru.kpfu.itis.bagaviev.agario.client.fx.listeners.ServerMessageListener;
import ru.kpfu.itis.bagaviev.agario.client.net.Client;
import ru.kpfu.itis.bagaviev.agario.server.net.Server;

import java.nio.file.Paths;


public class JavaFxApplication extends Application {

    public static final String SCREEN_TITLE = "Agario";

    @Override
    public void start(Stage primaryStage) {
        Client client = new Client();

        GameFrame gameFrame = new GameFrame(client);
        gameFrame.showConnectFrame();

        client.setServerMessageListener(new ServerMessageListener(gameFrame));

        client.connect(Server.ADDRESS, Server.PORT);

        Scene scene = new Scene(gameFrame.getRoot());

        primaryStage.setScene(scene);
        primaryStage.setTitle(SCREEN_TITLE);
        primaryStage.show();
        primaryStage.setOnCloseRequest((windowEvent -> client.disconnect()));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
