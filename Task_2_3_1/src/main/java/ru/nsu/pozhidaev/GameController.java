package ru.nsu.pozhidaev;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

public class GameController {
    @FXML
    private Pane gamePane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label bestScoreLabel;
    @FXML
    private VBox buttonBox;

    Timeline timeline;

    private SnakeGame snakeGame;
    private Rectangle[][] gridCells;
    private Stage stage;
    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty bestScore = new SimpleIntegerProperty(0);


    public void initData(Stage stage, SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
        this.stage = stage;
        gamePane.requestFocus();

        scoreLabel.textProperty().bind(score.asString("Score: %d"));
        bestScoreLabel.textProperty().bind(bestScore.asString("Best score: %d"));


        int rows = 20, cols = 20, size = 20;
        int speed = 200;
        GridPane grid = new GridPane();
        gridCells = new Rectangle[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle rect = new Rectangle(size, size, Color.LIGHTGRAY);
                grid.add(rect, col, row);
                gridCells[row][col] = rect;
            }
        }

        gamePane.getChildren().add(grid);

        timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> drawGame()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void drawGame() {
        for (int row = 0; row < gridCells.length; row++) {
            for (int col = 0; col < gridCells[0].length; col++) {
                gridCells[row][col].setFill(Color.PINK);
            }
        }

        drawSnake(snakeGame.getSnakeBody());
        drawWalls(snakeGame.getWalls());
        drawTreats(snakeGame.getTreats());
        snakeGame.move();

        if (snakeGame.isGameOver()) {
            gameOver();
        }

        score.set(snakeGame.getScore());
        bestScore.set(snakeGame.getBestScore());
    }

    private void drawSnake(ArrayList<SnakePart> snake) {
        for(SnakePart snakePart : snake) {
            gridCells[snakePart.getY()][snakePart.getX()].setFill(Color.GREEN);
        }
    }

    private void drawWalls(ArrayList<Wall> walls) {
        for(Wall wall : walls) {
            gridCells[wall.getY()][wall.getX()].setFill(Color.RED);
        }
    }

    private void drawTreats(ArrayList<Treat> treats) {
        for (Treat treat : treats) {
            gridCells[treat.getY()][treat.getX()].setFill(Color.BLUE);
        }
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                snakeGame.receiveAction(Action.UP);
                break;
            case DOWN:
                snakeGame.receiveAction(Action.DOWN);
                break;
            case LEFT:
                snakeGame.receiveAction(Action.LEFT);
                break;
            case RIGHT:
                snakeGame.receiveAction(Action.RIGHT);
                break;
        }
    }

    @FXML
    private void handleAgain() {
        snakeGame.startOver();
        buttonBox.setVisible(false);
        timeline.play();
    }

    private void gameOver() {
        scoreLabel.textProperty().unbind();
        scoreLabel.setText("You loose");
        buttonBox.setVisible(true);
        timeline.stop();
    }

    @FXML
    public void handleExit(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/intro.fxml"));
        Parent newView = loader.load();

        // Получаем контроллер, если нужно передать данные
        IntroController controller = loader.getController();
        controller.initData(stage);

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene newScene = new Scene(newView, 500, 500);
        currentStage.setScene(newScene);
        currentStage.show();
    }
}