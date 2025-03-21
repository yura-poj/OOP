package ru.nsu.pozhidaev;

import lombok.Getter;
import lombok.Setter;

/**
 * Block represents a basic unit in the game grid.
 * It serves as the base class for all game objects that occupy space on the board,
 * such as snake parts, walls, and treats.
 */
public class Block {
    /**
     * The x-coordinate of the block on the game grid.
     */
    @Getter
    @Setter
    int coordinateX;

    /**
     * The y-coordinate of the block on the game grid.
     */
    @Getter
    @Setter
    int coordinateY;

    /**
     * Constructs a new Block with specified coordinates.
     *
     * @param coordinateX the x-coordinate of the block
     * @param coordinateY the y-coordinate of the block
     */
    public Block(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
}
