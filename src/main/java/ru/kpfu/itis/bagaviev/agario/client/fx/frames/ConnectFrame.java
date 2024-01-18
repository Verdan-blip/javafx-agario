package ru.kpfu.itis.bagaviev.agario.client.fx.frames;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.bagaviev.agario.client.fx.controllers.ConnectController;
import ru.kpfu.itis.bagaviev.agario.client.net.Client;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.RegisterMeMessage;

import java.nio.file.Paths;

public class ConnectFrame extends Frame<ConnectController> {

    public static final float WIDTH = 256f;
    public static final float HEIGHT = 128f;

    public ConnectFrame(Client client) {
        super(Paths.get("src/main/resources/fxml/connect.fxml").toAbsolutePath());

        VBox vBox = controller.getVBoxConnect();
        vBox.setPrefWidth(WIDTH);
        vBox.setPrefHeight(HEIGHT);

        Button buttonPlay = controller.getButtonPlay();
        TextField textFieldNickname = controller.getTextFieldNickname();
        buttonPlay.setOnAction(event ->
            client.sendMessage(new RegisterMeMessage(textFieldNickname.getText()))
        );
    }

    public void setNickname(String nickname) {
        controller.getTextFieldNickname().setText(nickname);
    }

    @Override
    public Parent getRoot() {
        return controller.getVBoxConnect();
    }

    public void setPosition(float x, float y) {
        root.setTranslateX(x);
        root.setTranslateX(y);
    }

    public String getNickname() {
        return controller.getTextFieldNickname().getText();
    }

}
