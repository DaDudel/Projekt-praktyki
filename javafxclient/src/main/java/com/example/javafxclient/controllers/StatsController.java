package com.example.javafxclient.controllers;

import com.example.javafxclient.Article;
import com.example.javafxclient.Functions;
import com.example.javafxclient.Material;
import com.example.javafxclient.MaterialHistory;
import com.example.javafxclient.httprequesters.HttpRequesterMaterialHistory;
import com.example.javafxclient.httprequesters.JsonGetter;
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

public class StatsController implements Initializable {
    private HttpRequesterMaterialHistory httpRequesterMaterialHistory = new HttpRequesterMaterialHistory();
    private ObservableList<MaterialHistory>gotList = FXCollections.observableArrayList();
    private Functions functions = new Functions();
    private LocalDate firstDate;
    private LocalDate secondDate;
    private ObservableList<MaterialHistory>filteredList = FXCollections.observableArrayList();
    private ObservableList<Material>filteredAndSortedList = FXCollections.observableArrayList();
    private ObservableList<Material>materialList = FXCollections.observableArrayList();
    private JsonGetter jsonGetter = new JsonGetter();
    private ObservableList<MaterialHistory>filteredByDateList = FXCollections.observableArrayList();
    private ObservableList<MaterialHistory>minuses = FXCollections.observableArrayList();
    private ObservableList<MaterialHistory>pluses = FXCollections.observableArrayList();

    public StatsController() {
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        try {
            gotList.setAll(httpRequesterMaterialHistory.getRequest());
            //historyList.setItems(filteredAndSortedList);
            materialList.setAll(jsonGetter.getJson());
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
        filteredByDateList.clear();

        for(MaterialHistory temp : gotList){
            if(firstDate!=null){
                if(secondDate!=null){
                    if(temp.getTimeStamp().isEqual(firstDate)||temp.getTimeStamp().isEqual(secondDate)||
                            (temp.getTimeStamp().isAfter(firstDate)&&temp.getTimeStamp().isBefore(secondDate))){
                        filteredList.add(temp);
                        filteredByDateList.add(temp);
                    }
                }else {
                    if(temp.getTimeStamp().isAfter(firstDate)||temp.getTimeStamp().isEqual(firstDate)){
                        filteredList.add(temp);
                        filteredByDateList.add(temp);
                    }
                }
            }else{
                if (secondDate!=null){
                    if(temp.getTimeStamp().isBefore(secondDate)||temp.getTimeStamp().isEqual(secondDate)){
                        filteredList.add(temp);
                        filteredByDateList.add(temp);
                    }
                } else {
                    filteredList.setAll(gotList);
                    filteredByDateList.setAll(gotList);
                }
            }
        }
        filterAndSort();
        fillPlusAndMinus();
        fillSpentMoney();
        clearSelected();
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
        searchTF.setText("");
        Boolean test;
        Material copyMat;
        filteredAndSortedList.clear();
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

    public void fillPlusAndMinus(){
        pluses.clear();
        minuses.clear();
        for(MaterialHistory temp : filteredByDateList){
            if(temp.getChange()>0){
                pluses.add(temp);
            }
            else {
                if(temp.getChange()<0){
                    minuses.add(temp);
                }
            }
        }
//        for(MaterialHistory tmp: minuses){
//            System.out.println(tmp.getMaterialId());
//            System.out.println(tmp.getChange());
//        }
    }
    @FXML
    public Text spentMoneyText;

    @FXML
    public TextField searchTF;

    public Double returnPrice(Integer id){
        Double price = 0.0;
        for(Material temp: materialList){
            if (temp.getId()==id){
                return temp.getPrice();
            }
        }
        return 0.0;
    }

    public void fillSpentMoney(){
        Double sum = 0.0;
        for(MaterialHistory tmp : pluses){
            sum = sum + (tmp.getChange()*returnPrice(tmp.getMaterialId()));
        }
        sum = functions.roundDouble(sum);
        spentMoneyText.setText(sum.toString()+" zł");
    }

    @FXML
    public void useSearchBar(){
        ObservableList<Material> data = FXCollections.observableArrayList();
        data.setAll(historyList.getItems());
        FilteredList<Material> filteredData = new FilteredList<>(data, s -> true);

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

    public Double returnPluses(Integer id){
        Double sum = 0.0;
        for(MaterialHistory tmp:pluses){
            if (tmp.getMaterialId()==id){
                sum = sum + functions.roundDouble(tmp.getChange());
            }
        }
        return sum;
    }

    public Double returnMinuses(Integer id){
        Double dif = 0.0;
        for(MaterialHistory tmp:minuses){
            if (tmp.getMaterialId()==id){
                dif = dif - functions.roundDouble(tmp.getChange());
            }
        }
        return dif;
    }

    public Double returnQuantity(Integer id){
        for (Material temp: materialList){
            if(temp.getId()==id){
                return temp.getQuantity();
            }
        }
        return 0.0;
    }

    public Double returnSpentMoney(Integer id){
        Double sum = 0.0;

        return sum;
    }

    @FXML
    public void displaySelected(){
        Material selected = (Material) historyList.getSelectionModel().getSelectedItem();
        Double tmp = Math.abs(returnMinuses(selected.getId()));
        selectedMinus.setText(tmp.toString());
        selectedPlus.setText(returnPluses(selected.getId()).toString());
        selectedQuantity.setText(returnQuantity(selected.getId()).toString());
        selectedName.setText(selected.getName());
        tmp = functions.roundDouble(returnPrice(selected.getId())*returnPluses(selected.getId()));
        selectedPrice.setText(tmp.toString()+" zł");
    }

    @FXML
    public Text selectedName;

    @FXML
    public Text selectedMinus;

    @FXML
    public Text selectedPlus;

    @FXML
    public Text selectedQuantity;

    @FXML
    public Text selectedPrice;

    public void clearSelected(){
        selectedName.setText("");
        selectedMinus.setText("");
        selectedPlus.setText("");
        selectedQuantity.setText("");
        selectedPrice.setText("");
    }
}
