package com.example.javafxclient.controllers;

import com.example.javafxclient.*;
import com.example.javafxclient.httprequesters.HttpRequesterArticle;
import com.example.javafxclient.httprequesters.HttpRequesterArticleHistory;
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

public class ArticleStatsController implements Initializable {
    private HttpRequesterArticleHistory httpRequesterArticleHistory = new HttpRequesterArticleHistory();
    private ObservableList<ArticleHistory>gotList = FXCollections.observableArrayList();
    private Functions functions = new Functions();
    private LocalDate firstDate;
    private LocalDate secondDate;
    private ObservableList<ArticleHistory>filteredList = FXCollections.observableArrayList();
    private ObservableList<Article>filteredAndSortedList = FXCollections.observableArrayList();
    private ObservableList<Article>articleList = FXCollections.observableArrayList();
    private JsonGetter jsonGetter = new JsonGetter();
    private ObservableList<Material>materialList = FXCollections.observableArrayList();
    private HttpRequesterArticle httpRequesterArticle = new HttpRequesterArticle();
    private ObservableList<ArticleHistory>filteredByDateList = FXCollections.observableArrayList();
    private ObservableList<ArticleHistory>minuses = FXCollections.observableArrayList();
    private ObservableList<ArticleHistory>pluses = FXCollections.observableArrayList();

    public ArticleStatsController() {
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        try {
            gotList.setAll(httpRequesterArticleHistory.getRequest());
            //historyList.setItems(filteredAndSortedList);
            articleList.setAll(httpRequesterArticle.getRequest());
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

        for(ArticleHistory temp : gotList){
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
//        fillSpentMoney();
        clearSelected();
        historyList.setItems(filteredAndSortedList);
    }
    public Article returnArticle(Integer id){
        for(Article tmp : articleList){
            if(Objects.equals(id, tmp.getId())){
                return tmp;
            }
        }
        return null;
    }

    public void filterAndSort(){
        searchTF.setText("");
        Boolean test;
        Article copyArt;
        filteredAndSortedList.clear();
        for(ArticleHistory tmp : filteredList){
            test = true;
            for(Article tmpArticle: filteredAndSortedList){
                if(Objects.equals(tmp.getArticleId(), tmpArticle.getId())){
                    tmpArticle.setQuantity(tmpArticle.getQuantity()+tmp.getChange());
                    test = false;
                    break;
                }
            }
            if (test){
                copyArt = returnArticle(tmp.getArticleId());
                filteredAndSortedList.add(new Article(copyArt.getId(),copyArt.getName(),tmp.getChange(),
                        copyArt.getPrice(), copyArt.getMaterials(), copyArt.getWorkPrice()));
            }
        }

    }

    public void fillPlusAndMinus(){
        pluses.clear();
        minuses.clear();
        for(ArticleHistory temp : filteredByDateList){
            if(temp.getChange()>0){
                pluses.add(temp);
            }
            else {
                if(temp.getChange()<0){
                    minuses.add(temp);
                }
            }
        }
    }
//    @FXML
//    public Text spentMoneyText;

    @FXML
    public TextField searchTF;

    public Double returnPrice(Integer id){
        Double price = 0.0;
        for(Article temp: articleList){
            if (temp.getId()==id){
                return temp.getPrice();
            }
        }
        return 0.0;
    }

//    public void fillSpentMoney(){
//        Double sum = 0.0;
//        for(ArticleHistory tmp : pluses){
//            sum = sum + (tmp.getChange()*returnPrice(tmp.getArticleId()));
//        }
//        sum = functions.roundDouble(sum);
//        spentMoneyText.setText(sum.toString()+" zł");
//    }

    @FXML
    public void useSearchBar(){
        ObservableList<Article> data = FXCollections.observableArrayList();
        data.setAll(historyList.getItems());
        FilteredList<Article> filteredData = new FilteredList<>(data, s -> true);

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

    public Integer returnPluses(Integer id){
        Integer sum = 0;
        for(ArticleHistory tmp:pluses){
            if (tmp.getArticleId()==id){
                sum = sum + tmp.getChange();
            }
        }
        return sum;
    }

    public Integer returnMinuses(Integer id){
        Integer dif = 0;
        for(ArticleHistory tmp:minuses){
            if (tmp.getArticleId()==id){
                dif = dif - tmp.getChange();
            }
        }
        return dif;
    }

    public Integer returnQuantity(Integer id){
        for (Article temp: articleList){
            if(temp.getId()==id){
                return temp.getQuantity();
            }
        }
        return 0;
    }

    public Double returnWorkPrice(Integer id){
        Double price = 0.0;
        for(Article temp: articleList){
            if (temp.getId()==id){
                return temp.getWorkPrice();
            }
        }
        return 0.0;
    }

    public Double returnMaterialPrice(Integer id){
        Double price = 0.0;
        for(Article temp: articleList){
            if (temp.getId()==id){
                return stringToDouble(temp.getMaterials());
            }
        }
        return price;
    }

    public Double stringToDouble(String str){
        Double price = 0.0;
        while (!str.equals("")){
            Integer index = str.indexOf(",");
            String sIdNumber = str.substring(0,index);
            Integer idNumber = Integer.parseInt(sIdNumber);

            str = str.substring(index+1);
            index = str.indexOf(";");
            String sQ = str.substring(0,index);
            Double q = Double.parseDouble(sQ);

            str = str.substring(index+1);

            price = price + (returnSinglePrice(idNumber)*q);
        }
        return price;
    }

    public Double returnSinglePrice(Integer id){
        Double price = 0.0;
        for(Material tmp : materialList){
            if(tmp.getId()==id){
                return tmp.getPrice();
            }
        }
        return price;
    }



    @FXML
    public void displaySelected(){
        Article selected = (Article) historyList.getSelectionModel().getSelectedItem();
        Integer tmp = returnMinuses(selected.getId());
        Double tmpPrice;
        selectedMinus.setText(tmp.toString());
        selectedPlus.setText(returnPluses(selected.getId()).toString());
        selectedQuantity.setText(returnQuantity(selected.getId()).toString());
        selectedName.setText(selected.getName());
        tmpPrice = functions.roundDouble(returnPrice(selected.getId()));
        selectedPrice.setText(tmpPrice.toString()+" zł");
        tmpPrice = functions.roundDouble(returnWorkPrice(selected.getId())*returnPluses(selected.getId()));
        selectedWorkPrice.setText(tmpPrice + " zł");
        tmpPrice = functions.roundDouble(returnMaterialPrice(selected.getId())*returnPluses(selected.getId()));
        selectedMaterialPrice.setText(tmpPrice + " zł");
        tmpPrice = functions.roundDouble(returnPrice(selected.getId())*returnMinuses(selected.getId()));
        selectedSumPrice.setText(tmpPrice + " zł");
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

    @FXML
    public Text selectedWorkPrice;

    @FXML
    public Text selectedMaterialPrice;

    @FXML
    public Text selectedSumPrice;

    public void clearSelected(){
        selectedName.setText("");
        selectedMinus.setText("");
        selectedPlus.setText("");
        selectedQuantity.setText("");
        selectedPrice.setText("");
        selectedMaterialPrice.setText("");
        selectedWorkPrice.setText("");
        selectedSumPrice.setText("");
    }
}
