package ru.nsu.pozhidaev;

/**
 * Wall represents an obstacle block on the board.
 * If the snake bumps into a wall, the game is over.
 */
public class Wall implements Block {
    private int x;
    private int y;

    /**
     * Constructs a Wall with the specified coordinates.
     *
     * @param x the x-coordinate of the wall
     * @param y the y-coordinate of the wall
     */
    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the   wall.
     *
     * @return the x-coordinate
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the wall.
     *
     * @return the y-coordinate
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the wall.
     *
     * @param x the x-coordinate to set
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the wall.
     *
     * @param y the y-coordinate to set
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }
}
