package com.example.restservice.materialhistory;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class MaterialHistoryService {
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
    private Integer materialId;
    private Double change;
    private LocalDate timeStamp;

    public MaterialHistoryService() {
    }

    public MaterialHistoryService(Integer materialId, Double change, LocalDate timeStamp) {
        this.materialId = materialId;
        this.change = change;
        this.timeStamp = timeStamp;
    }

    public MaterialHistoryService(Integer id, Integer materialId, Double change, LocalDate timeStamp) {
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
}
