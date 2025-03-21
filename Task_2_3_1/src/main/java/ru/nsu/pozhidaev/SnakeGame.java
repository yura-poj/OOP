package ru.nsu.pozhidaev;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * SnakeGame is the main controller of the game, managing the game state and logic.
 */
public class SnakeGame {
    @Getter //should not change array after get!
    private ArrayList<Wall> walls;
    @Getter //should not change array after get!
    private ArrayList<Treat> treats;
    @Getter
    private int score;
    @Getter
    private int bestScore;
    @Getter
    private boolean gameOver;
    @Getter
    private boolean gameWon;

    private ArrayList<Snake> snakes;
    private ArrayList<SnakeBot> snakeBots;
    private ArrayList<Snake> deadSnakes;
    private Snake userSnake;

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
        snakeBots = new ArrayList<>();
        deadSnakes = new ArrayList<>();
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
     * Returns the body of the snake.
     *
     * @return the list of snake parts
     */
    public ArrayList<ArrayList<SnakePart>> getSnakesBodies() {
        ArrayList<ArrayList<SnakePart>> snakesBodies = new ArrayList<>();
        for(Snake snake : snakes) {
            snakesBodies.add(snake.getBody());
        }
        return snakesBodies;
    }

    /**
     * Moves the snake according to the current direction.
     */
    public void move() {
        if (gameOver) {
            return;
        }
        moveSnakes();
        checkCollision();
        checkLunch();
        checkWin();
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
        if (action == Action.UP || action == Action.DOWN
                || action == Action.LEFT || action == Action.RIGHT) {
            userSnake.setDirection(action);
        }
    }

    /**
     * Starts or restarts the game.
     */
    public void startOver() {
        List<Integer> snakeCoordinates = settings.getSnakeCoordinates();
        for(Snake snake : deadSnakes){
            snakeBots.add((SnakeBot) snake);
            snakes.add(snake);
        }
        deadSnakes.clear();
        for (int i = 0; i < snakes.size(); i++) {
            snakes.get(i).startOver(settings.getSnakeCoordinates().get(0),
                    settings.getSnakeCoordinates().get(1) + i*2);
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
    public boolean blockExist(int x, int y, List<Block> exceptBlocks) {
        return Stream.of(
                        walls.stream(),
                        treats.stream(),
                        snakes.stream().map(Snake::getBody).flatMap(List::stream)
                ).flatMap(s -> s)
                .filter(s -> exceptBlocks == null || !exceptBlocks.contains(s))
                .anyMatch(s -> s.getCoordinateX() == x && s.getCoordinateY() == y);
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
            if (isCollision(currentSnake.getHead())) {
                if (currentSnake == userSnake) {
                    gameOver = true;
                } else {
                    snakes.remove(currentSnake);
                    snakeBots.remove(currentSnake);
                    deadSnakes.add((SnakeBot) currentSnake);
                }
            }
        }
    }

    public boolean isCollision(SnakePart head) {

        if (head.getCoordinateY() >= settings.getHeight() || head.getCoordinateY() < 0
                || head.getCoordinateX() >= settings.getWidth() || head.getCoordinateX() < 0) {
            return true;
        }
        List<Block> blocks = new ArrayList<>();
        blocks.addAll(treats);
        blocks.add(head);
        return blockExist(head.getCoordinateX(), head.getCoordinateY(), blocks);
    }

    /**
     * Checks if the snake has eaten a treat.
     */
    private void checkLunch() {
        for (Snake currentSnake : snakes) {

            SnakePart head = currentSnake.getHead();
            for (Treat treat : treats) {
                if (head.getCoordinateX() == treat.getCoordinateX()
                        && head.getCoordinateY() == treat.getCoordinateY()) {
                    currentSnake.lunch();
                    appearTreat(treat);

                    if(currentSnake == userSnake) {
                        updateScore();
                    }
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
        treat.setBooked(false);
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
