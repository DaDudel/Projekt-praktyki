package com.example.restservice.articlehistory;

import com.example.restservice.materialhistory.MaterialHistory;
import com.example.restservice.materialhistory.MaterialHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleHistoryService {
    private final ArticleHistoryRepository articleHistoryRepository;

    @Autowired
    public ArticleHistoryService(ArticleHistoryRepository articleHistoryRepository) {
        this.articleHistoryRepository = articleHistoryRepository;
    }

    public List<ArticleHistory> getArticleHistory() {
        return articleHistoryRepository.findAll();
    }

    public void addNewArticleHistory(ArticleHistory articleHistory) {
        articleHistoryRepository.save(articleHistory);
    }

    public void deleteArticleHistory(Integer articleHistoryId) {
        boolean exists = articleHistoryRepository.existsById(articleHistoryId);
        if(!exists){
            throw new IllegalStateException("articleHistory with id "+ articleHistoryId + " does not exist");
        }
        articleHistoryRepository.deleteById(articleHistoryId);
    }

    @Transactional
    public void updateArticleHistory(Integer articleHistoryId, Integer articleId, Integer change, LocalDate timeStamp) {
        ArticleHistory articleHistory = articleHistoryRepository.findById(articleHistoryId)
                .orElseThrow(()->new IllegalStateException("articlelHistory with id " + articleHistoryId + " does not exist"));

        if(articleId != null &&
                !Objects.equals(articleHistory.getArticleId(),articleId)){
            articleHistory.setArticleId(articleId);
        }

        if(change != null &&
                !Objects.equals(articleHistory.getChange(),change)){
            articleHistory.setChange(change);
        }

        if(timeStamp != null &&
                !Objects.equals(articleHistory.getTimeStamp(),timeStamp)){
            articleHistory.setTimeStamp(timeStamp);
        }
    }
}
