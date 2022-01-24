package com.example.javafxclient.controllers;

import com.example.javafxclient.Material;
import com.example.javafxclient.MaterialHistory;
import com.example.javafxclient.httprequesters.HttpRequesterMaterialHistory;
import com.example.javafxclient.httprequesters.JsonGetter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class StatsController implements Initializable {
    private HttpRequesterMaterialHistory httpRequesterMaterialHistory = new HttpRequesterMaterialHistory();
    private ObservableList<MaterialHistory>gotList = FXCollections.observableArrayList();
    private LocalDate firstDate;
    private LocalDate secondDate;
    private ObservableList<MaterialHistory>filteredList = FXCollections.observableArrayList();
    private ObservableList<Material>filteredAndSortedList = FXCollections.observableArrayList();
    private ObservableList<Material>materialList = FXCollections.observableArrayList();
    private JsonGetter jsonGetter = new JsonGetter();

    public StatsController() {
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        try {
            gotList.setAll(httpRequesterMaterialHistory.getRequest());
            historyList.setItems(gotList);
            materialList.setAll(jsonGetter.getJson());
            //System.out.println(materialList);
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
        filterAndSort();
        historyList.setItems(filteredAndSortedList);
    }
    public Material returnMaterial(Integer id){
        for(Material tmp : materialList){
            if(Objects.equals(id, tmp.getId())){
                return tmp;
            }
        }
        return null;
    }

    public void filterAndSort(){
        Boolean test;
        Material copyMat;
        for(MaterialHistory tmp : filteredList){
            test = true;
            for(Material tmpMaterial: filteredAndSortedList){
                if(Objects.equals(tmp.getMaterialId(), tmpMaterial.getId())){
                    tmpMaterial.setQuantity(tmpMaterial.getQuantity()+tmp.getChange());
                    test = false;
                    break;
                }
            }
            if (test){
                copyMat = returnMaterial(tmp.getMaterialId());
                filteredAndSortedList.add(new Material(copyMat.getId(),copyMat.getName(),tmp.getChange(), copyMat.getPrice()));
            }
        }
    }
}
