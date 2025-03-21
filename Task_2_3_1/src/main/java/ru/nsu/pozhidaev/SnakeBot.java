package ru.nsu.pozhidaev;

import lombok.Setter;

import java.util.ArrayList;

/**
 * Abstract class representing a computer-controlled snake in the game.
 * This class extends the basic Snake class and adds AI behavior through
 * the abstract think() method that must be implemented by concrete bot classes.
 */
abstract class SnakeBot extends Snake {
    /**
     * The number of possible directions a snake can move.
     */
    final static int numberDirections = 4;

    /**
     * List of directions sorted by priority for the bot's movement.
     */
    ArrayList<Action> sortedDirections;

    /**
     * The closest treat that the bot can target.
     */
    @Setter
    Treat closestTreat;

    /**
     * List of valid directions that won't result in immediate collision.
     */
    ArrayList<Action> resolvedDirections;

    /**
     * Reference to the main game instance.
     */
    SnakeGame game;

    /**
     * Constructs a new SnakeBot with a reference to the game instance.
     *
     * @param game the game instance this bot belongs to
     */
    public SnakeBot(SnakeGame game) {
        super(game);
        this.game = game;
        sortedDirections = new ArrayList<>();
        sortedDirections.add(Action.RIGHT);
        sortedDirections.add(Action.LEFT);
        sortedDirections.add(Action.DOWN);
        sortedDirections.add(Action.UP);
    }

    /**
     * Overrides the basic move behavior to include AI decision making.
     * The bot thinks about its next move, checks for valid directions,
     * and avoids collisions before moving.
     */
    @Override
    public void move() {
        think();
        setUpResolvedDirections();
        checkForCollision();
        super.move();
    }

    /**
     * Checks for potential collisions and chooses the best available direction.
     * Uses the sorted list of preferred directions and checks them against
     * resolved (safe) directions.
     */
    private void checkForCollision() {
        for (Action action : sortedDirections) {
            if (resolvedDirections.contains(action)) {
                setDirection(action);
                break;
            }
        }
    }

    /**
     * Sets up the list of valid directions by checking adjacent positions
     * for potential collisions.
     */
    private void setUpResolvedDirections() {
        resolvedDirections = new ArrayList<>();

        int x = getHead().getCoordinateX();
        int y = getHead().getCoordinateY();

        if (!game.isCollision(new SnakePart(x + 1, y))) {
            resolvedDirections.add(Action.RIGHT);
        }
        if (!game.isCollision(new SnakePart(x - 1, y))) {
            resolvedDirections.add(Action.LEFT);
        }
        if (!game.isCollision(new SnakePart(x, y + 1))) {
            resolvedDirections.add(Action.DOWN);
        }
        if (!game.isCollision(new SnakePart(x, y - 1))) {
            resolvedDirections.add(Action.UP);
        }
    }

    /**
     * Abstract method that defines the bot's thinking strategy.
     * Must be implemented by concrete bot classes to determine
     * how the bot decides its next move.
     */
    abstract void think();
}
