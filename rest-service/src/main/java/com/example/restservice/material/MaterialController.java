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


    @GetMapping(path = "/getmaterial")
    public List<Material> getMaterial() {

        return materialService.getMaterial();
    }

    @PostMapping
    public void registerNewMaterial(@RequestBody Material material){
        materialService.addNewMaterial(material);
    }

//    @PostMapping(path="/testmapping")
//    public void testMapping(@RequestBody String string){
//        System.out.println(string);
//    }
}
