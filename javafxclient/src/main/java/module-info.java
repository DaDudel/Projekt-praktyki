module com.example.javafxclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens com.example.javafxclient;
    opens com.example.javafxclient.httprequesters;
    opens com.example.javafxclient.controllers;
}