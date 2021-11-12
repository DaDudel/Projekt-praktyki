package com.example.javafxclient.controllers;

import com.example.javafxclient.httprequesters.HttpRequesterMaterialHistory;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsController implements Initializable {
    private HttpRequesterMaterialHistory httpRequesterMaterialHistory = new HttpRequesterMaterialHistory();


    public StatsController() {
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){

    }
}
