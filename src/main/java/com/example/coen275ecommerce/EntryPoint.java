package com.example.coen275ecommerce;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EntryPoint extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EntryPoint.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
        stage.setTitle("COEN 275, Group 3, E-Commerce");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}