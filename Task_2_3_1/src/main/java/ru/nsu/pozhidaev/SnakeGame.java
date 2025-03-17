package ru.nsu.pozhidaev;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * SnakeGame is the main controller of the game, managing the game state and logic.
 */
public class SnakeGame {
    private ArrayList<Wall> walls;
    private ArrayList<Treat> treats;
    private ArrayList<Snake> snakes;
    private ArrayList<SnakeBot> snakeBots;
    private Snake userSnake;
    @Getter
    private int score;
    @Getter
    private int bestScore;
    @Getter
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
        snakes = new ArrayList<>();
        setUpWalls();
        for (int i = 0; i < settings.getNumberFood(); i++) {
            treats.add(new Treat());
        }
        userSnake = new Snake();
        snakes.add(userSnake);
        for (int i = 0; i < settings.getBotsNumber(); i++) {
            SnakeBot snakeBot = new SnakeBot();
            snakes.add(snakeBot);
            snakeBots.add(snakeBot);
        }
        startOver();
    }

    /**
     * Returns the body of the snake.
     *
     * @return the list of snake parts
     */
    public ArrayList<SnakePart> getSnakeBody() {
        return userSnake.getBody();
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
        setUpClosestTreats();
        SetUpResolvedDirections();
        moveSnakes();
        checkCollision();
        checkLunch();
        checkWin();
    }

    private void SetUpResolvedDirections() {
        for (SnakeBot snakeBot : snakeBots) {
            blockExist(snakeBot.getHead().getCoordinateX(), snakeBot.getHead().getCoordinateY(), );
        }
    }

    private void setUpClosestTreats() {
        Treat closestTreat = null;
        int closestSum;
        for (SnakeBot snakeBot : snakeBots) {
            closestSum = Integer.MAX_VALUE;
            for (Treat treat : treats) {
                int sum = (int) Math.sqrt(treat.getCoordinateX() ^ 2 + treat.getCoordinateY() ^ 2);
                if (sum < closestSum) {
                    closestTreat = treat;
                    closestSum = sum;
                }
            }
            snakeBot.setClosestTreat(closestTreat);
        }
    }

    private void moveSnakes() {
        for (Snake snake : snakes) {
            snake.move();
        }
    }

    /**
     * Receives an action to change the direction of the snake.
     *
     * @param action the action to change the direction
     */
    public void receiveAction(Action action) {
        if (action==Action.UP || action==Action.DOWN
                || action==Action.LEFT || action==Action.RIGHT) {
            userSnake.setDirection(action);
        }
    }

    /**
     * Starts or restarts the game.
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
     * Checks if a block exists at the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     *
     * @return true if a block exists, false otherwise
     */
    public boolean blockExist(int x, int y, ArrayList<Block> exceptBlocks) {
        return Stream.of(
                        walls.stream(),
                        treats.stream(),
                        snakes.stream().map(Snake::getBody).flatMap(List::stream)
                ).flatMap(s -> s)
                .filter(s -> exceptBlocks == null || !exceptBlocks.contains(s))
                .anyMatch(s -> s.getCoordinateX()==x && s.getCoordinateY()==y);
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
        for (Snake currentSnake : snakes) {
            if (isColision(currentSnake)) {
                if (currentSnake==userSnake) {
                    gameOver = true;
                } else {
                    snakes.remove(currentSnake);
                }
            }
        }
    }

    private boolean isColision(Snake currentSnake) {
        SnakePart head = currentSnake.getHead();
        ArrayList<SnakePart> tail = currentSnake.getBody();
        tail.remove(0);
        ArrayList<Snake> otherSnakes = new ArrayList<>(snakes);
        otherSnakes.remove(currentSnake);

        if (head.getCoordinateY() >= settings.getHeight() || head.getCoordinateY() < 0
                || head.getCoordinateX() >= settings.getWidth() || head.getCoordinateX() < 0) {
            return true;
        }
        return Stream.of(
                        walls.stream(),
                        tail.stream(),
                        otherSnakes.stream().map(Snake::getBody).flatMap(List::stream)
                )
                .flatMap(s -> s)
                .anyMatch(s -> s.getCoordinateX()==head.getCoordinateX()
                        && s.getCoordinateY()==head.getCoordinateY());
    }

    /**
     * Checks if the snake has eaten a treat.
     */
    private void checkLunch() {
        for (Snake currentSnake : snakes) {

            SnakePart head = currentSnake.getHead();
            for (Treat treat : treats) {
                if (head.getCoordinateX()==treat.getCoordinateX()
                        && head.getCoordinateY()==treat.getCoordinateY()) {
                    currentSnake.lunch();
                    updateScore();
                    appearTreat(treat);
                    return;
                }
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
        } while (blockExist(x, y, null));

        treat.setCoordinateX(x);
        treat.setCoordinateY(y);
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
