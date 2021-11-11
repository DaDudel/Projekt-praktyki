package com.example.restservice.materialhistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialHistoryRepository extends JpaRepository<MaterialHistory,Integer> {

//    @Query("SELECT mh FROM MaterialHistory mh WHERE mh.id = ?1")
//    Optional<MaterialHistory> findMaterialHistoriesById(Integer id);
}
