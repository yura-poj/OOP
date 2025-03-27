package ru.nsu.pozhidaev;

/**
 * Wall represents an obstacle block on the board.
 * If the snake bumps into a wall, the game is over.
 */
public class Wall extends Block {
    public Wall(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
    }
}
