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
import java.util.List;
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
    private ObservableList<Article>articleList = FXCollections.observableArrayList();

    public OrdersStatsController() {
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        try {
            gotList.setAll(httpRequesterOrders.getRequest());
            //historyList.setItems(filteredAndSortedList);
            orderList.setAll(httpRequesterOrders.getRequest());
            articleList.setAll(httpRequesterArticle.getRequest());
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
            setProducts(selected);
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
        productList.setItems(null);
    }

    @FXML
    public ListView productList;

    public void setProducts(Orders ord){
        ObservableList<Article> templist = FXCollections.observableArrayList();
        templist.setAll(stringCutterOrdersToList(ord.getItems()));
        productList.setItems(templist);
    }

    public List stringCutterOrdersToList(String string){
        ObservableList<Article> templist = FXCollections.observableArrayList();
        if(string.equals("")){
            //itemsList.setItems(templist);
            return templist;
        }

        while (!string.equals("")){
            Integer index = string.indexOf(",");
            String sIdNumber = string.substring(0,index);
            Integer idNumber = Integer.parseInt(sIdNumber);

            string = string.substring(index+1);
            index = string.indexOf(";");
            String sQ = string.substring(0,index);
            Integer q = Integer.parseInt(sQ);

            string = string.substring(index+1);

            templist.add(findArticle(idNumber,q));

        }
        return templist;

    }

    public Article findArticle(Integer id, Integer q){
        try {
            ObservableList<Article> helpList = FXCollections.observableList(httpRequesterArticle.getRequest());
            for (Article temp: helpList){
                if(temp.getId()==id){
                    return new Article(id,temp.getName(),q,temp.getPrice(),temp.getMaterials());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("null");
        return null;
    }

}

