package com.example.javafx;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SimpleUiController implements Initializable {
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

    @FXML
    public Label connectionLabel;

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
            ObservableList<Material> helpList = FXCollections.observableList(jsonGetter.getJson());
            testList.setItems(helpList);
            connectionLabel.setTextFill(Color.GREEN);
            connectionLabel.setText("Połączono");
        } catch (IOException e) {
            connectionLabel.setTextFill(Color.RED);
            connectionLabel.setText("Wystąpił błąd z połączeniem");
            e.printStackTrace();
        } catch (InterruptedException e) {
            connectionLabel.setTextFill(Color.RED);
            connectionLabel.setText("Wystąpił błąd bazy danych");
            e.printStackTrace();
        }
    }
    @FXML
    public void filterTable(){

    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        try {
            ObservableList<Material> helpList = FXCollections.observableList(jsonGetter.getJson());
            testList.setItems(helpList);
            connectionLabel.setTextFill(Color.GREEN);
            connectionLabel.setText("Połączono");
        } catch (IOException e) {
            connectionLabel.setTextFill(Color.RED);
            connectionLabel.setText("Wystąpił błąd z połączeniem");
            e.printStackTrace();
        } catch (InterruptedException e) {
            connectionLabel.setTextFill(Color.RED);
            connectionLabel.setText("Wystąpił błąd bazy danych");
            e.printStackTrace();
        }
    }

    @FXML
    public Label nameLabel;

    @FXML
    public Label quantityLabel;

    @FXML
    public Label priceLabel;

}
