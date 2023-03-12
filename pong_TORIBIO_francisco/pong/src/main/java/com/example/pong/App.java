package com.example.pong;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Pong");
        var root = new VistaJuego();
        stage.setScene(new Scene(root, 600, 500));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}