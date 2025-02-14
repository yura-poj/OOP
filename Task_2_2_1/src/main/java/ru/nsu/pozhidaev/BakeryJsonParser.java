package ru.nsu.pozhidaev;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Parses bakery configuration from a JSON file.
 */
public class BakeryJsonParser {
    private int stackSize;
    private int storageVolume;
    private int courierSpeed;
    private int[] bakersSpeeds;
    private int[] couriersVolumes;

    /**
     * Loads configuration from the given JSON file.
     *
     * @param pathToJson path to the JSON file
     */
    public BakeryJsonParser(String pathToJson) {
        parseJson(pathToJson);
    }

    /**
     * Reads JSON and initializes fields.
     *
     * @param pathToJson path to the JSON file
     */
    private void parseJson(String pathToJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> jsonMap = objectMapper.readValue(new File(pathToJson), Map.class);

            this.stackSize = (int) jsonMap.get("stackSize");
            this.storageVolume = (int) jsonMap.get("storageVolume");
            this.courierSpeed = (int) jsonMap.get("courierSpeed");

            this.bakersSpeeds = objectMapper.convertValue(jsonMap.get("bakerSpeeds"),
                    new TypeReference<int[]>() {});
            this.couriersVolumes = objectMapper.convertValue(jsonMap.get("courierVolumes"),
                    new TypeReference<int[]>() {});

        } catch (IOException e) {
            throw new RuntimeException("Error loading JSON: " + e.getMessage(), e);
        }
    }

    /**
     * getter.
     *
     * @return dough stack size.
     */
    public int getStackSize() {
        return stackSize;
    }

    /**
     * getter.
     *
     * @return storage capacity.
     */
    public int getStorageVolume() {
        return storageVolume;
    }

    /**
     * getter.
     *
     * @return courier speed.
     */
    public int getCourierSpeed() {
        return courierSpeed;
    }

    /**
     * getter.
     *
     * @return array of baker speeds.
     */
    public int[] getBakersSpeeds() {
        return bakersSpeeds;
    }

    /**
     * getter.
     *
     * @return array of courier volumes.
     */
    public int[] getCouriersVolumes() {
        return couriersVolumes;
    }
}
