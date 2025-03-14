package ru.nsu.pozhidaev;

/**
 * Treat represents a block on the board that the snake can eat.
 * Once eaten, the treat is destroyed.
 */
public class Treat implements Block{
    int x;
    int y;

    /**
     * Returns the x-coordinate of the treat.
     *
     * @return the x-coordinate
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the treat.
     *
     * @return the y-coordinate
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the treat.
     *
     * @param x the x-coordinate to set
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the treat.
     *
     * @param y the y-coordinate to set
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }
}
