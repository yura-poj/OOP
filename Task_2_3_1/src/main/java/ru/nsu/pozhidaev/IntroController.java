package ru.nsu.pozhidaev;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * IntroController manages the initial screen and handles user interactions
 * to start the game or exit the application.
 */
public class IntroController {

    @FXML
    private Label label;
    private Stage stage;

    /**
     * Initializes the controller with the primary stage.
     *
     * @param stage the primary stage of the application
     */
    public void initData(Stage stage) {
        this.stage = stage;
    }

    /**
     * Handles the action to load level 1 of the game.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    private void handleLevel1(ActionEvent event) {
        String levelPath = "/levels/level1.json";
        try {
            GameSettings gameSettings = new GameSettings(getClass().getResource(levelPath).getPath());
            loadLevel(event, gameSettings);
        } catch (IOException e) {
            System.out.println("No such level, choose another one");
        }
    }

    /**
     * Handles the action to load level 2 of the game.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    private void handleLevel2(ActionEvent event) {
        String levelPath = "/levels/level2.json";
        try {
            GameSettings gameSettings = new GameSettings(getClass().getResource(levelPath).getPath());
            loadLevel(event, gameSettings);
        } catch (IOException e) {
            System.out.println("No such level, choose another one");
        }
    }

    /**
     * Loads the specified game level and initializes the game controller.
     *
     * @param event    the action event
     * @param settings the game settings loaded from the JSON file
     *
     * @throws IOException if the game view cannot be loaded
     */
    private void loadLevel(ActionEvent event, GameSettings settings) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent newView = loader.load();

        GameController controller = loader.getController();
        controller.initData(stage, settings);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene newScene = new Scene(newView, settings.getWidth() * settings.getCubeSize(),
                settings.getHeight() * settings.getCubeSize() + 4 * settings.getCubeSize());
        currentStage.setScene(newScene);
        currentStage.show();
    }

    /**
     * Handles the exit action to close the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}