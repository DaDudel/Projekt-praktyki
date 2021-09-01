package com.example.restservice.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Optional<Material> materialOptional = materialRepository
                .findMaterialByName(material.getName());
        if(materialOptional.isPresent()){
            throw new IllegalStateException("name taken");
        }
        //System.out.println(material);
        materialRepository.save(material);
    }

    public void deleteMaterial(Integer materialId) {
        //materialRepository.findById(materialId);
        boolean exists = materialRepository.existsById(materialId);
        if(!exists){
            throw new IllegalStateException("material with id "+ materialId + " does not exist");
        }
        materialRepository.deleteById(materialId);
    }

    @Transactional
    public void updateMaterial(Integer materialId, String name, Double quantity, Double price) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(()->new IllegalStateException("material with id " + materialId + " does not exist"));

        if(name != null &&
                name.length() > 0 &&
                !Objects.equals(material.getName(),name)){
            material.setName(name);
        }

        if(quantity != null &&
                !Objects.equals(material.getQuantity(),quantity)){
            material.setQuantity(quantity);
        }

        if(price != null &&
        !Objects.equals(material.getPrice(),price)){
            material.setPrice(price);
        }
    }
}
