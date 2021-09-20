package com.example.restservice.article;

import javax.persistence.*;

@Entity
@Table
public class Article {
    @Id
    @SequenceGenerator(
            name = "material_sequence",
            sequenceName = "material_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "material_sequence"
    )
    private Integer id;
    private String name;
    private Integer quantity;
    private Double price;
    private String materials;
    private Double workPrice;

    public Article(String name, Integer quantity, Double price, String materials, Double workPrice) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.materials = materials;
        this.workPrice = workPrice;
    }

    public Article(Integer id, String name, Integer quantity, Double price, String materials, Double workPrice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.materials = materials;
        this.workPrice = workPrice;
    }

    public Double getWorkPrice() {
        return workPrice;
    }

    public void setWorkPrice(Double workPrice) {
        this.workPrice = workPrice;
    }

    public Article() {
    }

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
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", materials='" + materials + '\'' +
                '}';
    }
}
