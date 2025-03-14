package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnakeGameTest {
    SnakeGame snakeGame;
    GameSettings gameSettings;

    @BeforeEach
    void setUp() {
        gameSettings = new GameSettings(getClass().getResource("/levels/level1.json").getPath());
        snakeGame = new SnakeGame(gameSettings);
    }

    @Test
    void getScore() {
        assertEquals(0, snakeGame.getScore());
    }

    @Test
    void getBestScore() {
        assertEquals(0, snakeGame.getScore());
    }

    @Test
    void getTreats() {
        assertEquals(gameSettings.getNumberFood(), snakeGame.getTreats().size());
    }

    @Test
    void getWalls() {
        assertEquals(gameSettings.getWalls().size(), snakeGame.getWalls().size());
    }

    @Test
    void move() {
        snakeGame.move();
    }

    @Test
    void isGameOver() {
        assertFalse(snakeGame.isGameOver());
    }

    @Test
    void getAwayFromScreen() {
        for (int i = 0; i <= gameSettings.getSnakeCoordinates().get(0); i++) {
            snakeGame.move();
        }
        assertTrue(snakeGame.isGameOver());
    }

    @Test
    void blockExist() {
        for (int i = 0; i <= gameSettings.getSnakeCoordinates().get(0); i++) {
            snakeGame.move();
        }
        assertTrue(snakeGame.blockExist(1, 1));
        assertTrue(snakeGame.blockExist(1, 2));
        assertFalse(snakeGame.blockExist(0, 1));
    }

    @Test
    void startOver() {
        snakeGame.startOver();
        assertFalse(snakeGame.isGameOver());
        assertFalse(snakeGame.isGameWon());
    }
}