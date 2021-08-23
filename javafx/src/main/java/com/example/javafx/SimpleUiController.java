package com.example.javafx;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SimpleUiController {
    private JsonGetter jsonGetter = new JsonGetter();
    private final HostServices hostServices;

    @FXML
    public Label label;

    @FXML
    public Button button;

    @FXML
    public Button connectButton;

    @FXML
    public ListView testList;

    SimpleUiController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    public void initialize(){
        this.button.setOnAction(actionEvent ->
                this.label.setText(this.hostServices.getDocumentBase()));
    }

    @FXML
    public void connectJson(){
        try {
            jsonGetter.getJson();
            ObservableList<Greeting> helpList = FXCollections.observableList(jsonGetter.getJson());
            testList.setItems(helpList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
