package com.example.restservice.materialhistory;

import com.example.restservice.material.Material;
import com.example.restservice.material.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MaterialHistoryService {
    private final MaterialHistoryRepository materialHistoryRepository;

    @Autowired
    public MaterialHistoryService(MaterialHistoryRepository materialHistoryRepository) {
        this.materialHistoryRepository = materialHistoryRepository;
    }

    public List<MaterialHistory> getMaterialHistory() {
        return materialHistoryRepository.findAll();
    }

    public void addNewMaterialHistory(MaterialHistory materialHistory) {
        materialHistoryRepository.save(materialHistory);
    }

    public void deleteMaterialHistory(Integer materialHistoryId) {
        boolean exists = materialHistoryRepository.existsById(materialHistoryId);
        if(!exists){
            throw new IllegalStateException("materialHistory with id "+ materialHistoryId + " does not exist");
        }
        materialHistoryRepository.deleteById(materialHistoryId);
    }

    @Transactional
    public void updateMaterialHistory(Integer materialHistoryId, Integer materialId, Double change, LocalDate timeStamp) {
        MaterialHistory materialHistory = materialHistoryRepository.findById(materialHistoryId)
                .orElseThrow(()->new IllegalStateException("materialHistory with id " + materialHistoryId + " does not exist"));

        if(materialId != null &&
                !Objects.equals(materialHistory.getMaterialId(),materialId)){
            materialHistory.setMaterialId(materialId);
        }

        if(change != null &&
                !Objects.equals(materialHistory.getChange(),change)){
            materialHistory.setChange(change);
        }

        if(timeStamp != null &&
                !Objects.equals(materialHistory.getTimeStamp(),timeStamp)){
            materialHistory.setTimeStamp(timeStamp);
        }
    }
}
