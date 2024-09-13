package com.example.parisroutefinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage=stage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainview.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 781);
            stage.setScene(scene);
            stage.setTitle("Paris Route Finder");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}