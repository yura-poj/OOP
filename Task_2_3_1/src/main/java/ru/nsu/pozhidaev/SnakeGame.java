package ru.nsu.pozhidaev;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * SnakeGame is the main game controller class that manages the game state,
 * including snakes, treats, walls, and game logic. It handles game initialization,
 * movement, collisions, scoring, and win/lose conditions.
 */
public class SnakeGame {
    /**
     * List of wall objects in the game.
     */
    @Getter //should not change array after get!
    private ArrayList<Wall> walls;

    /**
     * List of treat objects that snakes can collect.
     */
    @Getter //should not change array after get!
    private ArrayList<Treat> treats;

    /**
     * List of all snakes in the game (both player and bots).
     */
    private ArrayList<Snake> snakes;

    /**
     * List of bot snakes in the game.
     */
    private ArrayList<SnakeBot> snakeBots;

    /**
     * The player-controlled snake.
     */
    @Getter
    private Snake userSnake;

    /**
     * Current game score.
     */
    @Getter
    private int score;

    /**
     * Best score achieved in the game session.
     */
    @Getter
    private int bestScore;

    /**
     * Flag indicating if the game is over.
     */
    @Getter
    private boolean gameOver;

    /**
     * Flag indicating if the game has been won.
     */
    @Getter
    private boolean gameWon;

    /**
     * Game settings configuration.
     */
    private GameSettings settings;

    /**
     * Constructs a new game with the specified settings.
     *
     * @param settings the game settings to use
     */
    public SnakeGame(GameSettings settings) {
        this.settings = settings;
        treats = new ArrayList<>();
        walls = new ArrayList<>();
        snakes = new ArrayList<>();
        snakeBots = new ArrayList<>();
        setUpWalls();
        for (int i = 0; i < settings.getNumberFood(); i++) {
            treats.add(new Treat(0, 0));
        }
        userSnake = new Snake(this);
        snakes.add(userSnake);
        for (int i = 0; i < settings.getBotsNumber(); i++) {
            SnakeBot snakeBot = new SnakeBot(this);
            snakes.add(snakeBot);
            snakeBots.add(snakeBot);
        }
        startOver();
    }

    /**
     * Returns the body segments of all snakes in the game.
     *
     * @return list of snake body segments for all snakes
     */
    public ArrayList<ArrayList<SnakePart>> getSnakesBodies() {
        ArrayList<ArrayList<SnakePart>> bodies = new ArrayList<>();
        for (Snake snake : snakes) {
            bodies.add(snake.getBody());
        }
        return bodies;
    }

    /**
     * Returns the player-controlled snake.
     *
     * @return the user's snake
     */
    public Snake getUserSnake() {
        return userSnake;
    }

    /**
     * Returns the list of treats in the game.
     *
     * @return list of treats
     */
    public ArrayList<Treat> getTreats() {
        return new ArrayList<>(treats);
    }

    /**
     * Returns the list of walls in the game.
     *
     * @return list of walls
     */
    public ArrayList<Wall> getWalls() {
        return new ArrayList<>(walls);
    }

    /**
     * Moves all snakes and updates the game state.
     * Checks for collisions, treats collected, and win conditions.
     */
    public void move() {
        if (gameOver) {
            return;
        }
        setUpClosestTreats();
        setUpResolvedDirections();
        moveSnakes();
        checkCollision();
        checkLunch();
        checkWin();
    }

    /**
     * Sets up the closest treats for each bot snake.
     */
    private void setUpClosestTreats() {
        Treat closestTreat = null;
        int closestSum;
        for (SnakeBot snakeBot : snakeBots) {
            closestSum = Integer.MAX_VALUE;
            for (Treat treat : treats) {
                int sum = (int) Math.sqrt(Math.pow(treat.getCoordinateX(), 2) + Math.pow(treat.getCoordinateY(), 2));
                if (sum < closestSum) {
                    closestTreat = treat;
                    closestSum = sum;
                }
            }
            snakeBot.setClosestTreat(closestTreat);
        }
    }

    /**
     * Sets up resolved directions for bot snakes based on available moves.
     */
    private void setUpResolvedDirections() {
        ArrayList<Action> resolvedDirections = new ArrayList<>();
        for (SnakeBot snakeBot : snakeBots) {
            if (blockExist(snakeBot.getHead().getCoordinateX() + 1, snakeBot.getHead().getCoordinateY(),
                    new ArrayList<>(treats))) {
                resolvedDirections.add(Action.RIGHT);
            }
            if (blockExist(snakeBot.getHead().getCoordinateX() - 1, snakeBot.getHead().getCoordinateY(),
                    new ArrayList<>(treats))) {
                resolvedDirections.add(Action.LEFT);
            }
            if (blockExist(snakeBot.getHead().getCoordinateX(), snakeBot.getHead().getCoordinateY() + 1,
                    new ArrayList<>(treats))) {
                resolvedDirections.add(Action.DOWN);
            }
            if (blockExist(snakeBot.getHead().getCoordinateX(), snakeBot.getHead().getCoordinateY() - 1,
                    new ArrayList<>(treats))) {
                resolvedDirections.add(Action.UP);
            }
            snakeBot.setResolvedDirections(resolvedDirections);
        }
    }

