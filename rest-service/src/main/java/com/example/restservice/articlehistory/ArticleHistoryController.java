package com.example.restservice.articlehistory;

import com.example.restservice.materialhistory.MaterialHistory;
import com.example.restservice.materialhistory.MaterialHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "API/articleHistory")
public class ArticleHistoryController {
    private final ArticleHistoryService articleHistoryService;

    @Autowired
    public ArticleHistoryController(ArticleHistoryService articleHistoryService) {
        this.articleHistoryService = articleHistoryService;
    }

    @GetMapping
    public List<ArticleHistory> getArticleHistory() {

        return articleHistoryService.getArticleHistory();
    }

    @PostMapping
    public void registerNewArticleHistory(@RequestBody ArticleHistory articleHistory){
        articleHistoryService.addNewArticleHistory(articleHistory);
    }

    @DeleteMapping(path = "{articleHistoryId}")
    public void deleteArticleHistory(@PathVariable("articleHistoryId") Integer articleHistoryId){
        articleHistoryService.deleteArticleHistory(articleHistoryId);
    }

    @PutMapping(path = "{articleHistoryId}")
    public void updateArticleHistory(
            @PathVariable("articleIdHistory") Integer articleHistoryId,
            @RequestParam(required = false) Integer articleId,
            @RequestParam(required = false) Integer change,
            @RequestParam(required = false) LocalDate timeStamp){
        articleHistoryService.updateArticleHistory(articleHistoryId,articleId, change,timeStamp);
    }

}
