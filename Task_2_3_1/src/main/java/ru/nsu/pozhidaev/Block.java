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
    int getCoordinateX();

    /**
     * Returns the y-coordinate of the block.
     *
     * @return the y-coordinate
     */
    int getCoordinateY();

    /**
     * Sets the x-coordinate of the block.
     *
     * @param coordinateX the x-coordinate to set
     */
    void setCoordinateX(int coordinateX);

    /**
     * Sets the y-coordinate of the block.
     *
     * @param coordinateY the y-coordinate to set
     */
    void setCoordinateY(int coordinateY);

}
