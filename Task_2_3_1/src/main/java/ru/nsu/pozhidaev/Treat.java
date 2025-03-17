package ru.nsu.pozhidaev;

/**
 * Treat represents a block on the board that the snake can eat.
 * Once eaten, the treat is destroyed.
 */
public class Treat extends Block {
    public Treat(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
    }
}
