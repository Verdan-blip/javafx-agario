package ru.kpfu.itis.bagaviev.agario.client.fx.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class GameController {

    @FXML
    private AnchorPane anchorPaneGameField;

    @FXML
    private AnchorPane anchorPaneUi;

    public Pane getBorderPaneGameField() {
        return anchorPaneGameField;
    }

    public Pane getAnchorPaneUi() {
        return anchorPaneUi;
    }

}
