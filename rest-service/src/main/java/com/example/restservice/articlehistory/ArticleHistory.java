package com.example.restservice.articlehistory;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class ArticleHistory {

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
    private Integer articleId;
    private Integer change;
    private LocalDate timeStamp;

    public ArticleHistory() {
    }

    public ArticleHistory(Integer materialId, Integer change, LocalDate timeStamp) {
        this.articleId = materialId;
        this.change = change;
        this.timeStamp = timeStamp;
    }

    public ArticleHistory(Integer id, Integer materialId, Integer change, LocalDate timeStamp) {
        this.id = id;
        this.articleId = materialId;
        this.change = change;
        this.timeStamp = timeStamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer materialId) {
        this.articleId = materialId;
    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
        this.change = change;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }
}
