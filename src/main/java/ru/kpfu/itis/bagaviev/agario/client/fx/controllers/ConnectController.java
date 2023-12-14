package ru.kpfu.itis.bagaviev.agario.client.fx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ConnectController {

    @FXML
    private VBox vBoxConnect;

    @FXML
    private Button buttonPlay;

    @FXML
    private TextField textFieldNickname;

    public Button getButtonPlay() {
        return buttonPlay;
    }

    public TextField getTextFieldNickname() {
        return textFieldNickname;
    }

    public VBox getVBoxConnect() {
        return vBoxConnect;
    }

}
