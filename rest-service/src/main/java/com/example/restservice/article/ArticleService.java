package com.example.restservice.article;

import com.example.restservice.material.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    public List<Article> getArticle(){
        return articleRepository.findAll();
    }

    public void addNewArticle(Article article) {
        Optional<Article> articleOptional = articleRepository
                .findMaterialByName(article.getName());
        if(articleOptional.isPresent()){
            throw new IllegalStateException("name taken");
        }
        //System.out.println(material);
        articleRepository.save(article);
    }

    public void deleteArticle(Integer articleId) {
        boolean exists = articleRepository.existsById(articleId);
        if(!exists){
            throw new IllegalStateException("article with id "+ articleId + " does not exist");
        }
        articleRepository.deleteById(articleId);
    }

    @Transactional
    public void updateArticle(Integer articleId, String name, Integer quantity, Double price, String materials) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()->new IllegalStateException("article with id " + articleId + " does not exist"));

        if(name != null &&
                name.length() > 0 &&
                !Objects.equals(article.getName(),name)){
            article.setName(name);
        }

        if(quantity != null &&
                !Objects.equals(article.getQuantity(),quantity)){
            article.setQuantity(quantity);
        }

        if(price != null &&
                !Objects.equals(article.getPrice(),price)){
            article.setPrice(price);
        }
        if(materials != null &&
                materials.length() > 0 &&
                !Objects.equals(article.getMaterials(),materials)){
            article.setMaterials(materials);
        }
    }
}
