package com.example.javafxclient.controllers;


import com.example.javafxclient.Article;
import com.example.javafxclient.Material;
import com.example.javafxclient.Orders;
import com.example.javafxclient.httprequesters.HttpRequesterArticle;
import com.example.javafxclient.httprequesters.HttpRequesterMaterial;
import com.example.javafxclient.httprequesters.HttpRequesterOrders;
import com.example.javafxclient.httprequesters.JsonGetter;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class SimpleUiController implements Initializable {
    private JsonGetter jsonGetter = new JsonGetter();
    private HttpRequesterMaterial httpRequesterMaterial = new HttpRequesterMaterial();
    private Material tempMaterial;
    private Article tempArticle;
    private Orders tempOrder;
    private HttpRequesterArticle httpRequesterArticle = new HttpRequesterArticle();
    private HttpRequesterOrders httpRequesterOrders = new HttpRequesterOrders();

    public SimpleUiController() {
    }

    public SimpleUiController(Orders tempOrder) {
        this.tempOrder = tempOrder;
    }

    public SimpleUiController(Article tempArticle) {
        this.tempArticle = tempArticle;
    }

    @FXML
    public Button connectButton;

    @FXML
    public ListView materialList;

    @FXML
    public TextField filterMaterial;

    @FXML
    public Label connectionLabel;

    @FXML
    public void connectJson(){
        refreshDatabase();
    }

    @FXML
    public void filterTable(){

    }

    @Override
    public void initialize (URL url, ResourceBundle rb){

        if(materialList !=null){
            refreshDatabase();
        }
    }

    @FXML
    public Label nameLabel;

    @FXML
    public Label quantityLabel;

    @FXML
    public Label priceLabel;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField quantityTextField;

    @FXML
    public TextField priceTextField;

    @FXML
    private void displaySelected(MouseEvent event){
        if((Material) materialList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) materialList.getSelectionModel().getSelectedItem();
        }
        if(tempMaterial==null){
            nameTextField.setText("");
            quantityTextField.setText("");
            priceTextField.setText("");
        }
        else{
            nameTextField.setText(tempMaterial.getName());
            quantityTextField.setText(tempMaterial.getQuantity().toString());
            priceTextField.setText(tempMaterial.getPrice().toString());
        }

    }

    @FXML
    public ListView ordersList;

    public void refreshDatabase(){
        try {
            ObservableList<Material> helpList = FXCollections.observableList(jsonGetter.getJson());
            materialList.setItems(helpList);

            ObservableList<Article> tempList = FXCollections.observableList(httpRequesterArticle.getRequest());
            articleList.setItems(tempList);

            ObservableList<Orders> tmpList = FXCollections.observableList(httpRequesterOrders.getRequest());
            ordersList.setItems(tmpList);

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

    public String updateName(){
        //Material material = (Material) testList.getSelectionModel().getSelectedItem();
        if((Material) materialList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) materialList.getSelectionModel().getSelectedItem();
        }

        if(nameTextField.getText()==""){
        }
        else{
            //httpRequester.editRequest(new Material(material.getId(), nameTextField.getText(),material.getQuantity(), material.getPrice()));
            //System.out.println("name changed to: "+nameTextField.getText());
            //connectDatabase();
            return nameTextField.getText();
        }
        return tempMaterial.getName();
    }

    @FXML
    public Button updateButton;

    @FXML
    public Button deleteMaterialButton;

    @FXML
    public Button addMaterialButton;

    @FXML
    public Label priceErrorLabel;

    @FXML
    public Label quantityErrorLabel;

    public Double updateQuantity(){
        //Material material = (Material) testList.getSelectionModel().getSelectedItem();
        if((Material) materialList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) materialList.getSelectionModel().getSelectedItem();
        }
        Double quantity;
        String temp = quantityTextField.getText();
        temp=temp.replaceAll(",",".");
        try{
            quantity=Double.parseDouble(temp);
            quantityErrorLabel.setText("");
            //httpRequester.editRequest(new Material(material.getId(), material.getName(),quantity, material.getPrice()));
            return quantity;

        }catch (Exception e){
            quantityErrorLabel.setTextFill(Color.RED);
            quantityErrorLabel.setText("Ilość musi być liczbą");
        }
        return tempMaterial.getQuantity();
    }

    public Double updatePrice(){
        if((Material) materialList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) materialList.getSelectionModel().getSelectedItem();
        }
        String temp = priceTextField.getText();
        temp=temp.replaceAll(",",".");
        Double price;
        try{
            price=Double.parseDouble(temp);
            priceErrorLabel.setText("");
            //httpRequester.editRequest(new Material(material.getId(), material.getName(),quantity, material.getPrice()));
            return price;

        }catch (Exception e){
            priceErrorLabel.setTextFill(Color.RED);
            priceErrorLabel.setText("Cena musi być liczbą");
        }
        return tempMaterial.getPrice();
    }

    @FXML
    public void updateDetails(){
        //Material material = (Material) testList.getSelectionModel().getSelectedItem();
        if((Material) materialList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) materialList.getSelectionModel().getSelectedItem();
        }
        try{
            httpRequesterMaterial.editRequest(new Material(tempMaterial.getId(), updateName(),updateQuantity(), updatePrice()));
            tempMaterial.setName(nameTextField.getText());
            tempMaterial.setPrice(updatePrice());
            tempMaterial.setQuantity(updateQuantity());
            //errorLabel.setText("");
        }catch (Exception e){

        }
        refreshDatabase();
    }

    @FXML
    public Label deleteMaterialLabel;

    @FXML
    public void deleteMaterial(){
        Material material = (Material) materialList.getSelectionModel().getSelectedItem();
        if(material==null){
            deleteMaterialLabel.setTextFill(Color.RED);
            deleteMaterialLabel.setText("Wybierz materiał do usunięcia.");
        }
        else {
            if(canDeleteMaterial(material)){
                httpRequesterMaterial.deleteRequest(material.getId());
                //System.out.println("delete");

                deleteMaterialLabel.setText("");
                nameTextField.setText("");
                priceTextField.setText("");
                quantityTextField.setText("");
                refreshDatabase();
            }
            else {
                deleteMaterialLabel.setTextFill(Color.RED);
                deleteMaterialLabel.setText("Nie można usunąć, materiał jest wykorzystywany.");
            }

        }

    }

    @FXML
    public Button plusButton;

    @FXML
    public Button minusButton;

    @FXML
    public void plusQuantity(){
        if((Material) materialList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) materialList.getSelectionModel().getSelectedItem();
        }
        httpRequesterMaterial.editRequest(new Material(tempMaterial.getId(), tempMaterial.getName(), tempMaterial.getQuantity()+1, tempMaterial.getPrice()));
        Double temp = tempMaterial.getQuantity()+1;
        tempMaterial.setQuantity(tempMaterial.getQuantity()+1);
        quantityTextField.setText(temp.toString());
        refreshDatabase();
    }

    @FXML
    public void minusQuantity(){
        if((Material) materialList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) materialList.getSelectionModel().getSelectedItem();
        }
        httpRequesterMaterial.editRequest(new Material(tempMaterial.getId(), tempMaterial.getName(), tempMaterial.getQuantity()-1, tempMaterial.getPrice()));
        Double temp = tempMaterial.getQuantity()-1;
        tempMaterial.setQuantity(tempMaterial.getQuantity()-1);
        quantityTextField.setText(temp.toString());
        refreshDatabase();
    }

    @FXML
    public void handleAddMaterialScene(ActionEvent event) throws  IOException{
        Stage stage;
        Parent root;
        System.out.println("Button pressed");

        if(event.getSource()==addMaterialButton){
            stage = new Stage();
            //root = FXMLLoader.load(getClass().getResource("/addMaterialPopUp.fxml"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addMaterialPopUp.fxml"));
            loader.setController(new SimpleUiController());
            root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Dodawanie materiału");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addMaterialButton.getScene().getWindow());
            stage.showAndWait();
            refreshDatabase();
        }
        else{
            if(event.getSource()==returnButton){
                stage = (Stage) returnButton.getScene().getWindow();
                stage.close();
            }
            else {
                if(event.getSource()==acceptButton){
                    stage = (Stage) acceptButton.getScene().getWindow();
                    Material material = new Material(getNewMaterialName(),getNewMaterialQuantity(),getNewMaterialPrice());
                    if(checkAdding()){
                        httpRequesterMaterial.addRequest(material);
                        stage.close();
                    }
                }
            }
        }
    }

    @FXML
    public Button returnButton;

    @FXML
    public void acceptMaterial(){

    }

    @FXML
    public TextField createMaterialNameTF;

    @FXML
    public Label createNameErrorLabel;

    public String getNewMaterialName(){
        String temp;
        temp = createMaterialNameTF.getText();
        if (temp.equals("")){
            createNameErrorLabel.setTextFill(Color.RED);
            createNameErrorLabel.setText("Podaj nazwę materiału.");
        }else{
            createNameErrorLabel.setText("");
        }
        return temp;
    }

    @FXML
    public TextField createMaterialQuantityTF;

    @FXML
    public Label createQuantityErrorLabel;

    public Double getNewMaterialQuantity(){
        Double quantity;
        try{
            String temp = createMaterialQuantityTF.getText();
            temp=temp.replaceAll(",",".");
            quantity=Double.parseDouble(temp);
            createQuantityErrorLabel.setText("");
            return quantity;
        }catch (Exception e){
            createQuantityErrorLabel.setTextFill(Color.RED);
            createQuantityErrorLabel.setText("Ilość musi być liczbą.");
            return 0.0;
        }
    }

    @FXML
    public Label createPriceErrorLabel;

    @FXML
    public TextField createMaterialPriceTF;

    public Double getNewMaterialPrice(){
        String temp = createMaterialPriceTF.getText();
        temp=temp.replaceAll(",",".");
        Double price;
        try{
            price=Double.parseDouble(temp);
            createPriceErrorLabel.setText("");
            return price;

        }catch (Exception e){
            createPriceErrorLabel.setTextFill(Color.RED);
            createPriceErrorLabel.setText("Cena musi być liczbą.");
        }
        return 0.0;
    }

    @FXML
    public Button acceptButton;

    Boolean checkAdding(){
        if(!createNameErrorLabel.getText().equals("")){
            return false;
        }
        if(!createQuantityErrorLabel.getText().equals("")){
            return false;
        }
        if(!createPriceErrorLabel.getText().equals("")){
            return false;
        }
        return true;
    }

    @FXML
    public ListView articleList;

    @FXML
    public TextField nameTextField1;

    @FXML
    public TextField quantityTextField1;

    @FXML
    public TextField priceTextField1;

    @FXML
    private void displaySelectedArticle(MouseEvent event){
        if((Article) articleList.getSelectionModel().getSelectedItem()!=null){
            tempArticle = (Article) articleList.getSelectionModel().getSelectedItem();
        }
        String codedMaterials;
        if(tempArticle==null){
            nameTextField1.setText("");
            quantityTextField1.setText("");
            priceTextField1.setText("");
            codedMaterials="";
        }
        else{
            nameTextField1.setText(tempArticle.getName());
            quantityTextField1.setText(tempArticle.getQuantity().toString());
            priceTextField1.setText(tempArticle.getPrice().toString());
            codedMaterials=tempArticle.getMaterials();
        }

        stringCutter(codedMaterials);

    }

    public Material findMaterial(Integer id, Double quantity){
        try {
            ObservableList<Material> helpList = FXCollections.observableList(jsonGetter.getJson());
            for (Material temp: helpList){
                if(temp.getId()==id){
                    return new Material(id,temp.getName(),quantity,temp.getPrice());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public ListView materialsList;

    public void stringCutter(String string){
        ObservableList<Material> templist = FXCollections.observableArrayList();
        if(string.equals("")){
            materialsList.setItems(templist);
            return;
        }

        while (!string.equals("")){
            Integer index = string.indexOf(",");
            String sIdNumber = string.substring(0,index);
            Integer idNumber = Integer.parseInt(sIdNumber);

            string = string.substring(index+1);
            index = string.indexOf(";");
            String sQ = string.substring(0,index);
            Double q = Double.parseDouble(sQ);

            string = string.substring(index+1);

            templist.add(findMaterial(idNumber,q));

        }
        materialsList.setItems(templist);
    }

    @FXML
    public Button updateArticleButton;

    @FXML
    public Button deleteArticleButton;

    @FXML
    public Button addArticleButton;

    @FXML
    public Label priceErrorLabel1;

    @FXML
    public Label quantityErrorLabel1;

    @FXML
    public Label deleteArticleLabel;

    @FXML
    public void deleteArticle(){
        Article article = (Article) articleList.getSelectionModel().getSelectedItem();
        if(article==null){
            deleteArticleLabel.setTextFill(Color.RED);
            deleteArticleLabel.setText("Wybierz przedmiot do usunięcia");
        }
        else {
            httpRequesterArticle.deleteRequest(article.getId());
            deleteArticleLabel.setText("");
            nameTextField1.setText("");
            priceTextField1.setText("");
            quantityTextField1.setText("");
            refreshDatabase();
        }

    }

    public Integer updateArticleQuantity(){
        if((Article) articleList.getSelectionModel().getSelectedItem()!=null){
            tempArticle = (Article) articleList.getSelectionModel().getSelectedItem();
        }
        Integer quantity;
        try{
            quantity=Integer.parseInt(quantityTextField1.getText());
            quantityErrorLabel1.setText("");
            return quantity;

        }catch (Exception e){
            quantityErrorLabel1.setTextFill(Color.RED);
            quantityErrorLabel1.setText("Ilość musi być liczbą całkowitą");
        }
        return tempArticle.getQuantity();
    }

    public Double updateArticlePrice(){
        if((Article) articleList.getSelectionModel().getSelectedItem()!=null){
            tempArticle = (Article) articleList.getSelectionModel().getSelectedItem();
        }
        String temp = priceTextField1.getText();
        temp=temp.replaceAll(",",".");
        Double price;
        try{
            price=Double.parseDouble(temp);
            priceErrorLabel1.setText("");
            return price;

        }catch (Exception e){
            priceErrorLabel1.setTextFill(Color.RED);
            priceErrorLabel1.setText("Cena musi być liczbą");
        }
        return tempArticle.getPrice();
    }

    public String updateArticleName(){
        if((Article) articleList.getSelectionModel().getSelectedItem()!=null){
            tempArticle = (Article) articleList.getSelectionModel().getSelectedItem();
        }

        if(nameTextField1.getText()==""){
        }
        else{
            return nameTextField1.getText();
        }
        return tempArticle.getName();
    }

    @FXML
    public void updateArticleDetails(){
        if((Article) articleList.getSelectionModel().getSelectedItem()!=null){
            tempArticle = (Article) articleList.getSelectionModel().getSelectedItem();
        }
        try{
            httpRequesterArticle.editRequest(new Article(tempArticle.getId(), updateArticleName(),updateArticleQuantity(), updateArticlePrice(), tempArticle.getMaterials()));
            tempArticle.setName(nameTextField1.getText());
            tempArticle.setPrice(updateArticlePrice());
        }catch (Exception e){

        }
        refreshDatabase();
    }

    @FXML
    public Button plusButton1;

    @FXML
    public Button minusButton1;

    @FXML
    public void plusArticleQuantity(){
        if((Article) articleList.getSelectionModel().getSelectedItem()!=null){
            tempArticle = (Article) articleList.getSelectionModel().getSelectedItem();
        }
        httpRequesterArticle.editRequest(new Article(tempArticle.getId(), tempArticle.getName(), tempArticle.getQuantity()+1, tempArticle.getPrice(), tempArticle.getMaterials()));
        Integer temp = tempArticle.getQuantity()+1;
        tempArticle.setQuantity(tempArticle.getQuantity()+1);
        quantityTextField1.setText(temp.toString());
        refreshDatabase();
    }

    @FXML
    public void minusArticleQuantity(){
        if((Article) articleList.getSelectionModel().getSelectedItem()!=null){
            tempArticle = (Article) articleList.getSelectionModel().getSelectedItem();
        }
        httpRequesterArticle.editRequest(new Article(tempArticle.getId(), tempArticle.getName(), tempArticle.getQuantity()-1, tempArticle.getPrice(), tempArticle.getMaterials()));
        Integer temp = tempArticle.getQuantity()-1;
        tempArticle.setQuantity(tempArticle.getQuantity()-1);
        quantityTextField1.setText(temp.toString());
        refreshDatabase();
    }

    @FXML
    public Button editMaterialsButton;

    @FXML
    public ListView editMaterialsList;

    @FXML
    public void handleEditMaterialsWindow(Event event) throws IOException{
        Stage stage;
        Parent root;

        if(event.getSource()==editMaterialsButton){
            if(tempArticle==null){
                return;
            }

            stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editArticleMaterials.fxml"));
            loader.setController(new SimpleUiController(tempArticle));
            root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Edycja materiałów");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addMaterialButton.getScene().getWindow());
            stage.showAndWait();

            refreshDatabase();
            tempArticle.setMaterials(getSingleArticle(tempArticle.getId()).getMaterials());
            refreshArticleList();
        }
        else {
            if(event.getSource()==connectEditButton){
                stage = (Stage) connectEditButton.getScene().getWindow();

                ObservableList<Material> helpList = null;
                try {
                    helpList = FXCollections.observableList(jsonGetter.getJson());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                editMaterialsList.setItems(helpList);

                String codedMaterials=tempArticle.getMaterials();
                stringCutterEdit(codedMaterials);

            }
            else {
                if(event.getSource()==exitEditButton){
                    stage = (Stage) exitEditButton.getScene().getWindow();
                    stage.close();
                }
                else {
                    if(event.getSource()==addMaterialToListButton){
                        stage = (Stage) addMaterialToListButton.getScene().getWindow();
                        Material gotOne = (Material) editMaterialsList.getSelectionModel().getSelectedItem();
                        addMaterialToArticle(tempArticle,gotOne);
                        tempArticle.setMaterials(getSingleArticle(tempArticle.getId()).getMaterials());
                        String codedMaterials=tempArticle.getMaterials();
                        stringCutterEdit(codedMaterials);
                    }
                    else {
                        if(event.getSource()==deleteFromMaterials){
                            stage = (Stage) deleteFromMaterials.getScene().getWindow();
                            Material gotOne = (Material) editMaterialsList2.getSelectionModel().getSelectedItem();
                            deleteMaterialFromArticle(tempArticle,gotOne);
                            tempArticle.setMaterials(getSingleArticle(tempArticle.getId()).getMaterials());
                            String codedMaterials=tempArticle.getMaterials();
                            stringCutterEdit(codedMaterials);
                        }
                        else{
                            if(event.getSource()==materialWindowUpdateButton){
                                stage = (Stage) materialWindowUpdateButton.getScene().getWindow();
                                Material gotOne = (Material) editMaterialsList2.getSelectionModel().getSelectedItem();
                                gotOne.setQuantity(returnMaterialQuantity());

                                editMaterialQuantityInArticle(tempArticle,gotOne);

                                tempArticle.setMaterials(getSingleArticle(tempArticle.getId()).getMaterials());
                                String codedMaterials=tempArticle.getMaterials();
                                stringCutterEdit(codedMaterials);

                            }
                            else{
                                if(event.getSource()==editMaterialsList2){
                                    stage = (Stage) editMaterialsList2.getScene().getWindow();
                                    Material tmp = (Material) editMaterialsList2.getSelectionModel().getSelectedItem();
                                    articleMaterialsQuantity.setText(tmp.getQuantity().toString());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Double returnMaterialQuantity(){
        if((Material) editMaterialsList2.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) editMaterialsList2.getSelectionModel().getSelectedItem();
        }
        String temp = articleMaterialsQuantity.getText();
        temp=temp.replaceAll(",",".");
        Double quantity;
        try{
            quantity=Double.parseDouble(temp);
            editMaterialsWindowLabel.setText("");
            return quantity;

        }catch (Exception e){
            editMaterialsWindowLabel.setTextFill(Color.RED);
            editMaterialsWindowLabel.setText("Cena musi być liczbą");
        }
        return tempMaterial.getQuantity();

    }

    @FXML
    public Label editMaterialsWindowLabel;

    @FXML
    public TextField articleMaterialsQuantity;

    @FXML
    public Button materialWindowUpdateButton;

    @FXML
    public Button deleteFromMaterials;

    public void addMaterialToArticle(Article art, Material mat){
        String str = art.getMaterials();
        str = str + mat.getId()+",0;";
        art.setMaterials(str);
        httpRequesterArticle.editRequest(art);
    }

    public void deleteMaterialFromArticle(Article art, Material mat){
        String str = art.getMaterials();
        String fullStr = art.getMaterials();
        Integer index = str.indexOf(mat.getId()+",");
        if(index!=0){
            index = str.indexOf(";"+mat.getId()+",")+1;
        }
        str = str.substring(index);
        String temp = mat.getId().toString();
        Integer tempLength = temp.length() + 1;

        index = str.indexOf(";");
        str = str.substring(0,index+1);

        if(fullStr.indexOf(str)==0){
            fullStr=fullStr.replace(str,"");
        }
        else {
            fullStr=fullStr.replace(";"+str,";");
        }

        //System.out.println(fullStr);
        httpRequesterArticle.editRequest(new Article(art.getId(),art.getName(),art.getQuantity(),art.getPrice(),fullStr));
        tempArticle.setMaterials(fullStr);
    }

    public void editMaterialQuantityInArticle(Article art, Material mat){
        String str = art.getMaterials();
        String fullStr = art.getMaterials();
        Integer index = str.indexOf(mat.getId()+",");
        //System.out.println(index);
        if(index!=0){
            index = str.indexOf(";"+mat.getId()+",")+1;
        }
        str = str.substring(index);
        String temp = mat.getId().toString();
        Integer tempLength = temp.length() + 1;

        index = str.indexOf(";");
        str = str.substring(0,index+1);

        if(fullStr.indexOf(str)==0){
            fullStr=fullStr.replace(str,"");
        }
        else {
            fullStr=fullStr.replace(";"+str,";");
        }

        fullStr = fullStr+mat.getId()+","+mat.getQuantity()+";";

        httpRequesterArticle.editRequest(new Article(art.getId(),art.getName(),art.getQuantity(),art.getPrice(),fullStr));
        tempArticle.setMaterials(fullStr);
    }

    @FXML
    public ListView editMaterialsList2;

    public void stringCutterEdit(String string){
        ObservableList<Material> templist = FXCollections.observableArrayList();
        if(string.equals("")){
            editMaterialsList2.setItems(templist);
            return;
        }

        while (!string.equals("")){
            Integer index = string.indexOf(",");
            String sIdNumber = string.substring(0,index);
            Integer idNumber = Integer.parseInt(sIdNumber);

            string = string.substring(index+1);
            index = string.indexOf(";");
            String sQ = string.substring(0,index);
            Double q = Double.parseDouble(sQ);

            string = string.substring(index+1);

            templist.add(findMaterial(idNumber,q));

        }
        editMaterialsList2.setItems(templist);

    }

    @FXML
    public Button exitEditButton;

    @FXML
    public Button connectEditButton;

    @FXML
    public Button addMaterialToListButton;

    public Article getSingleArticle(Integer id){
        try {
            ObservableList<Article> helpList = FXCollections.observableList(httpRequesterArticle.getRequest());
            for(Article art: helpList){
                if(id == art.getId()){
                    return art;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void refreshArticleList(){
        String codedMaterials;
        nameTextField1.setText(tempArticle.getName());
        quantityTextField1.setText(tempArticle.getQuantity().toString());
        priceTextField1.setText(tempArticle.getPrice().toString());
        codedMaterials=tempArticle.getMaterials();

        stringCutter(codedMaterials);
    }

    @FXML
    public void handleAddArticleScene(Event event) throws IOException{
        Stage stage;
        Parent root;
        if(event.getSource()==addArticleButton){
            stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addArticlePopUp.fxml"));
            loader.setController(new SimpleUiController());
            root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Dodawanie przedmiotu");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addMaterialButton.getScene().getWindow());
            stage.showAndWait();
            refreshDatabase();
        }
        else {
            if(event.getSource()==acceptButton){
                stage = (Stage) acceptButton.getScene().getWindow();
                Article article = new Article(getNewMaterialName(),getNewArticleQuantity(),getNewMaterialPrice(),"");
                if(checkAdding()){
                    httpRequesterArticle.addRequest(article);
                    stage.close();
                }
            }
            else{
                if(event.getSource()==returnButton){
                    stage = (Stage) returnButton.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }
    public Integer getNewArticleQuantity(){
        Integer quantity;
        try{
            String temp = createMaterialQuantityTF.getText();
            quantity = Integer.parseInt(temp);
            createQuantityErrorLabel.setText("");
            return quantity;
        }catch (Exception e){
            createQuantityErrorLabel.setTextFill(Color.RED);
            createQuantityErrorLabel.setText("Ilość musi być liczbą całkowitą.");
            return 0;
        }
    }

    public Boolean canDeleteMaterial(Material material){

        Integer matId = material.getId();
        String matIdStr = matId.toString();
        Integer index;

        try {
            ObservableList<Article> tempList = FXCollections.observableList(httpRequesterArticle.getRequest());

            for(Article art:tempList){

                String mats = art.getMaterials();

                index = mats.indexOf(matIdStr+",");

                if (index == 0){
                    return false;
                }
                if (index > 1){
                    index = mats.indexOf(";"+matIdStr+",");
                    if(index == -1){

                    }
                    else{
                        return false;
                    }
                }

            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;

    }

    //////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    public TextField transIdTF;

    @FXML
    public TextField clientTF;

    @FXML
    public TextField bruttoPriceTF;

    @FXML
    public TextField nettoPriceTF;

    @FXML
    public TextField discountTF;

    @FXML
    public void displaySelectedOrder(MouseEvent event){
        if((Orders) ordersList.getSelectionModel().getSelectedItem()!=null){
            tempOrder = (Orders) ordersList.getSelectionModel().getSelectedItem();
        }
        String codedItems;
        if(tempOrder==null){
            transIdTF.setText("");
            clientTF.setText("");
            bruttoPriceTF.setText("");
            nettoPriceTF.setText("");
            discountTF.setText("");
            codedItems="";
        }
        else{
            transIdTF.setText(tempOrder.getTransId().toString());
            clientTF.setText(tempOrder.getClient());
            bruttoPriceTF.setText(tempOrder.getBruttoPrice().toString());
            nettoPriceTF.setText(tempOrder.getNettoPrice().toString());
            discountTF.setText(tempOrder.getDiscount().toString());
            codedItems=tempOrder.getItems();
        }
        stringCutterOrders(codedItems);
    }

    @FXML
    public ListView itemsList;

    public void stringCutterOrders(String string){
        ObservableList<Article> templist = FXCollections.observableArrayList();
        if(string.equals("")){
            itemsList.setItems(templist);
            return;
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
        itemsList.setItems(templist);
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
        System.out.println("null");
        return null;
    }

    @FXML
    public Button updateOrderButton;

    public Integer updateOrderTransId(){
        if(ordersList!=null){
            if((Orders) ordersList.getSelectionModel().getSelectedItem()!=null){
                tempOrder = (Orders) ordersList.getSelectionModel().getSelectedItem();
            }
        }
        Integer id;
        try{
            id=Integer.parseInt(transIdTF.getText());
            quantityErrorLabel1.setText("");
            return id;

        }catch (Exception e){
            quantityErrorLabel1.setTextFill(Color.RED);
            quantityErrorLabel1.setText("ID musi być liczbą całkowitą");
        }
        if(tempOrder!=null){
            return tempOrder.getTransId();
        }
        else{
            return 0;
        }
    }

    public String updateOrderClient(){
        if(ordersList!=null){
            if((Orders) ordersList.getSelectionModel().getSelectedItem()!=null){
                tempOrder = (Orders) ordersList.getSelectionModel().getSelectedItem();
            }
        }

        if(clientTF.getText()==""){
        }
        else{
            return clientTF.getText();
        }
        if(tempOrder!=null){
            return tempOrder.getClient();
        }
        else {
            return "";
        }

    }

    public Double updateBruttoPrice(){
        if(ordersList!=null){
            if((Orders) ordersList.getSelectionModel().getSelectedItem()!=null){
                tempOrder = (Orders) ordersList.getSelectionModel().getSelectedItem();
            }
        }
        String temp = bruttoPriceTF.getText();
        temp=temp.replaceAll(",",".");
        Double price;
        try{
            price=Double.parseDouble(temp);
            priceErrorLabel1.setText("");
            return price;

        }catch (Exception e){
            priceErrorLabel1.setTextFill(Color.RED);
            priceErrorLabel1.setText("Cena musi być liczbą");
        }
        if(tempOrder!=null){
            return tempOrder.getBruttoPrice();
        }
        else {
            return 0.0;
        }
    }

    public Double updateNettoPrice(){
        if(ordersList!=null){
            if((Orders) ordersList.getSelectionModel().getSelectedItem()!=null){
                tempOrder = (Orders) ordersList.getSelectionModel().getSelectedItem();
            }
        }
        String temp = nettoPriceTF.getText();
        temp=temp.replaceAll(",",".");
        Double price;
        try{
            price=Double.parseDouble(temp);
            deleteArticleLabel.setText("");
            return price;

        }catch (Exception e){
            deleteArticleLabel.setTextFill(Color.RED);
            deleteArticleLabel.setText("Cena musi być liczbą");
        }
        if(tempOrder!=null){
            return tempOrder.getNettoPrice();
        }
        else {
            return 0.0;
        }

    }

    public Double updateDiscount(){
        if(ordersList!=null){
            if((Orders) ordersList.getSelectionModel().getSelectedItem()!=null){
                tempOrder = (Orders) ordersList.getSelectionModel().getSelectedItem();
            }
        }
        String temp = discountTF.getText();
        temp=temp.replaceAll(",",".");
        Double price;
        try{
            price=Double.parseDouble(temp);
            discErrorLabel.setText("");
            return price;

        }catch (Exception e){
            discErrorLabel.setTextFill(Color.RED);
            discErrorLabel.setText("Rabat musi być liczbą");
        }
        if(tempOrder!=null){
            return tempOrder.getDiscount();
        }
        else {
            return 0.0;
        }
    }

    @FXML
    public void updateOrderDetails(){
        if((Orders) ordersList.getSelectionModel().getSelectedItem()!=null){
            tempOrder = (Orders) ordersList.getSelectionModel().getSelectedItem();
        }
        try{
            httpRequesterOrders.editRequest(new Orders(tempOrder.getId(), updateOrderTransId(),updateOrderClient(),
                    updateBruttoPrice(), updateNettoPrice(), updateDiscount() ,tempOrder.getItems()));

            tempOrder.setTransId(updateOrderTransId());
            tempOrder.setClient(updateOrderClient());
            tempOrder.setBruttoPrice(updateBruttoPrice());
            tempOrder.setNettoPrice(updateNettoPrice());
            tempOrder.setDiscount(updateDiscount());


        }catch (Exception e){

        }
        refreshDatabase();
    }

    @FXML
    public Label discErrorLabel;

    @FXML
    public Button deleteOrderButton;

    @FXML
    public void deleteOrder(){
        Orders order = (Orders) ordersList.getSelectionModel().getSelectedItem();
        if(order==null){
            deleteArticleLabel.setTextFill(Color.RED);
            deleteArticleLabel.setText("Wybierz transakcję do usunięcia");
        }
        else {
            httpRequesterOrders.deleteRequest(order.getId());
            deleteArticleLabel.setText("");
            transIdTF.setText("");
            clientTF.setText("");
            bruttoPriceTF.setText("");
            nettoPriceTF.setText("");
            discountTF.setText("");
            refreshDatabase();
        }
    }

    @FXML
    public Button addOrderButton;

    @FXML
    public void handleAddOrderScene(Event event) throws IOException{
        Stage stage;
        Parent root;
        if(event.getSource()==addOrderButton){
            stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addOrderPopUp.fxml"));
            loader.setController(new SimpleUiController());
            root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Dodawanie zamówienia");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addMaterialButton.getScene().getWindow());
            stage.showAndWait();
            refreshDatabase();
        }
        else {
            if(event.getSource()==acceptButton){
                stage = (Stage) acceptButton.getScene().getWindow();
                Orders order = new Orders(updateOrderTransId(),updateOrderClient(),updateBruttoPrice(),updateNettoPrice(),
                        updateDiscount(),"");
                if(checkAddingOrder()){
                    httpRequesterOrders.addRequest(order);
                    stage.close();
                }
            }
            else{
                if(event.getSource()==returnButton){
                    stage = (Stage) returnButton.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    Boolean checkAddingOrder(){
        if(!quantityErrorLabel1.getText().equals("")){
            return false;
        }
        if(!priceErrorLabel1.getText().equals("")){
            return false;
        }
        if(!deleteArticleLabel.getText().equals("")){
            return false;
        }
        if(!discErrorLabel.getText().equals("")){
            return false;
        }
        return true;
    }

    @FXML
    public Button editArticlesButton;

    @FXML
    public void handleEditArticlesWindow(Event event) throws IOException{
        Stage stage;
        Parent root;

        if(event.getSource()==editArticlesButton){
            if(tempOrder==null){
                return;
            }

            stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editOrderArticles.fxml"));
            loader.setController(new SimpleUiController(tempOrder));
            root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Edycja zamówienia");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addMaterialButton.getScene().getWindow());
            stage.showAndWait();

            refreshDatabase();
            tempOrder.setItems(getSingleOrder(tempOrder.getId()).getItems());
            refreshOrderList();
        }
        else {
            if(event.getSource()==connectEditButton){
                stage = (Stage) connectEditButton.getScene().getWindow();

                ObservableList<Article> helpList = null;
                try {
                    helpList = FXCollections.observableList(httpRequesterArticle.getRequest());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                editMaterialsList.setItems(helpList);

                String codedMaterials= tempOrder.getItems();
                stringCutterEditOrder(codedMaterials);
            }

            else {
                if(event.getSource()==exitEditButton){
                    stage = (Stage) exitEditButton.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    public Orders getSingleOrder(Integer id){
        try {
            ObservableList<Orders> helpList = FXCollections.observableList(httpRequesterOrders.getRequest());
            for(Orders ord: helpList){
                if(id == ord.getId()){
                    return ord;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void refreshOrderList(){
        String codedItems;
        transIdTF.setText(tempOrder.getTransId().toString());
        clientTF.setText(tempOrder.getClient());
        bruttoPriceTF.setText(tempOrder.getBruttoPrice().toString());
        nettoPriceTF.setText(tempOrder.getNettoPrice().toString());
        discountTF.setText(tempOrder.getDiscount().toString());
        codedItems= tempOrder.getItems();

        stringCutterOrders(codedItems);
    }

    public void stringCutterEditOrder(String string){
        ObservableList<Article> templist = FXCollections.observableArrayList();
        if(string.equals("")){
            editMaterialsList2.setItems(templist);
            return;
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
        editMaterialsList2.setItems(templist);

    }

}

