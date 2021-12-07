package com.example.javafxclient.controllers;

import com.example.javafxclient.MaterialHistory;
import com.example.javafxclient.httprequesters.HttpRequesterMaterialHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StatsController implements Initializable {
    private HttpRequesterMaterialHistory httpRequesterMaterialHistory = new HttpRequesterMaterialHistory();
    private ObservableList<MaterialHistory>gotList = FXCollections.observableArrayList();
    private LocalDate firstDate;
    private LocalDate secondDate;
    private ObservableList<MaterialHistory>filteredList = FXCollections.observableArrayList();

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

    @FXML
    public DatePicker dateFrom;

    @FXML
    public DatePicker dateTo;

    @FXML
    public void filterByDate(){
        firstDate = dateFrom.getValue();
        secondDate = dateTo.getValue();
        filteredList.clear();

        for(MaterialHistory temp : gotList){
            if(firstDate!=null){
                if(secondDate!=null){
                    if(temp.getTimeStamp().isEqual(firstDate)||temp.getTimeStamp().isEqual(secondDate)||
                            (temp.getTimeStamp().isAfter(firstDate)&&temp.getTimeStamp().isBefore(secondDate))){
                        filteredList.add(temp);
                    }
                }else {
                    if(temp.getTimeStamp().isAfter(firstDate)||temp.getTimeStamp().isEqual(firstDate)){
                        filteredList.add(temp);
                    }
                }
            }else{
                if (secondDate!=null){
                    if(temp.getTimeStamp().isBefore(secondDate)||temp.getTimeStamp().isEqual(secondDate)){
                        filteredList.add(temp);
                    }
                } else {
                    filteredList.setAll(gotList);
                }
            }
        }
        historyList.setItems(filteredList);
    }
}
