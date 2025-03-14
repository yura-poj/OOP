package ru.nsu.pozhidaev;

/**
 * SnakePart represents a part of the snake's body.
 * It acts as a block that can be an obstacle for the snake.
 */
public class SnakePart implements Block {
    private int x;
    private int y;

    /**
     * Constructs a SnakePart with the specified coordinates.
     *
     * @param x the x-coordinate of the snake part
     * @param y the y-coordinate of the snake part
     */
    public SnakePart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x-coordinate of the snake part.
     *
     * @param x the x-coordinate to set
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the snake part.
     *
     * @param y the y-coordinate to set
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the snake part.
     *
     * @return the x-coordinate
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the snake part.
     *
     * @return the y-coordinate
     */
    @Override
    public int getY() {
        return y;
    }
}