    /**
     * Moves all snakes in the game.
     */
    private void moveSnakes() {
        for (Snake snake : snakes) {
            snake.move();
        }
    }

    /**
     * Receives and processes a movement action for the player's snake.
     *
     * @param action the movement action to process
     */
    public void receiveAction(Action action) {
        if (action == Action.UP || action == Action.DOWN
                || action == Action.LEFT || action == Action.RIGHT) {
            userSnake.setDirection(action);
        }
    }

    /**
     * Starts or restarts the game, resetting all game elements to their initial state.
     */
    public void startOver() {
        List<Integer> snakeCoordinates = settings.getSnakeCoordinates();

        for (int i = 0; i < snakes.size(); i++) {
            snakes.get(i).startOver(settings.getSnakeCoordinates().get(0),
                    settings.getSnakeCoordinates().get(1) + i);
        }
        score = 0;
        for (Treat treat : treats) {
            appearTreat(treat);
        }
        gameOver = false;
        gameWon = false;
    }

    /**
     * Checks if a block exists at the specified coordinates, excluding certain blocks.
     *
     * @param x            the x-coordinate to check
     * @param y            the y-coordinate to check
     * @param exceptBlocks list of blocks to exclude from the check
     * @return true if a block exists at the coordinates, false otherwise
     */
    public boolean blockExist(int x, int y, ArrayList<Block> exceptBlocks) {
        return Stream.of(
                        walls.stream(),
                        treats.stream(),
                        snakes.stream().map(Snake::getBody).flatMap(List::stream)
                ).flatMap(s -> s)
                .filter(s -> exceptBlocks == null || !exceptBlocks.contains(s))
                .anyMatch(s -> s.getCoordinateX() == x && s.getCoordinateY() == y);
    }

    /**
     * Sets up the walls in the game based on the game settings.
     */
    private void setUpWalls() {
        List<List<Integer>> wallsCoordinates = settings.getWalls();
        for (List<Integer> wallCoordinates : wallsCoordinates) {
            walls.add(new Wall(wallCoordinates.get(0), wallCoordinates.get(1)));
        }
    }

    /**
     * Checks for collisions between snakes and walls or other snakes.
     */
    private void checkCollision() {
        for (Snake currentSnake : snakes) {
            if (isCollision(currentSnake)) {
                if (currentSnake == userSnake) {
                    gameOver = true;
                } else {
                    snakes.remove(currentSnake);
                }
            }
        }
    }

    /**
     * Checks if a snake has collided with a wall or another snake.
     *
     * @param currentSnake the snake to check for collisions
     * @return true if a collision occurred, false otherwise
     */
    private boolean isCollision(Snake currentSnake) {
        SnakePart head = currentSnake.getHead();

        if (head.getCoordinateY() >= settings.getHeight() || head.getCoordinateY() < 0
                || head.getCoordinateX() >= settings.getWidth() || head.getCoordinateX() < 0) {
            return true;
        }
        return blockExist(head.getCoordinateX(), head.getCoordinateY(),
                new ArrayList<>(Arrays.asList(head, treats)));
    }

    /**
     * Checks if any snake has collected a treat and updates the game state accordingly.
     */
    private void checkLunch() {
        for (Snake currentSnake : snakes) {
            SnakePart head = currentSnake.getHead();
            for (Treat treat : treats) {
                if (head.getCoordinateX() == treat.getCoordinateX()
                        && head.getCoordinateY() == treat.getCoordinateY()) {
                    currentSnake.lunch();
                    updateScore();
                    appearTreat(treat);
                    return;
                }
            }
        }
    }

    /**
     * Updates the score when a treat is collected.
     */
    private void updateScore() {
        score++;
        if (bestScore < score) {
            bestScore = score;
        }
    }

    /**
     * Places a treat at a random unoccupied location on the game board.
     *
     * @param treat the treat to place
     */
    private void appearTreat(Treat treat) {
        int x = 0;
        int y = 0;
        do {
            x = (int) (Math.random() * settings.getWidth());
            y = (int) (Math.random() * settings.getHeight());
        } while (blockExist(x, y, null));

        treat.setCoordinateX(x);
        treat.setCoordinateY(y);
    }

    /**
     * Checks if the win condition has been met.
     */
    private void checkWin() {
        if (score >= settings.getNumberFoodToWin()) {
            gameOver = true;
            gameWon = true;
        }
    }

    /**
     * Checks if a collision would occur at the specified coordinates.
     *
     * @param snakePart the snake part to check for collision
     * @return true if a collision would occur, false otherwise
     */
    public boolean isCollision(SnakePart snakePart) {
        if (snakePart.getCoordinateY() >= settings.getHeight() || snakePart.getCoordinateY() < 0
                || snakePart.getCoordinateX() >= settings.getWidth() || snakePart.getCoordinateX() < 0) {
            return true;
        }
        return blockExist(snakePart.getCoordinateX(), snakePart.getCoordinateY(),
                new ArrayList<>(Arrays.asList(snakePart)));
    }
}
