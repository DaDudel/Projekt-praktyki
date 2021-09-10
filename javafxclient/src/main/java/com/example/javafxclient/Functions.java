package com.example.javafxclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Functions {

    public Functions() {
    }

    public String removePolish(String string){
        string=string.replaceAll("ą","a");
        string=string.replaceAll("ć","c");
        string=string.replaceAll("ę","e");
        string=string.replaceAll("ł","l");
        string=string.replaceAll("ń","n");
        string=string.replaceAll("ó","o");
        string=string.replaceAll("ś","s");
        string=string.replaceAll("ź","z");
        string=string.replaceAll("ż","z");
        string=string.replaceAll("Ą","A");
        string=string.replaceAll("Ć","C");
        string=string.replaceAll("Ę","E");
        string=string.replaceAll("Ł","L");
        string=string.replaceAll("Ń","N");
        string=string.replaceAll("Ó","O");
        string=string.replaceAll("Ś","S");
        string=string.replaceAll("Ź","Z");
        string=string.replaceAll("Ż","Z");
        return string;
    }

    public Double roundDouble(Double d){
        return (Double) (((double) Math.round(d*100))/100);
    }

    public Double returnSum(List templist){

        Double sum = 0.0;

        for(Material mat: (ObservableList<Material>) templist){
            Double temp = (Double) mat.getPrice() * (Double) mat.getQuantity();
            sum=sum+temp;
        }
        return sum;
    }

    public List reduceElements(List tempList){
        List helpList = tempList;
        ObservableList<Material>finalMaterials = FXCollections.observableArrayList();
        Integer j = helpList.size();
        for (Integer i = 0; i<j; i++){
            Material tempMaterial = (Material) helpList.get(i);
            for (Integer k = i+1;k<j;k++){
                Material secondMaterial = (Material) helpList.get(k);
                if(tempMaterial.getId()==secondMaterial.getId()){
                    tempMaterial.setQuantity(tempMaterial.getQuantity()+secondMaterial.getQuantity());
                    helpList.remove(secondMaterial);
                    k--;
                    j=helpList.size();
                }
            }
            finalMaterials.add(tempMaterial);
        }
        return finalMaterials;
    }
}
