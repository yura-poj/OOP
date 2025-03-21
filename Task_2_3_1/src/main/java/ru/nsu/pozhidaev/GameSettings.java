package ru.nsu.pozhidaev;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * GameSettings is responsible for loading and providing access to game configuration
 * from a JSON file.
 */
public class GameSettings {
    private final ObjectMapper objectMapper;
    private Map<String, Object> jsonMap;

    /**
     * Constructs a GameSettings object and loads settings from the specified JSON file.
     *
     * @param input the path to the JSON file containing game settings
     */
    public GameSettings(String input) {
        this.objectMapper = new ObjectMapper();
        loadSettings(input);
    }

    /**
     * Loads game settings from the specified JSON file.
     *
     * @param input the path to the JSON file
     */
    private void loadSettings(String input) {
        try {
            jsonMap = objectMapper.readValue(new File(input),
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (IOException e) {
            throw new RuntimeException("Error loading JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Returns the speed setting from the JSON configuration.
     *
     * @return the speed of the game
     */
    public int getSpeed() {
        return (int) jsonMap.get("speed");
    }

    /**
     * Returns the width setting from the JSON configuration.
     *
     * @return the width of the game area
     */
    public int getWidth() {
        return (int) jsonMap.get("width");
    }

    /**
     * Returns the height setting from the JSON configuration.
     *
     * @return the height of the game area
     */
    public int getHeight() {
        return (int) jsonMap.get("hight");
    }

    /**
     * Returns the number of food items from the JSON configuration.
     *
     * @return the number of food items
     */
    public int getNumberFood() {
        return (int) jsonMap.get("numberFood");
    }

    /**
     * Returns the number of food items required to win from the JSON configuration.
     *
     * @return the number of food items to win
     */
    public int getNumberFoodToWin() {
        return (int) jsonMap.get("numberFoodToWin");
    }

    /**
     * Returns the size of each cube in the game grid.
     *
     * @return the size of each cube
     */
    public int getCubeSize() {
        return (int) jsonMap.get("cubeSize");
    }

    /**
     * Returns the initial coordinates of the snake from the JSON configuration.
     *
     * @return a list containing the x and y coordinates of the snake
     */
    public List<Integer> getSnakeCoordinates() {
        Map<String, Object> snakeCoordinates =
                (Map<String, Object>) jsonMap.get("snakeCoordinates");
        return Arrays.asList((int) snakeCoordinates.get("x"), (int) snakeCoordinates.get("y"));
    }

    /**
     * Returns the list of wall coordinates from the JSON configuration.
     *
     * @return a list of wall coordinates
     */
    public List<List<Integer>> getWalls() {
        return objectMapper.convertValue(jsonMap.get("walls"),
                new TypeReference<List<List<Integer>>>() {
                });
    }

    public int getBotsNumber() {
        return (int) jsonMap.get("botsNumber");
    }

    public int getBotTreatRadiusView() {
        return (int) jsonMap.get("botTreatRadiusView");
    }
}
