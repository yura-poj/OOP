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
    private void handleStart(ActionEvent event) {
        String levelPath = "./levels/level1.json";
        GameSettings gameSettings = new GameSettings(getClass().getResource(levelPath));
        try{
            loadLevel(event);
        } catch (IOException e) {
            System.out.println("No such level, choose another one");
        }
    }

    private void loadLevel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent newView = loader.load();

        // Получаем контроллер, если нужно передать данные
        GameController controller = loader.getController();
        controller.initData(stage, new SnakeGame()); // Метод для инициализации данных

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene newScene = new Scene(newView, 500, 500);
        currentStage.setScene(newScene);
        currentStage.show();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}