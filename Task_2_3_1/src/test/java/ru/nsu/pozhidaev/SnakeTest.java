package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class SnakeTest {
    Snake snake;
    @BeforeEach
    void setUp() {
        snake = new Snake(5,5);
      }

    @Test
    void setDirection() {
        snake.setDirection(Action.UP);
        snake.move();
        assertEquals(5, snake.getHead().getX());
        assertEquals(4, snake.getHead().getY());
        snake.setDirection(Action.RIGHT);
        snake.move();
        assertEquals(6, snake.getHead().getX());
        assertEquals(4, snake.getHead().getY());
        snake.setDirection(Action.DOWN);
        snake.move();
        assertEquals(6, snake.getHead().getX());
        assertEquals(5, snake.getHead().getY());
        snake.setDirection(Action.LEFT);
        snake.move();
        assertEquals(5, snake.getHead().getX());
        assertEquals(5, snake.getHead().getY());
      }

    @Test
    void move() {
        assertEquals(5, snake.getHead().getX());
        assertEquals(5, snake.getHead().getY());
        snake.move();
        assertEquals(4, snake.getHead().getX());
        assertEquals(5, snake.getHead().getY());
      }

    @Test
    void getBody() {
        assertEquals(3, snake.getBody().size());
      }

    @Test
    void getHead() {
        assertEquals(5, snake.getHead().getX());
        assertEquals(5, snake.getHead().getY());
      }

    @Test
    void lunch() {
        List<SnakePart> body = snake.getBody();
        SnakePart lastPart = body.get(body.size() - 1);
        int x = lastPart.getX();
        int y = lastPart.getY();
        snake.lunch();
        snake.move();
        body = snake.getBody();
        SnakePart newLastPart = body.get(body.size() - 1);
        assertEquals(x, newLastPart.getX());
        assertEquals(y, newLastPart.getY());
    }
}