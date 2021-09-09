package com.example.javafxclient;
public class Material {
    private Integer id;
    private String name;
    private Double quantity;
    private Double price;

    public Material(String name, Double quantity, Double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Material() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Material(Integer id, String name, Double quantity, Double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        Double roundQuantity =(Double) (((double) Math.round(this.quantity*100))/100);
        return name + " " + roundQuantity;
    }
}

