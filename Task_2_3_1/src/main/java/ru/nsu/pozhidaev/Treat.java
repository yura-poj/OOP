package ru.nsu.pozhidaev;

/**
 * Treat represents a block on the board that the snake can eat.
 * Once eaten, the treat is destroyed.
 */
public class Treat implements Block {
    int coordinateX;
    int coordinateY;

    /**
     * Returns the x-coordinate of the treat.
     *
     * @return the x-coordinate
     */
    @Override
    public int getCoordinateX() {
        return coordinateX;
    }

    /**
     * Returns the y-coordinate of the treat.
     *
     * @return the y-coordinate
     */
    @Override
    public int getCoordinateY() {
        return coordinateY;
    }

    /**
     * Sets the x-coordinate of the treat.
     *
     * @param coordinateX the x-coordinate to set
     */
    @Override
    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    /**
     * Sets the y-coordinate of the treat.
     *
     * @param coordinateY the y-coordinate to set
     */
    @Override
    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }
}
