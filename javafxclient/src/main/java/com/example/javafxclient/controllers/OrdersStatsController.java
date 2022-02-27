package com.example.javafxclient.controllers;

import com.example.javafxclient.*;
import com.example.javafxclient.httprequesters.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class OrdersStatsController implements Initializable {
    private HttpRequesterOrders httpRequesterOrders = new HttpRequesterOrders();
    private ObservableList<Orders>gotList = FXCollections.observableArrayList();
    private Functions functions = new Functions();
    private LocalDate firstDate;
    private LocalDate secondDate;
    private ObservableList<Orders>orderList = FXCollections.observableArrayList();
    private JsonGetter jsonGetter = new JsonGetter();
    private HttpRequesterArticle httpRequesterArticle = new HttpRequesterArticle();
    private ObservableList<Orders>filteredList = FXCollections.observableArrayList();

    public OrdersStatsController() {
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        try {
            gotList.setAll(httpRequesterOrders.getRequest());
            //historyList.setItems(filteredAndSortedList);
            orderList.setAll(httpRequesterOrders.getRequest());
            //materialList.setAll(jsonGetter.getJson());
            filterByDate();
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

        for(Orders temp : gotList){
            if(temp.getDone()==true){
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
        }
        historyList.setItems(filteredList);
        clearSelected();
    }

    @FXML
    public TextField searchTF;

    @FXML
    public void useSearchBar(){
        ObservableList<Orders> data = historyList.getItems();
        FilteredList<Orders> filteredData = new FilteredList<>(data, s -> true);

        searchTF.textProperty().addListener(obs->{
            String filter = searchTF.getText().toLowerCase();
            if(filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            }
            else {
                filteredData.setPredicate(s -> s.toString().toLowerCase().contains(filter));
            }
        });
        historyList.setItems(filteredData);
    }

    @FXML
    public void displaySelected(){
        Orders selected = (Orders) historyList.getSelectionModel().getSelectedItem();
        if(selected!=null){
            selectedName.setText(selected.getClient());
            selectedId.setText(selected.getTransId().toString());
            selectedDate.setText(selected.getTimeStamp().toString());
            selectedBrutto.setText(functions.roundDouble(selected.getBruttoPrice()).toString() + " zł");
            selectedNetto.setText(functions.roundDouble(selected.getNettoPrice()).toString() + " zł");
            selectedDiscount.setText(functions.roundDouble(selected.getDiscount()).toString() + " %");
        }
    }

    @FXML
    public Text selectedName;

    @FXML
    public Text selectedId;

    @FXML
    public Text selectedBrutto;

    @FXML
    public Text selectedNetto;

    @FXML
    public Text selectedDiscount;

    @FXML
    public Text selectedDate;

    public void clearSelected(){
        selectedName.setText("");
        selectedBrutto.setText("");
        selectedNetto.setText("");
        selectedDate.setText("");
        selectedDiscount.setText("");
        selectedId.setText("");
    }
}

