package com.example.restservice.material;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaterialListController {
    MaterialList materialList = new MaterialList();
//    private final MaterialListService materialListService;
//
//    public MaterialListController(MaterialListService materialListService) {
//        this.materialListService = materialListService;
//    }


    @GetMapping("/materials")
    public MaterialList getMaterialList() {
        return materialList;
    }
}
