package ru.nsu.pozhidaev;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * SnakeGame is the main controller of the game, managing the game state and logic.
 */
public class SnakeGame {
    private Snake snake;
    private ArrayList<Wall> walls;
    private ArrayList<Treat> treats;
    @Getter
    private int score;
    @Getter
    private int bestScore;
    private boolean gameOver;
    @Getter
    private boolean gameWon;
    private GameSettings settings;

    /**
     * Constructs a SnakeGame with the specified game settings.
     *
     * @param settings the game settings
     */
    public SnakeGame(GameSettings settings) {
        this.settings = settings;
        treats = new ArrayList<>();
        walls = new ArrayList<>();
        setUpWalls();
        for (int i = 0; i < settings.getNumberFood(); i++) {
            treats.add(new Treat());
        }
        startOver();
    }

    /**
     * Returns the body of the snake.
     *
     * @return the list of snake parts
     */
    public ArrayList<SnakePart> getSnakeBody() {
        return snake.getBody();
    }

    /**
     * Returns the list of treats.
     *
     * @return the list of treats
     */
    public ArrayList<Treat> getTreats() {
        return new ArrayList<>(treats);
    }

    /**
     * Returns the list of walls.
     *
     * @return the list of walls
     */
    public ArrayList<Wall> getWalls() {
        return new ArrayList<>(walls);
    }

    /**
     * Moves the snake according to the current direction.
     */
    public void move() {
        if (gameOver) {
            return;
        }

        snake.move();
        checkCollision();
        checkLunch();
        checkWin();
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Receives an action to change the direction of the snake.
     *
     * @param action the action to change the direction
     */
    public void receiveAction(Action action) {
        if (action == Action.UP || action == Action.DOWN || action == Action.LEFT || action == Action.RIGHT) {
            snake.setDirection(action);
        }
    }

    /**
     * Starts or restarts the game.
     */
    public void startOver() {
        List<Integer> snakeCoordinates = settings.getSnakeCoordinates();
        snake = new Snake(snakeCoordinates.get(0), snakeCoordinates.get(1));
        score = 0;
        for (Treat treat : treats) {
            appearTreat(treat);
        }
        gameOver = false;
        gameWon = false;
    }

    /**
     * Checks if a block exists at the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if a block exists, false otherwise
     */
    public boolean blockExist(int x, int y) {
        return Stream.of(
                        walls.stream(),
                        treats.stream(),
                        snake.getBody().stream()
                ).flatMap(s -> s)
                .anyMatch(s -> s.getX() == x && s.getY() == y);
    }

    /**
     * Sets up the walls for the game.
     */
    private void setUpWalls() {
        List<List<Integer>> wallsCoordinates = settings.getWalls();
        for (List<Integer> wallCoordinates : wallsCoordinates) {
            walls.add(new Wall(wallCoordinates.get(0), wallCoordinates.get(1)));
        }
    }

    /**
     * Checks for collisions with walls or the snake's body.
     */
    private void checkCollision() {
        SnakePart head = snake.getHead();
        if (head.getY() > settings.getHeight() || head.getY() < 0 || head.getX() > settings.getWidth() || head.getX() < 0) {
            gameOver = true;
            return;
        }
        gameOver = Stream.concat(
                walls.stream(),
                snake.getBody().stream().skip(1)
        ).anyMatch(s -> s.getX() == head.getX() && s.getY() == head.getY());
    }

    /**
     * Checks if the snake has eaten a treat.
     */
    private void checkLunch() {
        SnakePart head = snake.getHead();
        for (Treat treat : treats) {
            if (head.getX() == treat.getX() && head.getY() == treat.getY()) {
                snake.lunch();
                updateScore();
                appearTreat(treat);
                return;
            }
        }
    }

    /**
     * Updates the score when the snake eats a treat.
     */
    private void updateScore() {
        score++;
        if (bestScore < score) {
            bestScore = score;
        }
    }

    /**
     * Places a treat at a random location on the board.
     *
     * @param treat the treat to place
     */
    private void appearTreat(Treat treat) {
        int x = 0;
        int y = 0;
        do {
            x = (int) (Math.random() * settings.getWidth());
            y = (int) (Math.random() * settings.getHeight());
        } while (blockExist(x, y));

        treat.setX(x);
        treat.setY(y);
    }

    /**
     * Checks if the game has been won.
     */
    private void checkWin() {
        if (score >= settings.getNumberFoodToWin()) {
            gameOver = true;
            gameWon = true;
        }
    }
}
