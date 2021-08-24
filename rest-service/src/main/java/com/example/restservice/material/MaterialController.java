package com.example.restservice.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MaterialController {
    //MaterialList materialList = new MaterialList();
    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }


    @GetMapping("/API/materials")
    public List<Material> getMaterial() {

        List<Material>templist = new ArrayList<Material>();
        templist.add(new Material(1,"Gumka",20,5.00));
        templist.add(new Material(2,"Pasek",15,7.50));
        templist.add(new Material(3,"Koronka",40,3.00));
        return templist;
    }
}
