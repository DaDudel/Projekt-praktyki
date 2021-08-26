package com.example.javafx;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SimpleUiController {
    private JsonGetter jsonGetter = new JsonGetter();
    private HttpRequester httpRequester = new HttpRequester();
    private final HostServices hostServices;

    @FXML
    public Label label;

    @FXML
    public Button button;

    @FXML
    public Button connectButton;

    @FXML
    public ListView testList;

    @FXML
    public TextField filterMaterial;

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
            //jsonGetter.getJson();
            ObservableList<Material> helpList = FXCollections.observableList(jsonGetter.getJson());
            testList.setItems(helpList);
            //jsonGetter.deleteElement(8);
            //httpRequester.deleteRequest(8);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void filterTable(){

    }
}
