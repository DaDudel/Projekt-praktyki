module javafx {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.example.javafx;
    opens com.example.javafx.httprequesters;
    opens com.example.javafx.controllers;
}