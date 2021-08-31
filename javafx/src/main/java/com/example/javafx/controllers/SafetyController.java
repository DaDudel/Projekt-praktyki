package com.example.javafx.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SafetyController {

    public SafetyController() {
    }

    @FXML
    public Button enterButton;

    @FXML
    public  Button exitButton;

    @FXML
    public PasswordField passwordField;

    @FXML
    public void handlePasswordScene()throws IOException {
        Stage stage;
        Parent root;
        stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/safetyPopUp.fxml"));
        loader.setController(new SafetyController());
        root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Zabezpieczenie");
        stage.initModality(Modality.WINDOW_MODAL);
        //stage.initOwner(enterButton.getScene().getWindow());
        stage.show();
    }

    @FXML
    public void exitProgram(){
        Platform.exit();
    }


}
