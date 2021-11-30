package com.example.javafxclient;


import java.time.LocalDate;


public class MaterialHistory {
    private Integer id;
    private Integer materialId;
    private Double change;
    private LocalDate timeStamp;

    public MaterialHistory() {
    }

    public MaterialHistory(Integer materialId, Double change, LocalDate timeStamp) {
        this.materialId = materialId;
        this.change = change;
        this.timeStamp = timeStamp;
    }

    public MaterialHistory(Integer id, Integer materialId, Double change, LocalDate timeStamp) {
        this.id = id;
        this.materialId = materialId;
        this.change = change;
        this.timeStamp = timeStamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return
                "materialId=" + materialId +
                ", change=" + change +
                ", timeStamp=" + timeStamp;
    }
}
