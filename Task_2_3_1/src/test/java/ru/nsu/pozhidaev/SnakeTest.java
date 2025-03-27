package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnakeTest {
    Snake snake;

    @BeforeEach
    void setUp() {
        GameSettings gameSettings = new GameSettings(getClass().getResource(
                "/levels/level1.json").getPath());
        snake = new Snake(new SnakeGame(gameSettings));
        snake.startOver(5, 5);
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
        SnakePart copiedPart = new SnakePart(0, 0);
        copiedPart.setCoordinateX(lastPart.getCoordinateX());
        copiedPart.setCoordinateY(lastPart.getCoordinateY());
        snake.lunch();
        snake.move();
        body = snake.getBody();
        SnakePart newLastPart = body.get(body.size() - 1);
        assertEquals(copiedPart.getCoordinateX(), newLastPart.getCoordinateX());
        assertEquals(copiedPart.getCoordinateY(), newLastPart.getCoordinateY());
    }
}