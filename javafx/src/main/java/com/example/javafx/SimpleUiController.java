package com.example.javafx;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SimpleUiController implements Initializable {
    private JsonGetter jsonGetter = new JsonGetter();
    private HttpRequester httpRequester = new HttpRequester();
    private final HostServices hostServices;
    private Material tempMaterial;


    @FXML
    public Label label;

    @FXML
    public Button button;

    @FXML
    public Button connectButton;

    @FXML
    public ListView testList;

    @FXML
    public TextField filterMaterial;

    @FXML
    public Label connectionLabel;

    SimpleUiController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    public void connectJson(){
        refreshDatabase();
    }
    @FXML
    public void filterTable(){

    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        if(testList!=null){
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
        if((Material) testList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) testList.getSelectionModel().getSelectedItem();
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

    public void refreshDatabase(){
        try {
            ObservableList<Material> helpList = FXCollections.observableList(jsonGetter.getJson());
            testList.setItems(helpList);
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
        if((Material) testList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) testList.getSelectionModel().getSelectedItem();
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

    public Integer updateQuantity(){
        //Material material = (Material) testList.getSelectionModel().getSelectedItem();
        if((Material) testList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) testList.getSelectionModel().getSelectedItem();
        }
        Integer quantity;
        try{
            quantity=Integer.parseInt(quantityTextField.getText());
            quantityErrorLabel.setText("");
            //httpRequester.editRequest(new Material(material.getId(), material.getName(),quantity, material.getPrice()));
            return quantity;

        }catch (Exception e){
            quantityErrorLabel.setTextFill(Color.RED);
            quantityErrorLabel.setText("Ilość musi być liczbą całkowitą");
        }
        return tempMaterial.getQuantity();
    }

    public Double updatePrice(){
        //Material material = (Material) testList.getSelectionModel().getSelectedItem();
        if((Material) testList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) testList.getSelectionModel().getSelectedItem();
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
        if((Material) testList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) testList.getSelectionModel().getSelectedItem();
        }
        try{
            httpRequester.editRequest(new Material(tempMaterial.getId(), updateName(),updateQuantity(), updatePrice()));
            tempMaterial.setName(nameTextField.getText());
            tempMaterial.setPrice(updatePrice());
            //errorLabel.setText("");
        }catch (Exception e){

        }
        refreshDatabase();
    }

    @FXML
    public Label deleteMaterialLabel;

    @FXML
    public void deleteMaterial(){
        Material material = (Material) testList.getSelectionModel().getSelectedItem();
        if(material==null){
            deleteMaterialLabel.setTextFill(Color.RED);
            deleteMaterialLabel.setText("Wybierz materiał do usunięcia");
        }
        else {
            httpRequester.deleteRequest(material.getId());
            deleteMaterialLabel.setText("");
            nameTextField.setText("");
            priceTextField.setText("");
            quantityTextField.setText("");
            refreshDatabase();
        }

    }

    @FXML
    public Button plusButton;

    @FXML
    public Button minusButton;

    @FXML
    public void plusQuantity(){
        if((Material) testList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) testList.getSelectionModel().getSelectedItem();
        }
        httpRequester.editRequest(new Material(tempMaterial.getId(), tempMaterial.getName(), tempMaterial.getQuantity()+1, tempMaterial.getPrice()));
        Integer temp = tempMaterial.getQuantity()+1;
        tempMaterial.setQuantity(tempMaterial.getQuantity()+1);
        quantityTextField.setText(temp.toString());
        //refreshDatabase();
    }

    @FXML
    public void minusQuantity(){
        if((Material) testList.getSelectionModel().getSelectedItem()!=null){
            tempMaterial = (Material) testList.getSelectionModel().getSelectedItem();
        }
        httpRequester.editRequest(new Material(tempMaterial.getId(), tempMaterial.getName(), tempMaterial.getQuantity()-1, tempMaterial.getPrice()));
        Integer temp = tempMaterial.getQuantity()-1;
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
            loader.setController(new SimpleUiController(hostServices));
            root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Dodawanie materiału");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addMaterialButton.getScene().getWindow());
            stage.showAndWait();
            refreshDatabase();
        }
        else{
//            stage = (Stage) returnButton.getScene().getWindow();
//            getNewMaterialName();
//            System.out.println(getNewMaterialQuantity());
//            getNewMaterialPrice();
//            //stage.close();
            if(event.getSource()==returnButton){
                stage = (Stage) returnButton.getScene().getWindow();
                stage.close();
            }
            else {
                if(event.getSource()==acceptButton){
                    stage = (Stage) acceptButton.getScene().getWindow();
                    Material material = new Material(getNewMaterialName(),getNewMaterialQuantity(),getNewMaterialPrice());
                    if(checkAdding()){
                        httpRequester.addRequest(material);
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

    public Integer getNewMaterialQuantity(){
        Integer quantity;
        try{
            quantity=Integer.parseInt(createMaterialQuantityTF.getText());
            createQuantityErrorLabel.setText("");
            return quantity;
        }catch (Exception e){
            createQuantityErrorLabel.setTextFill(Color.RED);
            createQuantityErrorLabel.setText("Ilość musi być liczbą całkowitą.");
            return 0;
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

}
