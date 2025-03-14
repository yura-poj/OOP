package ru.nsu.pozhidaev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Main class to launch the JavaFX application.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application by setting up the primary stage.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/intro.fxml"));
        primaryStage.setTitle("JavaFX FXML Example");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    /**
     * The main method to launch the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}