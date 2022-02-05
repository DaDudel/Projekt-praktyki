package com.example.javafxclient;



import java.time.LocalDate;


public class ArticleHistory {
    private Integer id;
    private Integer articleId;
    private Integer change;
    private LocalDate timeStamp;

    public ArticleHistory() {
    }

    public ArticleHistory(Integer articleId, Integer change, LocalDate timeStamp) {
        this.articleId = articleId;
        this.change = change;
        this.timeStamp = timeStamp;
    }

    public ArticleHistory(Integer id, Integer articleId, Integer change, LocalDate timeStamp) {
        this.id = id;
        this.articleId = articleId;
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

    @Override
    public String toString() {
        return
                "materialId=" + articleId +
                        ", change=" + change +
                        ", timeStamp=" + timeStamp;
    }
}

