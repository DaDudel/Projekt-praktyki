package com.example.restservice.material;

import com.example.restservice.material.Material;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class MaterialList {
    List materials = new ArrayList();

    public MaterialList() {
        materials.add(new Material(1,"Gumka",20,5.00));
        materials.add(new Material(2,"Pasek",15,7.50));
        materials.add(new Material(3,"Koronka",40,3.00));
    }

    public List getMaterials() {
        return materials;
    }


}
