package ru.nsu.pozhidaev;

/**
 * Wall represents an obstacle block on the board.
 * If the snake bumps into a wall, the game is over.
 */
public class Wall implements Block {
    private int coordinateX;
    private int coordinateY;

    /**
     * Constructs a Wall with the specified coordinates.
     *
     * @param coordinateX the x-coordinate of the wall
     * @param coordinateY the y-coordinate of the wall
     */
    public Wall(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    /**
     * Returns the x-coordinate of the   wall.
     *
     * @return the x-coordinate
     */
    @Override
    public int getCoordinateX() {
        return coordinateX;
    }

    /**
     * Returns the y-coordinate of the wall.
     *
     * @return the y-coordinate
     */
    @Override
    public int getCoordinateY() {
        return coordinateY;
    }

    /**
     * Sets the x-coordinate of the wall.
     *
     * @param coordinateX the x-coordinate to set
     */
    @Override
    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    /**
     * Sets the y-coordinate of the wall.
     *
     * @param coordinateY the y-coordinate to set
     */
    @Override
    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }
}
