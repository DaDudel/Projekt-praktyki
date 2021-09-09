package com.example.javafxclient;

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
}
