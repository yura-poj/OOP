package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnakeTest {
    Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake(5, 5);
    }

    @Test
    void setDirection() {
        snake.setDirection(Action.UP);
        snake.move();
        assertEquals(5, snake.getHead().getCoordinateX());
        assertEquals(4, snake.getHead().getCoordinateY());
        snake.setDirection(Action.RIGHT);
        snake.move();
        assertEquals(6, snake.getHead().getCoordinateX());
        assertEquals(4, snake.getHead().getCoordinateY());
        snake.setDirection(Action.DOWN);
        snake.move();
        assertEquals(6, snake.getHead().getCoordinateX());
        assertEquals(5, snake.getHead().getCoordinateY());
        snake.setDirection(Action.LEFT);
        snake.move();
        assertEquals(5, snake.getHead().getCoordinateX());
        assertEquals(5, snake.getHead().getCoordinateY());
    }

    @Test
    void move() {
        assertEquals(5, snake.getHead().getCoordinateX());
        assertEquals(5, snake.getHead().getCoordinateY());
        snake.move();
        assertEquals(4, snake.getHead().getCoordinateX());
        assertEquals(5, snake.getHead().getCoordinateY());
    }

    @Test
    void getBody() {
        assertEquals(3, snake.getBody().size());
    }

    @Test
    void getHead() {
        assertEquals(5, snake.getHead().getCoordinateX());
        assertEquals(5, snake.getHead().getCoordinateY());
    }

    @Test
    void lunch() {
        List<SnakePart> body = snake.getBody();
        SnakePart lastPart = body.get(body.size() - 1);
        int x = lastPart.getCoordinateX();
        int y = lastPart.getCoordinateY();
        snake.lunch();
        snake.move();
        body = snake.getBody();
        SnakePart newLastPart = body.get(body.size() - 1);
        assertEquals(x, newLastPart.getCoordinateX());
        assertEquals(y, newLastPart.getCoordinateY());
    }
}