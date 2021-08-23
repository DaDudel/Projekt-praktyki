package com.example.restservice.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaterialListController {
    MaterialList materialList = new MaterialList();
    private final MaterialListService materialListService;

    @Autowired
    public MaterialListController(MaterialListService materialListService) {
        this.materialListService = materialListService;
    }


    @GetMapping("/API/materials")
    public MaterialList getMaterialList() {
        return materialListService.getMaterialList();
    }
}
