package ru.nsu.pozhidaev;

/**
 * Block represents a cube on the board.
 */
public interface Block {

    /**
     * Returns the x-coordinate of the block.
     *
     * @return the x-coordinate
     */
    int getX();

    /**
     * Returns the y-coordinate of the block.
     *
     * @return the y-coordinate
     */
    int getY();

    /**
     * Sets the x-coordinate of the block.
     *
     * @param x the x-coordinate to set
     */
    void setX(int x);

    /**
     * Sets the y-coordinate of the block.
     *
     * @param y the y-coordinate to set
     */
    void setY(int y);

}
