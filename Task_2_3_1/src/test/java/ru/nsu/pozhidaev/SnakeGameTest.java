package ru.nsu.pozhidaev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SnakeGameTest {
    SnakeGame snakeGame;

    @BeforeEach
    void setUp() {
        snakeGame = new SnakeGame();
      }

    @Test
    void getScore() {
      }

    @Test
    void getBestScore() {
      }

    @Test
    void getSnakeBody() {
      }

    @Test
    void getTreats() {
      }

    @Test
    void getWalls() {
      }

    @Test
    void move() {
      }

    @Test
    void isGameOver() {
      }

    @Test
    void receiveAction() {
      }

      @Test
    void blockExist() {
        assertTrue(snakeGame.blockExist(9,9));
        assertTrue(snakeGame.blockExist(9,10));
        assertTrue(snakeGame.blockExist(10,10));
        assertFalse(snakeGame.blockExist(0,1));
      }
}