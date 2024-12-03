package com.example.idfie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 550);
        Image logo = new Image(HelloApplication.class.getResource("/images/logo.jpg").toExternalForm());
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.setTitle("IDfie");
        stage.setScene(scene);
        stage.show();
        centerStage(stage);
        stage.sceneProperty().addListener((observable, oldScene, newScene) -> centerStage(stage));

    }
    private void centerStage(Stage stage) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = (bounds.getWidth() - stage.getWidth()) / 2;
        double y = (bounds.getHeight() - stage.getHeight()) / 2;
        stage.setX(x);
        stage.setY(y); }

    public static void main(String[] args) {
        launch();
    }
}