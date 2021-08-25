package com.example.restservice.material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    @Query("SELECT m FROM Material m WHERE m.name = ?1")
    Optional<Material> findMaterialByName(String name);
}
