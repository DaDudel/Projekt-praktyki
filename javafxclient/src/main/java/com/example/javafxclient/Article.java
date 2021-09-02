package com.example.javafxclient;

public class Article {
    private Integer id;
    private String name;
    private Integer quantity;
    private Double price;
    private String materials;

    public Article(Integer id, String name, Integer quantity, Double price, String materials) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.materials = materials;
    }

    public Article(String name, Integer quantity, Double price, String materials) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.materials = materials;
    }

    public Article() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    @Override
    public String toString() {
        return name + " " + quantity;
    }
}

