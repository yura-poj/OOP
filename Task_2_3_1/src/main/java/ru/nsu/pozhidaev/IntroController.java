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

public class IntroController {

    @FXML
    private Label label;
    private Stage stage;

    public void initData(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleLevel1(ActionEvent event) {
        String levelPath = "/levels/level1.json";
        try{
            GameSettings gameSettings = new GameSettings(getClass().getResource(levelPath).getPath());
            loadLevel(event, gameSettings);
        } catch (IOException e) {
            System.out.println("No such level, choose another one");
        }
    }

    @FXML
    private void handleLevel2(ActionEvent event) {
        String levelPath = "/levels/level2.json";
        try{
            GameSettings gameSettings = new GameSettings(getClass().getResource(levelPath).getPath());
            loadLevel(event, gameSettings);
        } catch (IOException e) {
            System.out.println("No such level, choose another one");
        }
    }

    private void loadLevel(ActionEvent event, GameSettings settings) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent newView = loader.load();

        GameController controller = loader.getController();
        controller.initData(stage,settings);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene newScene = new Scene(newView, settings.getWidth() * settings.getCubeSize(),
                settings.getHeight() * settings.getCubeSize() + 4 * settings.getCubeSize());
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}