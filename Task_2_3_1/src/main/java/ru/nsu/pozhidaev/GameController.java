package ru.nsu.pozhidaev;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

/**
 * GameController manages the game logic and user interactions during the game.
 */
public class GameController {
    @FXML
    private Pane gamePane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label bestScoreLabel;
    @FXML
    private VBox buttonBox;
    @FXML
    private Text winText;
    @FXML
    private Text looseText;

    private Timeline timeline;
    private SnakeGame snakeGame;
    private Rectangle[][] gridCells;
    private Stage stage;
    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty bestScore = new SimpleIntegerProperty(0);

    /**
     * Initializes the game controller with the primary stage and game settings.
     *
     * @param stage        the primary stage of the application
     * @param gameSettings the settings for the game
     */
    public void initData(Stage stage, GameSettings gameSettings) {
        snakeGame = new SnakeGame(gameSettings);
        this.stage = stage;
        gamePane.requestFocus();

        scoreLabel.textProperty().bind(score.asString("Score: %d"));
        bestScoreLabel.textProperty().bind(bestScore.asString("Best score: %d"));

        int rows = gameSettings.getHeight();
        int cols = gameSettings.getWidth();
        int size = gameSettings.getCubeSize();
        GridPane grid = new GridPane();
        gridCells = new Rectangle[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle rect = new Rectangle(size, size, Color.PINK);
                grid.add(rect, col, row);
                gridCells[row][col] = rect;
            }
        }

        gamePane.getChildren().add(grid);

        int speed = gameSettings.getSpeed();
        timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> drawGame()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Draws the current state of the game on the game pane.
     */
    private void drawGame() {
        for (int row = 0; row < gridCells.length; row++) {
            for (int col = 0; col < gridCells[0].length; col++) {
                gridCells[row][col].setFill(Color.PINK);
            }
        }

        drawSnake(snakeGame.getSnakesBodies());
        drawWalls(snakeGame.getWalls());
        drawTreats(snakeGame.getTreats());
        drawPlayerHead(snakeGame.getUserSnake().getHead());
        snakeGame.move();

        if (snakeGame.isGameOver()) {
            gameOver();
        }

        score.set(snakeGame.getScore());
        bestScore.set(snakeGame.getBestScore());
    }

    private void drawPlayerHead(SnakePart head) {
        gridCells[head.getCoordinateY()][head.getCoordinateX()].setFill(Color.LIGHTGREEN);
    }

    /**
     * Draws the snake on the game pane.
     *
     * @param snakesBodies the list of snake parts to draw
     */
    private void drawSnake(ArrayList<ArrayList<SnakePart>> snakesBodies) {
        for (ArrayList<SnakePart> snakeParts : snakesBodies) {
            for (SnakePart snakePart : snakeParts) {
                gridCells[snakePart.getCoordinateY()][snakePart.getCoordinateX()].setFill(Color.GREEN);
            }
        }
    }

    /**
     * Draws the walls on the game pane.
     *
     * @param walls the list of wall coordinates to draw
     */
    private void drawWalls(ArrayList<Wall> walls) {
        for (Wall wall : walls) {
            gridCells[wall.getCoordinateY()][wall.getCoordinateX()].setFill(Color.RED);
        }
    }

    /**
     * Draws the treats on the game pane.
     *
     * @param treats the list of treat coordinates to draw
     */
    private void drawTreats(ArrayList<Treat> treats) {
        for (Treat treat : treats) {
            gridCells[treat.getCoordinateY()][treat.getCoordinateX()].setFill(Color.BLUE);
        }
    }

    /**
     * Handles key press events to control the snake.
     *
     * @param event the key event
     */
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
            default:
                // Handle unexpected key events
                break;
        }
    }

    /**
     * Handles the action to restart the game.
     */
    @FXML
    private void handleAgain() {
        snakeGame.startOver();
        buttonBox.setVisible(false);
        winText.setVisible(false);
        looseText.setVisible(false);
        timeline.play();
    }

    /**
     * Handles the game over state, displaying the appropriate message.
     */
    private void gameOver() {
        if (snakeGame.isGameWon()) {
            winText.setVisible(true);
        } else {
            looseText.setVisible(true);
        }
        buttonBox.setVisible(true);
        timeline.stop();
    }

    /**
     * Handles the exit action to return to the intro screen.
     *
     * @param actionEvent the action event
     *
     * @throws IOException if the intro view cannot be loaded
     */
    @FXML
    public void handleExit(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/intro.fxml"));
        Parent newView = loader.load();

        IntroController controller = loader.getController();
        controller.initData(stage);

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene newScene = new Scene(newView, 500, 500);
        currentStage.setScene(newScene);
        currentStage.show();
    }
}