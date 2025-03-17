package ru.nsu.pozhidaev;

/**
 * SnakePart represents a part of the snake's body.
 * It acts as a block that can be an obstacle for the snake.
 */
public class SnakePart extends Block {
    public SnakePart(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
    }
}
