package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GreetingList {
    List greetings = new ArrayList();

    public List getGreetings() {
        return greetings;
    }

    @GetMapping
    public List getList(){
        return this.getGreetings();
    }

    public void fillList(Greeting greeting){
        greetings.add(greeting);
    }

    public GreetingList() {
        greetings.add(new Greeting(5,"obiekt1"));
        greetings.add(new Greeting(10,"Obiekt2"));
    }

    public GreetingList(List greetings) {
        this.greetings = greetings;
    }
}
