package com.example.restservice.articlehistory;

import com.example.restservice.materialhistory.MaterialHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleHistoryRepository extends JpaRepository<ArticleHistory,Integer> {

//    @Query("SELECT mh FROM MaterialHistory mh WHERE mh.id = ?1")
//    Optional<MaterialHistory> findMaterialHistoriesById(Integer id);
}
