package com.example.javafxclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ui.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/loginPopUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 360);
        stage.setTitle("Aplikacja");
        stage.setScene(scene);
        stage.show();

//        Stage stage = stageReadyEvent.getStage();
//        URL url = this.fxml.getURL();
//        System.out.println(url);
//        FXMLLoader fxmlLoader = new FXMLLoader(url);
//        fxmlLoader.setControllerFactory(ac::getBean);
//        Parent root = fxmlLoader.load();
//        Scene scene = new Scene(root,1280,720);
//        stage.setScene(scene);
//        stage.setTitle(this.applicationTitle);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}