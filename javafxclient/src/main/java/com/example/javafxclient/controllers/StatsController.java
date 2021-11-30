package com.example.javafxclient.controllers;

import com.example.javafxclient.MaterialHistory;
import com.example.javafxclient.httprequesters.HttpRequesterMaterialHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable {
    private HttpRequesterMaterialHistory httpRequesterMaterialHistory = new HttpRequesterMaterialHistory();
    private ObservableList<MaterialHistory>gotList = FXCollections.observableArrayList();


    public StatsController() {
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        try {
            gotList.setAll(httpRequesterMaterialHistory.getRequest());
            historyList.setItems(gotList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public ListView historyList;
}
