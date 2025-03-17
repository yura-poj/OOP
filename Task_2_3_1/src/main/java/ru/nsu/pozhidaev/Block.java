package ru.nsu.pozhidaev;

import lombok.Getter;
import lombok.Setter;

/**
 * Block represents a cube on the board.
 */
public class Block {
    @Getter
    @Setter
    int coordinateX;

    @Getter
    @Setter
    int coordinateY;

    public Block(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
}
