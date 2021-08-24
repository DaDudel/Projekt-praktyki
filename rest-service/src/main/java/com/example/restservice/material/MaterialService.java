package com.example.restservice.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<Material> getMaterial() {

//        List<Material>templist = new ArrayList<Material>();
//        templist.add(new Material(1,"Gumka",20,5.00));
//        templist.add(new Material(2,"Pasek",15,7.50));
//        templist.add(new Material(3,"Koronka",40,3.00));
//        return templist;

        return materialRepository.findAll();
    }

    public void addNewMaterial(Material material) {
        System.out.println(material);
    }
}
