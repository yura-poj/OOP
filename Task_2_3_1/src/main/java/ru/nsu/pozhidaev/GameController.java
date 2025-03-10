package ru.nsu.pozhidaev;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameController {
    @FXML
    private Pane gamePane;
    @FXML
    private Label scoreLabel;

    private int score;

    private Stage stage;

    public void initData(Stage stage) {
        this.stage = stage;
        gamePane.requestFocus();
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {

        scoreLabel.setText(String.valueOf(score));

        switch (event.getCode()) {
            case UP:
                score += 1;
                break;
            case DOWN:
                // TODO: Move snake down
                break;
            case LEFT:
                // TODO: Move snake left
                break;
            case RIGHT:
                // TODO: Move snake right
                break;
        }
    }
}