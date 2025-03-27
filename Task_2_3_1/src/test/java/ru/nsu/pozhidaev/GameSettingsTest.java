package ru.nsu.pozhidaev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameSettingsTest {
    GameSettings gameSettings;

    @BeforeEach
    void setUp() {
        gameSettings = new GameSettings(getClass().getResource("/levels/level1.json").getPath());
    }

    @Test
    void getSpeed() {
        assertEquals(gameSettings.getSpeed(), 200);
    }

    @Test
    void getWidth() {
        assertEquals(gameSettings.getWidth(), 20);
    }

    @Test
    void getHeight() {
        assertEquals(gameSettings.getHeight(), 30);
    }

    @Test
    void getNumberFood() {
        assertEquals(gameSettings.getNumberFood(), 5);
    }

    @Test
    void getNumberFoodToWin() {
        assertEquals(gameSettings.getNumberFoodToWin(), 30);
    }

    @Test
    void getCubeSize() {
        assertEquals(gameSettings.getCubeSize(), 20);
    }

    @Test
    void getSnakeCoordinates() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(10);
        assertEquals(list, gameSettings.getSnakeCoordinates());
    }

    @Test
    void getWalls() {
        List<List<Integer>> list = new ArrayList<>();
        list.add(List.of(1, 1));
        list.add(List.of(1, 2));
        assertEquals(list, gameSettings.getWalls());
    }

    @Test
    void getBotsNumber() {
        assertEquals(gameSettings.getBotsNumber(), 0);
    }
}