package ru.kpfu.itis.bagaviev.agario.client.fx.frames;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.kpfu.itis.bagaviev.agario.client.fx.controllers.ConnectController;
import ru.kpfu.itis.bagaviev.agario.client.net.Client;
import ru.kpfu.itis.bagaviev.agario.communication.messages.client.RegisterMeMessage;

import java.nio.file.Paths;

public class ConnectFrame extends Frame<ConnectController> {

    public ConnectFrame(Client client) {
        super(Paths.get("src/main/resources/fxml/connect.fxml"));
        Button buttonPlay = controller.getButtonPlay();
        TextField textFieldNickname = controller.getTextFieldNickname();
        buttonPlay.setOnAction(event ->
            client.sendMessage(new RegisterMeMessage(textFieldNickname.getText()))
        );
    }

    @Override
    public Parent getRoot() {
        return controller.getVBoxConnect();
    }

    public TextField getTextFieldNickname() {
        return controller.getTextFieldNickname();
    }

    public double getWidth() {
        return controller.getVBoxConnect().getWidth();
    }

    public double getHeight() {
        return controller.getVBoxConnect().getHeight();
    }

}
