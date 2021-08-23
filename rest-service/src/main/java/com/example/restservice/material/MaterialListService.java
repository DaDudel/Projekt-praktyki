package com.example.restservice.material;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MaterialListService {
    MaterialList materialList = new MaterialList();

    public MaterialListService() {
    }

    public MaterialList getMaterialList() {
        return materialList;
    }
}
