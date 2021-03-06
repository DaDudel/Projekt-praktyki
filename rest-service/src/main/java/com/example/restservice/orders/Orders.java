package com.example.restservice.orders;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Orders {
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
    private Integer transId;
    private String client;
    private Double bruttoPrice;
    private Double nettoPrice;
    private Double discount;
    private String items;
    private Boolean isDone;
    private LocalDate timeStamp;

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Orders(Integer transId, String client, Double bruttoPrice, Double nettoPrice, Double discount, String items, Boolean isDone, LocalDate timeStamp) {
        this.transId = transId;
        this.client = client;
        this.bruttoPrice = bruttoPrice;
        this.nettoPrice = nettoPrice;
        this.discount = discount;
        this.items = items;
        this.isDone = isDone;
        this.timeStamp = timeStamp;
    }

    public Orders(Integer id, Integer transId, String client, Double bruttoPrice, Double nettoPrice, Double discount, String items, Boolean isDone, LocalDate timeStamp) {
        this.id = id;
        this.transId = transId;
        this.client = client;
        this.bruttoPrice = bruttoPrice;
        this.nettoPrice = nettoPrice;
        this.discount = discount;
        this.items = items;
        this.isDone = isDone;
        this.timeStamp = timeStamp;
    }

    public Orders(Integer transId, String client, Double bruttoPrice, Double nettoPrice, Double discount, String items, Boolean isDone) {
        this.transId = transId;
        this.client = client;
        this.bruttoPrice = bruttoPrice;
        this.nettoPrice = nettoPrice;
        this.discount = discount;
        this.items = items;
        this.isDone = isDone;
    }

    public Orders(Integer id, Integer transId, String client, Double bruttoPrice, Double nettoPrice, Double discount, String items, Boolean isDone) {
        this.id = id;
        this.transId = transId;
        this.client = client;
        this.bruttoPrice = bruttoPrice;
        this.nettoPrice = nettoPrice;
        this.discount = discount;
        this.items = items;
        this.isDone = isDone;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Orders() {
    }

    public Orders(Integer id, Integer transId, String client, Double bruttoPrice, Double nettoPrice, Double discount, String items) {
        this.id = id;
        this.transId = transId;
        this.client = client;
        this.bruttoPrice = bruttoPrice;
        this.nettoPrice = nettoPrice;
        this.discount = discount;
        this.items = items;
    }

    public Orders(Integer transId, String client, Double bruttoPrice, Double nettoPrice, Double discount, String items) {
        this.transId = transId;
        this.client = client;
        this.bruttoPrice = bruttoPrice;
        this.nettoPrice = nettoPrice;
        this.discount = discount;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Double getBruttoPrice() {
        return bruttoPrice;
    }

    public void setBruttoPrice(Double bruttoPrice) {
        this.bruttoPrice = bruttoPrice;
    }

    public Double getNettoPrice() {
        return nettoPrice;
    }

    public void setNettoPrice(Double nettoPrice) {
        this.nettoPrice = nettoPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return transId + " " + client;
    }
}
