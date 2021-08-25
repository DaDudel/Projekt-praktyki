package com.example.restservice.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "API/materials")
public class MaterialController {
    //MaterialList materialList = new MaterialList();
    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }


    @GetMapping
    public List<Material> getMaterial() {

        return materialService.getMaterial();
    }

    @PostMapping
    public void registerNewMaterial(@RequestBody Material material){
        materialService.addNewMaterial(material);
    }

    @DeleteMapping(path = "{materialId}")
    public void deleteMaterial(@PathVariable("materialId") Integer materialId){
        materialService.deleteMaterial(materialId);
    }

    @PutMapping(path = "{materialId}")
    public void updateMaterial(
            @PathVariable("materialId") Integer materialId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Double price){
        materialService.updateMaterial(materialId,name, quantity,price);
    }
}
