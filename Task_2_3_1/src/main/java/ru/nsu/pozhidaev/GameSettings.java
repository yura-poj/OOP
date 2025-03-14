package ru.nsu.pozhidaev;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Setting of the game with parser.
 */
public class GameSettings {
    private ObjectMapper objectMapper;
    private Map<String, Object> jsonMap;

    public GameSettings(String  input) {
        this.objectMapper = new ObjectMapper();
        loadSettings(input);
    }

    public int getSpeed() {
        return (int) jsonMap.get("speed");
    }

    public int getWidth() {
        return (int) jsonMap.get("width");
    }

    public int getHeight() {
        return (int) jsonMap.get("hight");
    }

    public int getNumberFood() {
        return (int) jsonMap.get("numberFood");
    }

    public int getNumberFoodToWin() {
        return (int) jsonMap.get("numberFoodToWin");
    }

    public List<Integer> getSnakeCoordinates() {
        Map<String, Object> snakeCoordinates = (Map<String, Object>) jsonMap.get("snakeCoordinates");
        return Arrays.asList((int) snakeCoordinates.get("x"), (int) snakeCoordinates.get("y"));
    }

    public List<List<Integer>> getWalls() {
        return objectMapper.convertValue(jsonMap.get("walls"),
                new TypeReference<List<List<Integer>>>() {});
    }

    private void loadSettings(String input) {
        try {
            jsonMap = objectMapper.readValue(new File(input),
                    new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error loading JSON: " + e.getMessage(), e);
        }
    }
}
