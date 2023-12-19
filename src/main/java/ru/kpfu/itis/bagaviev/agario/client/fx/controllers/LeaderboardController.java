package ru.kpfu.itis.bagaviev.agario.client.fx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LeaderboardController {

    public static final int MAX_SHOWING_PLACES = 5;

    @FXML
    private VBox vBoxLeaderBoard;
    private final Label[] labelsPlaces;

    public LeaderboardController() {
        this.labelsPlaces = new Label[MAX_SHOWING_PLACES];
        for (int i = 0; i < labelsPlaces.length; i++) {
            Label label = new Label(String.format("%d. -- -- --", i + 1));
            label.setTextFill(Color.WHITE);
            labelsPlaces[i] = label;
        }
    }

    public void initializeLeaderBoard() {
        for (Label label : labelsPlaces) {
            vBoxLeaderBoard.getChildren().add(label);
        }
    }

    public void setPlace(int number, String text) {
        labelsPlaces[number - 1].setText(text);
    }

}
