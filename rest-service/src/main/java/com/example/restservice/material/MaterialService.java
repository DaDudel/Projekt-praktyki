package com.example.restservice.material;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {

    public MaterialService() {
    }

    public List<Material> getMaterial() {

        List<Material>templist = new ArrayList<Material>();
        templist.add(new Material(1,"Gumka",20,5.00));
        templist.add(new Material(2,"Pasek",15,7.50));
        templist.add(new Material(3,"Koronka",40,3.00));
        return templist;
    }
}
