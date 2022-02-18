package com.example.javafxclient.controllers;


import com.example.javafxclient.Article;
import com.example.javafxclient.Functions;
import com.example.javafxclient.Material;
import com.example.javafxclient.Orders;
import com.example.javafxclient.httprequesters.HttpRequesterArticle;
import com.example.javafxclient.httprequesters.HttpRequesterMaterial;
import com.example.javafxclient.httprequesters.HttpRequesterOrders;
import com.example.javafxclient.httprequesters.JsonGetter;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;
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
    private Functions functions = new Functions();

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
        cleanErrors();

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
            httpRequesterMaterial.doHistory(tempMaterial,new Material(tempMaterial.getId(),
                    functions.removePolish(updateName()),updateQuantity(), updatePrice()));
            httpRequesterMaterial.editRequest(new Material(tempMaterial.getId(),
                    functions.removePolish(updateName()),updateQuantity(), updatePrice()));
            tempMaterial.setName(functions.removePolish(nameTextField.getText()));
            tempMaterial.setPrice(updatePrice());
            tempMaterial.setQuantity(updateQuantity());
            //errorLabel.setText("");
        }catch (Exception e){

        }
        refreshDatabase();
        sumMaterialCosts(tempOrder);
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
        httpRequesterMaterial.doHistory(tempMaterial,new Material(tempMaterial.getId(), tempMaterial.getName(),
                tempMaterial.getQuantity()+1, tempMaterial.getPrice()));
        httpRequesterMaterial.editRequest(new Material(tempMaterial.getId(), tempMaterial.getName(),
                tempMaterial.getQuantity()+1, tempMaterial.getPrice()));
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
        httpRequesterMaterial.doHistory(tempMaterial,new Material(tempMaterial.getId(), tempMaterial.getName(),
                tempMaterial.getQuantity()-1, tempMaterial.getPrice()));
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
        //System.out.println("Button pressed");

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
                    Material material = new Material(functions.removePolish(getNewMaterialName()) ,getNewMaterialQuantity(),getNewMaterialPrice());
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
        if(createWorkPriceErrorLabel!=null&&!(createWorkPriceErrorLabel.getText().equals(""))){
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
        deleteArticleLabel.setText("");
        String codedMaterials;
        if(tempArticle==null){
            nameTextField1.setText("");
            quantityTextField1.setText("");
            priceTextField1.setText("");
            codedMaterials="";
            workPriceTF.setText("");
        }
        else{
            nameTextField1.setText(tempArticle.getName());
            quantityTextField1.setText(tempArticle.getQuantity().toString());
            priceTextField1.setText(tempArticle.getPrice().toString());
            codedMaterials=tempArticle.getMaterials();
            workPriceTF.setText(tempArticle.getWorkPrice().toString());
        }

        stringCutter(codedMaterials);
        cleanErrors();

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
            deleteArticleLabel.setText("Wybierz produkt do usunięcia");
        }
        else {
            if(canDeleteArticle(article)){
                httpRequesterArticle.deleteRequest(article.getId());
                deleteArticleLabel.setText("");
                nameTextField1.setText("");
                priceTextField1.setText("");
                quantityTextField1.setText("");
                refreshDatabase();
            }
            else {
                deleteArticleLabel.setTextFill(Color.RED);
                deleteArticleLabel.setText("Nie można usunąć, produkt jest wykorzystywany.");
            }

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

    public Double updateArticleWorkPrice(){
        if((Article) articleList.getSelectionModel().getSelectedItem()!=null){
            tempArticle = (Article) articleList.getSelectionModel().getSelectedItem();
        }
        String temp = workPriceTF.getText();
        temp=temp.replaceAll(",",".");
        Double price;
        try{
            price=Double.parseDouble(temp);
            discErrorLabel.setText("");
            return price;

        }catch (Exception e){
            discErrorLabel.setTextFill(Color.RED);
            discErrorLabel.setText("Koszt musi być liczbą");
        }
        return tempArticle.getWorkPrice();
    }

    @FXML
    public void updateArticleDetails(){
        if((Article) articleList.getSelectionModel().getSelectedItem()!=null){
            tempArticle = (Article) articleList.getSelectionModel().getSelectedItem();
        }
        try{
            httpRequesterArticle.doHistory(tempArticle,new Article(tempArticle.getId(),
                    functions.removePolish(updateArticleName()),updateArticleQuantity(), updateArticlePrice(),
                    tempArticle.getMaterials()));
            httpRequesterArticle.editRequest(new Article(tempArticle.getId(),functions.removePolish(updateArticleName()),
                    updateArticleQuantity(), updateArticlePrice(), tempArticle.getMaterials(), updateArticleWorkPrice()));
            tempArticle.setName(functions.removePolish(nameTextField1.getText()));
            tempArticle.setPrice(updateArticlePrice());
            tempArticle.setWorkPrice(updateArticleWorkPrice());
            tempArticle.setQuantity(updateArticleQuantity());
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

        httpRequesterArticle.doHistory(tempArticle,new Article(tempArticle.getId(), tempArticle.getName(),
                tempArticle.getQuantity()+1, tempArticle.getPrice(), tempArticle.getMaterials()));

        httpRequesterArticle.editRequest(new Article(tempArticle.getId(), tempArticle.getName(),
                tempArticle.getQuantity()+1, tempArticle.getPrice(), tempArticle.getMaterials(),
                tempArticle.getWorkPrice()));
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
        httpRequesterArticle.doHistory(tempArticle,new Article(tempArticle.getId(), tempArticle.getName(),
                tempArticle.getQuantity()-1, tempArticle.getPrice(), tempArticle.getMaterials()));
        httpRequesterArticle.editRequest(new Article(tempArticle.getId(), tempArticle.getName(),
                tempArticle.getQuantity()-1, tempArticle.getPrice(), tempArticle.getMaterials(),
                tempArticle.getWorkPrice()));
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
            fillUsedMaterials(tempOrder);
            sumMaterialCosts(tempOrder);
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
        str = str + mat.getId()+",1.0;";
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
        httpRequesterArticle.editRequest(new Article(art.getId(),art.getName(),art.getQuantity(),art.getPrice(),fullStr,art.getWorkPrice()));
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

        httpRequesterArticle.editRequest(new Article(art.getId(),art.getName(),art.getQuantity(),art.getPrice(),fullStr,art.getWorkPrice()));
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
            stage.setTitle("Dodawanie produktu");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addMaterialButton.getScene().getWindow());
            stage.showAndWait();
            refreshDatabase();
        }
        else {
            if(event.getSource()==acceptButton){
                stage = (Stage) acceptButton.getScene().getWindow();
                Article article = new Article(functions.removePolish(getNewMaterialName()),getNewArticleQuantity(),getNewMaterialPrice(),"",getNewArticleWorkPrice());
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
    public Double getNewArticleWorkPrice(){
        String temp = createMaterialWorkPriceTF.getText();
        temp=temp.replaceAll(",",".");
        Double price;
        try{
            price=Double.parseDouble(temp);
            createWorkPriceErrorLabel.setText("");
            return price;

        }catch (Exception e){
            createWorkPriceErrorLabel.setTextFill(Color.RED);
            createWorkPriceErrorLabel.setText("Koszt musi być liczbą.");
        }
        return 0.0;
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
    public ListView usedMaterialsList;

    public void fillUsedMaterials(Orders ord){
        if(ord==null){
            ObservableList<Material> templist = FXCollections.observableArrayList();
            usedMaterialsList.setItems(templist);
            return;
        }
        if(ord.getItems()==""){
            ObservableList<Material> templist = FXCollections.observableArrayList();
            usedMaterialsList.setItems(templist);
            return;
        }

        ObservableList<Article> tempArticleList = FXCollections.observableList(stringCutterOrdersToList(ord.getItems()));
        ObservableList<Material> tempMaterialList = FXCollections.observableArrayList();

        for(Article art: tempArticleList){
            for (Integer i = 0;i<art.getQuantity();i++){
                tempMaterialList.addAll(stringCutterArticleToList(art.getMaterials()));
            }
        }

        tempMaterialList=(ObservableList<Material>) functions.reduceElements(tempMaterialList);
        usedMaterialsList.setItems(tempMaterialList);

    }

    public List stringCutterArticleToList(String string){
        ObservableList<Material> templist = FXCollections.observableArrayList();
        if(string.equals("")){
            materialsList.setItems(templist);
            return templist;
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
        return templist;
    }

    public List stringCutterOrdersToList(String string){
        ObservableList<Article> templist = FXCollections.observableArrayList();
        if(string.equals("")){
            itemsList.setItems(templist);
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
            realisedCheckBox.setSelected(false);
        }
        else{
            transIdTF.setText(tempOrder.getTransId().toString());
            clientTF.setText(tempOrder.getClient());
            bruttoPriceTF.setText(tempOrder.getBruttoPrice().toString());
            nettoPriceTF.setText(tempOrder.getNettoPrice().toString());
            discountTF.setText(tempOrder.getDiscount().toString());
            codedItems=tempOrder.getItems();
            realisedCheckBox.setSelected(tempOrder.getDone());
            if(tempOrder.getDone()){
                realizeOrder.setDisable(true);
            }
            else{
                realizeOrder.setDisable(false);
            }
        }
        stringCutterOrders(codedItems);
        fillUsedMaterials(tempOrder);
        sumMaterialCostsv2(tempOrder);
        //setPrices(tempOrder);
        noMaterials.setText("");
        //disable when done
        //disable button when not enough
        fillNeededMaterials();
        cleanErrors();
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
        //System.out.println("null");
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
            httpRequesterOrders.editRequest(new Orders(tempOrder.getId(), updateOrderTransId(),
                    functions.removePolish(updateOrderClient()), updateBruttoPrice(), updateNettoPrice(), updateDiscount() ,tempOrder.getItems(),tempOrder.getDone()));

            tempOrder.setTransId(updateOrderTransId());
            tempOrder.setClient(functions.removePolish(updateOrderClient()));
            tempOrder.setBruttoPrice(updateBruttoPrice());
            tempOrder.setNettoPrice(updateNettoPrice());
            tempOrder.setDiscount(updateDiscount());
            //setPrices(tempOrder);


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
            deleteArticleLabel.setText("Wybierz zamówienie do usunięcia");
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
                Orders order = new Orders(updateOrderTransId(),functions.removePolish(updateOrderClient()) ,updateBruttoPrice(),updateNettoPrice(),
                        updateDiscount(),"",false);
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
            fillUsedMaterials(tempOrder);
            sumMaterialCosts(tempOrder);
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
                else {
                    if(event.getSource()==addMaterialToListButton){
                        stage = (Stage) addMaterialToListButton.getScene().getWindow();
                        Article gotOne = (Article) editMaterialsList.getSelectionModel().getSelectedItem();
                        addArticleToOrder(tempOrder,gotOne);
                        tempOrder.setItems(getSingleOrder(tempOrder.getId()).getItems());
                        String codedItems = tempOrder.getItems();
                        stringCutterEditOrder(codedItems);
                    }
                    else {
                        if(event.getSource()==deleteFromMaterials){
                            stage = (Stage) deleteFromMaterials.getScene().getWindow();
                            Article gotOne = (Article) editMaterialsList2.getSelectionModel().getSelectedItem();
                            deleteArticleFromOrder(tempOrder, gotOne);
                            String codedItems = tempOrder.getItems();
                            stringCutterEditOrder(codedItems);
                        }
                        else{
                            if(event.getSource()==materialWindowUpdateButton){
                                stage = (Stage) materialWindowUpdateButton.getScene().getWindow();
                                Article gotOne = (Article) editMaterialsList2.getSelectionModel().getSelectedItem();
                                gotOne.setQuantity(returnArticleQuantity());
                                editArticleQuantityInOrder(tempOrder,gotOne);
                                tempOrder.setItems(getSingleOrder(tempOrder.getId()).getItems());
                                String codedItems = tempOrder.getItems();
                                stringCutterEditOrder(codedItems);
                            }
                            else{
                                if(event.getSource()==editMaterialsList2){
                                    stage = (Stage) editMaterialsList2.getScene().getWindow();
                                    Article tmp = (Article) editMaterialsList2.getSelectionModel().getSelectedItem();
                                    articleMaterialsQuantity.setText((tmp.getQuantity().toString()));
                                }
                            }
                        }
                    }
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

    public void addArticleToOrder(Orders ord, Article art){
        String str = ord.getItems();
        str = str + art.getId()+",1;";
        ord.setItems(str);
        httpRequesterOrders.editRequest(ord);
    }

    public void deleteArticleFromOrder(Orders ord, Article art){
        String str = ord.getItems();
        String fullStr = ord.getItems();
        Integer index = str.indexOf(art.getId()+",");
        if(index!=0){
            index = str.indexOf(";"+art.getId()+",")+1;
        }
        str = str.substring(index);
        String temp = art.getId().toString();
        Integer tempLength = temp.length() + 1;

        index = str.indexOf(";");
        str = str.substring(0,index+1);

        if(fullStr.indexOf(str)==0){
            fullStr=fullStr.replace(str,"");
        }
        else {
            fullStr=fullStr.replace(";"+str,";");
        }

        httpRequesterOrders.editRequest(new Orders(ord.getId(),ord.getTransId(),ord.getClient(),ord.getBruttoPrice(),
                ord.getNettoPrice(),ord.getDiscount(),fullStr,ord.getDone()));

        tempOrder.setItems(fullStr);
    }

    public Integer returnArticleQuantity(){
        if((Article) editMaterialsList2.getSelectionModel().getSelectedItem()!=null){
            tempArticle = (Article) editMaterialsList2.getSelectionModel().getSelectedItem();
        }
        String temp = articleMaterialsQuantity.getText();
        temp=temp.replaceAll(",",".");
        Integer quantity;
        try{
            quantity=Integer.parseInt(temp);
            editMaterialsWindowLabel.setText("");
            return quantity;

        }catch (Exception e){
            editMaterialsWindowLabel.setTextFill(Color.RED);
            editMaterialsWindowLabel.setText("Cena musi być liczbą");
        }
        return tempArticle.getQuantity();

    }

    public void editArticleQuantityInOrder(Orders ord, Article art){
        String str = ord.getItems();
        String fullStr = ord.getItems();
        Integer index = str.indexOf(art.getId()+",");
        if(index!=0){
            index = str.indexOf(";"+art.getId()+",")+1;
        }
        str = str.substring(index);
        String temp = art.getId().toString();
        Integer tempLength = temp.length() + 1;

        index = str.indexOf(";");
        str = str.substring(0,index+1);

        if(fullStr.indexOf(str)==0){
            fullStr=fullStr.replace(str,"");
        }
        else {
            fullStr=fullStr.replace(";"+str,";");
        }

        fullStr = fullStr+art.getId()+","+art.getQuantity()+";";

        httpRequesterOrders.editRequest(new Orders(ord.getId(),ord.getTransId(),ord.getClient(),ord.getBruttoPrice(),
                ord.getNettoPrice(),ord.getDiscount(),fullStr,ord.getDone()));
        tempOrder.setItems(fullStr);
    }

    @FXML
    public Label materialCosts;

    public void sumMaterialCosts(Orders ord){
        if(ord==null){
            materialCosts.setText("0 zł");
            return;
        }
        if(ord.getItems()==""){
            materialCosts.setText("0 zł");
            return;
        }
        //Double sum = 0.0;
        ObservableList<Article> tempArticleList = FXCollections.observableList(stringCutterOrdersToList(ord.getItems()));
        ObservableList<Material> tempMaterialList = FXCollections.observableArrayList();

        for(Article art: tempArticleList){

            for (Integer i = 0;i<art.getQuantity();i++){
                tempMaterialList.addAll(stringCutterArticleToList(art.getMaterials()));
            }
        }

        tempMaterialList=(ObservableList<Material>) functions.reduceElements(tempMaterialList);

        Double costs =(Double) (((double) Math.round(functions.returnSum(tempMaterialList)*100))/100);

        materialCosts.setText(costs + " zł");

    }

    public Boolean canDeleteArticle(Article article){

        Integer artId = article.getId();
        String artIdStr = artId.toString();
        Integer index;

        try {
            ObservableList<Orders> tempList = FXCollections.observableList(httpRequesterOrders.getRequest());

            for(Orders ord:tempList){

                String arts = ord.getItems();

                index = arts.indexOf(artIdStr+",");

                if (index == 0){
                    return false;
                }
                if (index > 1){
                    index = arts.indexOf(";"+artIdStr+",");
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

    @FXML
    public Label noMaterials;

    public Boolean checkMaterials(){
        if(usedMaterialsList!=null){
            refreshDatabase();
            List tempList = usedMaterialsList.getItems();
            List tempMaterials = materialList.getItems();

            for(Material mat: (ObservableList<Material>) tempList){
                for (Material magMat : (ObservableList<Material>) tempMaterials){
                    if(mat.getId()==magMat.getId()){
                        if((magMat.getQuantity()- mat.getQuantity())<0){
                            noMaterials.setText("BRAK MATERIAŁÓW");
                            return false;
                        }
                    }
                }
            }
            noMaterials.setText("");
            return true;
        }
        noMaterials.setText("");
        return false;
    }

    @FXML
    public Button realizeOrder;

//    @FXML
//    public void realize(){
//        if(checkMaterials()){
//            refreshDatabase();
//            List tempList = usedMaterialsList.getItems();
//            List tempMaterials = materialList.getItems();
//
//            for(Material mat: (ObservableList<Material>) tempList){
//                for (Material magMat : (ObservableList<Material>) tempMaterials){
//                    if(mat.getId()==magMat.getId()){
//                        Material newMaterial = new Material(magMat.getId(), magMat.getName(),
//                                functions.roundDouble(magMat.getQuantity()-mat.getQuantity()), magMat.getPrice());
//                        //System.out.println(newMaterial);
//                        httpRequesterMaterial.editRequest(newMaterial);
//
//                    }
//                }
//            }
//            tempOrder.setDone(true);
//            realisedCheckBox.setSelected(true);
//            httpRequesterOrders.editRequest(tempOrder);
//            realizeOrder.setDisable(true);
//            refreshDatabase();
//        }
//
//    }

    @FXML
    public Label bruttoDiscount;

    @FXML
    public Label nettoDiscount;

//    public void setPrices(Orders ord){
//        if(ord==null)
//        {
//            bruttoDiscount.setText("");
//            nettoDiscount.setText("");
//            return;
//        }
//        Double bPrice = functions.roundDouble(ord.getBruttoPrice()*(100-ord.getDiscount())/100);
//        bruttoDiscount.setText(bPrice + " zł");
//        Double nPrice = functions.roundDouble(ord.getNettoPrice()*(100-ord.getDiscount())/100);
//        nettoDiscount.setText(nPrice + " zł");
//        return;
//    }

    @FXML
    public void filterMaterialTable(){
        ObservableList<Material> data = materialList.getItems();
        FilteredList<Material> filteredData = new FilteredList<>(data, s -> true);

        filterMaterial.textProperty().addListener(obs->{
            String filter = filterMaterial.getText().toLowerCase();
            if(filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            }
            else {
                filteredData.setPredicate(s -> s.toString().toLowerCase().contains(filter));
            }
        });
        materialList.setItems(filteredData);
    }

    @FXML
    public TextField filterArticle;

    @FXML
    public TextField filterOrders;

    @FXML
    public void filterArticleTable(){
        ObservableList<Article> data = articleList.getItems();
        FilteredList<Article> filteredData = new FilteredList<>(data, s -> true);

        filterArticle.textProperty().addListener(obs->{
            String filter = filterArticle.getText().toLowerCase();
            if(filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            }
            else {
                filteredData.setPredicate(s -> s.toString().toLowerCase().contains(filter));
            }
        });
        articleList.setItems(filteredData);
    }

    @FXML
    public void filterOrdersTable(){
        ObservableList<Orders> data = ordersList.getItems();
        FilteredList<Orders> filteredData = new FilteredList<>(data, s -> true);

        filterOrders.textProperty().addListener(obs->{
            String filter = filterOrders.getText().toLowerCase();
            if(filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            }
            else {
                filteredData.setPredicate(s -> s.toString().toLowerCase().contains(filter));
            }
        });
        ordersList.setItems(filteredData);
    }

    @FXML
    public TextField orderSearchBar;

    @FXML
    public void filterEditOrder(){
        ObservableList<Article> data = editMaterialsList.getItems();
        FilteredList<Article> filteredData = new FilteredList<>(data, s -> true);

        orderSearchBar.textProperty().addListener(obs->{
            String filter = orderSearchBar.getText().toLowerCase();
            if(filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            }
            else {
                filteredData.setPredicate(s -> s.toString().toLowerCase().contains(filter));
            }
        });
        editMaterialsList.setItems(filteredData);
    }

    @FXML
    public TextField articleSearchBar;

    @FXML
    public void filterEditArticle(){
        ObservableList<Material> data = editMaterialsList.getItems();
        FilteredList<Material> filteredData = new FilteredList<>(data, s -> true);

        articleSearchBar.textProperty().addListener(obs->{
            String filter = articleSearchBar.getText().toLowerCase();
            if(filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            }
            else {
                filteredData.setPredicate(s -> s.toString().toLowerCase().contains(filter));
            }
        });
        editMaterialsList.setItems(filteredData);
    }

    @FXML
    public TextField workPriceTF;

    @FXML
    public TextField createMaterialWorkPriceTF;

    @FXML
    public Label createWorkPriceErrorLabel;

    @FXML
    public CheckBox realisedCheckBox;

    @FXML
    public void changeRealisation(){
        httpRequesterOrders.editRequest(new Orders(tempOrder.getId(), tempOrder.getTransId(), tempOrder.getClient(),
                tempOrder.getBruttoPrice(), tempOrder.getNettoPrice(), tempOrder.getDiscount(), tempOrder.getItems(),
                realisedCheckBox.isSelected()));
        tempOrder.setDone(realisedCheckBox.isSelected());
        if(tempOrder.getDone()){
            realizeOrder.setDisable(true);
        }
        else{
            realizeOrder.setDisable(false);
        }
    }

    Boolean checkMaterialsv2(){
        //skopiować przedmioty z list i podmienic do innych
        if(usedMaterialsList!=null){
            refreshDatabase();
//            ObservableList<Article> usedArticlesList =  itemsList.getItems();
//            ObservableList<Material> tempList = FXCollections.observableArrayList();
//            ObservableList<Material> tempMaterials = materialList.getItems();
//            ObservableList<Article> tempArticles = articleList.getItems();


            ObservableList<Article> usedArticlesList =  FXCollections.observableList(itemsList.getItems());
            ObservableList<Material> tempList = FXCollections.observableArrayList();
            ObservableList<Material> tempMaterials = FXCollections.observableList(materialList.getItems());
            ObservableList<Article> tempArticles = FXCollections.observableList(articleList.getItems());

            ObservableList<Article> usedArticlesList2 =  FXCollections.observableArrayList();
            ObservableList<Material> tempList2 = FXCollections.observableArrayList();
            ObservableList<Material> tempMaterials2 = FXCollections.observableArrayList();
            ObservableList<Article> tempArticles2 = FXCollections.observableArrayList();


            //System.out.println(itemsList.getItems());

            for (Article art: (ObservableList<Article>) usedArticlesList){
                for(Article magArt : (ObservableList<Article>)tempArticles){
                    if(art.getId()==magArt.getId()){
                        Article tmpArt = new Article(art.getId(),art.getName(),
                                art.getQuantity(),art.getPrice(),art.getMaterials(), art.getWorkPrice());
                        //System.out.println("check: itemy przed: "+magArt.getQuantity());
                        //System.out.println("check: potrzebne przed: "+tmpArt.getQuantity());
                        if((magArt.getQuantity()-art.getQuantity())<0){
                            tmpArt.setQuantity(art.getQuantity()-magArt.getQuantity());
                            usedArticlesList2.add(tmpArt);
                            //art.setQuantity(art.getQuantity()-magArt.getQuantity());
                            magArt.setQuantity(0);
                        }
                        else {
                            magArt.setQuantity(magArt.getQuantity()-art.getQuantity());
                            //art.setQuantity(0);
                            tmpArt.setQuantity(0);
                            usedArticlesList2.add(tmpArt);
                        }
                        //System.out.println("check: itemy po: "+magArt.getQuantity());
                        //System.out.println("check: potrzebne po: "+tmpArt.getQuantity());
                    }
                }
            }

            //System.out.println(itemsList.getItems());
            //System.out.println();

            for(Article art: (ObservableList<Article>) usedArticlesList2){
                for (Integer i = 0;i<art.getQuantity();i++){
                    tempList.addAll(stringCutterArticleToList(art.getMaterials()));
                }
            }

            tempList=(ObservableList<Material>) functions.reduceElements(tempList);

            for(Material mat: (ObservableList<Material>) tempList){
                for (Material magMat : (ObservableList<Material>) tempMaterials){
                    if(mat.getId()==magMat.getId()){
                        if((magMat.getQuantity()- mat.getQuantity())<0){
                            noMaterials.setText("BRAK MATERIAŁÓW");
                            return false;
                        }
                    }
                }
            }
            noMaterials.setText("");
            return true;
        }
        noMaterials.setText("");
        return false;
    }

    @FXML
    public void realizev2(){
        if(checkMaterialsv2()){
            refreshDatabase();
            fillUsedMaterials(tempOrder);
            List usedArticlesList = itemsList.getItems();
            List tempList = FXCollections.observableArrayList();
            List tempMaterials = materialList.getItems();
            List tempArticles = articleList.getItems();

            ObservableList<Article> usedArticlesList2 =  FXCollections.observableArrayList();

            System.out.println(itemsList.getItems());

            for (Article art: (ObservableList<Article>) usedArticlesList){
                for(Article magArt : (ObservableList<Article>)tempArticles){
                    if(art.getId()==magArt.getId()){
                        System.out.println("itemy przed: "+magArt.getQuantity());
                        System.out.println("potrzebne przed: "+art.getQuantity());
                        Article tmpArt = new Article(art.getId(),art.getName(),
                                art.getQuantity(),art.getPrice(),art.getMaterials(), art.getWorkPrice());
                        if((magArt.getQuantity()-art.getQuantity())<0){
                            tmpArt.setQuantity(art.getQuantity()-magArt.getQuantity());
                            //art.setQuantity(art.getQuantity()-magArt.getQuantity());
                            usedArticlesList2.add(tmpArt);
                            magArt.setQuantity(0);
                        }
                        else {
                            magArt.setQuantity(magArt.getQuantity()-art.getQuantity());
                            //art.setQuantity(0);
                            tmpArt.setQuantity(0);
                            usedArticlesList2.add(tmpArt);
                        }
                        System.out.println("itemy po: "+magArt.getQuantity());
                        System.out.println("potrzebne po: "+tmpArt.getQuantity());
                        Article newArticle = new Article(magArt.getId(), magArt.getName(), magArt.getQuantity(), magArt.getPrice(),
                                magArt.getMaterials(), magArt.getWorkPrice());
                        httpRequesterArticle.editRequest(newArticle);
                    }
                }
            }

            for(Article art: (ObservableList<Article>) usedArticlesList2){
                for (Integer i = 0;i<art.getQuantity();i++){
                    tempList.addAll(stringCutterArticleToList(art.getMaterials()));
                }
            }

            tempList=(ObservableList<Material>) functions.reduceElements(tempList);

            for(Material mat: (ObservableList<Material>) tempList){
                for (Material magMat : (ObservableList<Material>) tempMaterials){
                    if(mat.getId()==magMat.getId()){
                        Material newMaterial = new Material(magMat.getId(), magMat.getName(),
                                functions.roundDouble(magMat.getQuantity()-mat.getQuantity()), magMat.getPrice());
                        Material oldMaterial = new Material(magMat.getId(), magMat.getName(),
                                functions.roundDouble(magMat.getQuantity()), magMat.getPrice());
                        //System.out.println(newMaterial);

                        httpRequesterMaterial.editRequest(newMaterial);
                        httpRequesterMaterial.doHistory(oldMaterial,newMaterial);

                    }
                }
            }
            tempOrder.setDone(true);
            realisedCheckBox.setSelected(true);
            httpRequesterOrders.editRequest(tempOrder);
            realizeOrder.setDisable(true);
            refreshDatabase();
        }

    }

    @FXML
    public ListView usedMaterialsList1;

    public void fillNeededMaterials(){
        if(usedMaterialsList!=null){
            //refreshDatabase();

            ObservableList<Article> usedArticlesList =  FXCollections.observableList(itemsList.getItems());
            ObservableList<Material> tempList = FXCollections.observableArrayList();
            ObservableList<Material> tempMaterials = FXCollections.observableList(materialList.getItems());
            ObservableList<Article> tempArticles = FXCollections.observableList(articleList.getItems());

            ObservableList<Article> usedArticlesList2 =  FXCollections.observableArrayList();
            ObservableList<Material> tempList2 = FXCollections.observableArrayList();
            ObservableList<Material> tempMaterials2 = FXCollections.observableArrayList();
            ObservableList<Article> tempArticles2 = FXCollections.observableArrayList();


            //System.out.println(itemsList.getItems());

            for (Article art: (ObservableList<Article>) usedArticlesList){
                for(Article magArt : (ObservableList<Article>)tempArticles){
                    if(art.getId()==magArt.getId()){
                        Article tmpArt = new Article(art.getId(),art.getName(),
                                art.getQuantity(),art.getPrice(),art.getMaterials(), art.getWorkPrice());
                        //System.out.println("check: itemy przed: "+magArt.getQuantity());
                        //System.out.println("check: potrzebne przed: "+tmpArt.getQuantity());
                        if((magArt.getQuantity()-art.getQuantity())<0){
                            tmpArt.setQuantity(art.getQuantity()-magArt.getQuantity());
                            usedArticlesList2.add(tmpArt);
                            //art.setQuantity(art.getQuantity()-magArt.getQuantity());
                            magArt.setQuantity(0);
                        }
                        else {
                            magArt.setQuantity(magArt.getQuantity()-art.getQuantity());
                            //art.setQuantity(0);
                            tmpArt.setQuantity(0);
                            usedArticlesList2.add(tmpArt);
                        }
                        //System.out.println("check: itemy po: "+magArt.getQuantity());
                        //System.out.println("check: potrzebne po: "+tmpArt.getQuantity());
                    }
                }
            }

            //System.out.println(itemsList.getItems());
            //System.out.println();

            for(Article art: (ObservableList<Article>) usedArticlesList2){
                for (Integer i = 0;i<art.getQuantity();i++){
                    tempList.addAll(stringCutterArticleToList(art.getMaterials()));
                }
            }

            tempList=(ObservableList<Material>) functions.reduceElements(tempList);

            for(Material mat: (ObservableList<Material>) tempList){
                for (Material magMat : (ObservableList<Material>) tempMaterials){
                    if(mat.getId()==magMat.getId()){
                        if((magMat.getQuantity()- mat.getQuantity())<0){
                            ///////
                            tempMaterials2.add(new Material(mat.getId(),mat.getName(),
                                    mat.getQuantity()-magMat.getQuantity(), mat.getPrice()));
                        }

                    }
                }
            }
            usedMaterialsList1.setItems(tempMaterials2);
        }
    }

    public void sumMaterialCostsv2(Orders ord){
        if(ord==null){
            materialCosts.setText("0 zł");
            return;
        }
        if(ord.getItems()==""){
            materialCosts.setText("0 zł");
            return;
        }

        ObservableList<Article> usedArticlesList =  FXCollections.observableList(stringCutterOrdersToList(ord.getItems()));
        ObservableList<Article> tempArticles = FXCollections.observableList(articleList.getItems());

        ObservableList<Article> usedArticlesList2 =  FXCollections.observableArrayList();

        Double sum = 0.0;
        ObservableList<Material> tempMaterialList = FXCollections.observableArrayList();

        for (Article art: (ObservableList<Article>) usedArticlesList){
            for(Article magArt : (ObservableList<Article>)tempArticles){
                if(art.getId()==magArt.getId()){
                    Article tmpArt = new Article(art.getId(),art.getName(),
                            art.getQuantity(),art.getPrice(),art.getMaterials(), magArt.getWorkPrice());
                    if((magArt.getQuantity()-art.getQuantity())<0){
                        tmpArt.setQuantity(art.getQuantity()-magArt.getQuantity());
                        usedArticlesList2.add(tmpArt);
                    }
                    else {
                        tmpArt.setQuantity(0);
                        usedArticlesList2.add(tmpArt);
                    }
                }
            }
        }


        for(Article art: usedArticlesList2){
            for (Integer i = 0;i<art.getQuantity();i++){
                tempMaterialList.addAll(stringCutterArticleToList(art.getMaterials()));
                sum = sum + art.getWorkPrice();
            }
        }

        tempMaterialList=(ObservableList<Material>) functions.reduceElements(tempMaterialList);

        Double costs =(Double) (((double) Math.round((functions.returnSum(tempMaterialList)+sum)*100))/100);

        materialCosts.setText(costs + " zł");

    }

    public void cleanErrors(){
        priceErrorLabel1.setText("");
        quantityErrorLabel.setText("");
        deleteMaterialLabel.setText("");
        quantityErrorLabel1.setText("");
        priceErrorLabel.setText("");
        deleteArticleLabel.setText("");
        discErrorLabel.setText("");
        noMaterials.setText("");
    }

    public void stringCutterArticleToFile(String string){
        ObservableList<Material> templist = FXCollections.observableArrayList();
        if(string.equals("")){
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
    }

    public void saveToFile(){
        try {
            ObservableList<Material> tempMaterialList = FXCollections.observableList(materialList.getItems());
            ObservableList<Article> tempArticleList = FXCollections.observableList(articleList.getItems());
            ObservableList<Orders> tempOrdersList = FXCollections.observableList(ordersList.getItems());
            PrintStream myWriter = new PrintStream("savedData.txt");
            myWriter.println("Materialy:");
            for (Material mat : tempMaterialList){
                myWriter.println("nazwa: "+mat.getName());
                myWriter.println("cena: "+mat.getPrice().toString());
                myWriter.println("ilosc: "+mat.getQuantity().toString());
                myWriter.println();
            }
            myWriter.println("Produkty:");
            for (Article art : tempArticleList){
                myWriter.println("nazwa: "+art.getName());
                myWriter.println("cena: "+art.getPrice());
                myWriter.println("ilosc: "+art.getQuantity());
                myWriter.println("robocizna: "+art.getWorkPrice());
                myWriter.println("sklad:");
                String string = art.getMaterials();
                while (!string.equals("")){
                    Integer index = string.indexOf(",");
                    String sIdNumber = string.substring(0,index);
                    Integer idNumber = Integer.parseInt(sIdNumber);

                    string = string.substring(index+1);
                    index = string.indexOf(";");
                    String sQ = string.substring(0,index);
                    Double q = Double.parseDouble(sQ);
                    string = string.substring(index+1);
                    myWriter.println(findMaterial(idNumber,q).getName() + ";" +findMaterial(idNumber,q).getQuantity());
                }
                myWriter.println();
            }
            myWriter.println("Zamowienia:");
            for(Orders ord : tempOrdersList){
                myWriter.println("id: "+ord.getTransId());
                myWriter.println("klient: "+ord.getClient());
                myWriter.println("cena brutto: "+ord.getBruttoPrice());
                myWriter.println("cena netto: "+ord.getNettoPrice());
                myWriter.println("rabat: "+ord.getDiscount());
                myWriter.println("czy zrealizowane: " + ord.getDone());
                myWriter.println("sklad:");
                String string = ord.getItems();
                while (!string.equals("")){
                    Integer index = string.indexOf(",");
                    String sIdNumber = string.substring(0,index);
                    Integer idNumber = Integer.parseInt(sIdNumber);

                    string = string.substring(index+1);
                    index = string.indexOf(";");
                    String sQ = string.substring(0,index);
                    Integer q = Integer.parseInt(sQ);
                    string = string.substring(index+1);
                    myWriter.println(findArticle(idNumber,q).getName() + ";" +findArticle(idNumber,q).getQuantity());
                }
                myWriter.println();
            }
            myWriter.close();
        } catch (IOException e) {
        }
    }

    @FXML
    public Button saveBtn;

    @FXML
    public void saveAll(){
        saveToFile();
    }

    @FXML
    public void handleLogin(Event event) throws IOException {
        Stage stage;
        Parent root;
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginPopUp.fxml"));
        loader.setController(new SimpleUiController());
        root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Logowanie");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addMaterialButton.getScene().getWindow());stage.showAndWait();
    }

    @FXML
    public Button loginButton;

    @FXML
    public PasswordField pField;

    @FXML
    public Label wrongPasswordLabel;

    @FXML
    public void switchScene()throws IOException{
        if(pField.getText().equals("piwo1234")){
            Stage stage;
            Parent root;
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui.fxml"));
            //loader.setController(new SimpleUiController());
            root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Aplikacja");
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initOwner(addMaterialButton.getScene().getWindow());
            stage.show();
            pField.getScene().getWindow().hide();
        }
        else {
            wrongPasswordLabel.setTextFill(Color.RED);
            wrongPasswordLabel.setText("Błędne hasło");
        }
    }

    @FXML
    public Button statsBtn;

    @FXML
    public void openStats() throws IOException{
        Stage stage;
        Parent root;
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/statsPopUp.fxml"));
        //loader.setController(new SimpleUiController());
        root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Statystyki");
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner(addMaterialButton.getScene().getWindow());
        stage.show();
        //pField.getScene().getWindow().hide();
    }
}

