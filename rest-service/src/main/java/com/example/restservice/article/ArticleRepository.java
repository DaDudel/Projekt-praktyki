package com.example.restservice.article;

import com.example.restservice.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
    @Query("SELECT a FROM Article a WHERE a.name = ?1")
    Optional<Article> findMaterialByName(String name);
}
