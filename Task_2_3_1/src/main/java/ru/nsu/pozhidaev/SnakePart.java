package ru.nsu.pozhidaev;

/**
 * SnakePart represents a part of the snake's body.
 * It acts as a block that can be an obstacle for the snake.
 */
public class SnakePart implements Block {
    private int coordinateX;
    private int coordinateY;

    /**
     * Constructs a SnakePart with the specified coordinates.
     *
     * @param coordinateX the x-coordinate of the snake part
     * @param coordinateY the y-coordinate of the snake part
     */
    public SnakePart(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    /**
     * Returns the x-coordinate of the snake part.
     *
     * @return the x-coordinate
     */
    @Override
    public int getCoordinateX() {
        return coordinateX;
    }

    /**
     * Sets the x-coordinate of the snake part.
     *
     * @param coordinateX the x-coordinate to set
     */
    @Override
    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    /**
     * Returns the y-coordinate of the snake part.
     *
     * @return the y-coordinate
     */
    @Override
    public int getCoordinateY() {
        return coordinateY;
    }

    /**
     * Sets the y-coordinate of the snake part.
     *
     * @param coordinateY the y-coordinate to set
     */
    @Override
    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }
}
