package com.example.restservice.material;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MaterialConfig {
    @Bean
    CommandLineRunner commandLineRunner(MaterialRepository repository){
        return args -> {
            Material gumka = new Material(
                    "Gumka",
                    20,
                    5.00
            );
            Material pasek = new Material(
                    "Pasek",
                    15,
                    7.50
            );
            Material koronka= new Material(
                    "Koronka",
                    40,
                    3.50
            );
            List<Material> tempList = new ArrayList<Material>();
            tempList.add(gumka);
            tempList.add(pasek);
            tempList.add(koronka);
            repository.saveAll(
                    tempList
            );
        };
    }
}
