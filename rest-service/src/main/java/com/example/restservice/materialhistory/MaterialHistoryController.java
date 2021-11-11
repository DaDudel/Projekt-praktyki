package com.example.restservice.materialhistory;

import com.example.restservice.material.Material;
import com.example.restservice.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "API/materialHistory")
public class MaterialHistoryController {
    private final MaterialHistoryService materialHistoryService;

    @Autowired
    public MaterialHistoryController(MaterialHistoryService materialHistoryService) {
        this.materialHistoryService = materialHistoryService;
    }

    @GetMapping
    public List<MaterialHistory> getMateriaHistory() {

        return materialHistoryService.getMaterialHistory();
    }

    @PostMapping
    public void registerNewMaterialHistory(@RequestBody MaterialHistory materialHistory){
        materialHistoryService.addNewMaterialHistory(materialHistory);
    }

    @DeleteMapping(path = "{materialHistoryId}")
    public void deleteMaterialHistory(@PathVariable("materialHistoryId") Integer materialHistoryId){
        materialHistoryService.deleteMaterialHistory(materialHistoryId);
    }

    @PutMapping(path = "{materialHistoryId}")
    public void updateMaterialHistory(
            @PathVariable("materialIdHistory") Integer materialHistoryId,
            @RequestParam(required = false) Integer materialId,
            @RequestParam(required = false) Double change,
            @RequestParam(required = false)LocalDate timeStamp){
        materialHistoryService.updateMaterialHistory(materialHistoryId,materialId, change,timeStamp);
    }

}
