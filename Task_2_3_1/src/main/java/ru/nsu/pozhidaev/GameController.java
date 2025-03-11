package ru.nsu.pozhidaev;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameController {
    @FXML
    private Pane gamePane;
    @FXML
    private Label scoreLabel;

    private SnakeGame snakeGame;
    private Rectangle[][] gridCells;
    private int score = 0;
    private Stage stage;

    public void initData(Stage stage, SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
        this.stage = stage;
        gamePane.requestFocus();
        
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

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> drawGame()));

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
        updateScore();

        if (snakeGame.isGameOver()) {
            scoreLabel.setText(String.valueOf("You loose"));
        }
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
    private void handleExit() {
        System.exit(0);
    }

    public void updateScore() {
        scoreLabel.setText(String.valueOf(snakeGame.getScore()));
    }
}