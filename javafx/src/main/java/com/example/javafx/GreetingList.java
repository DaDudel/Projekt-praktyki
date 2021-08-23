package com.example.javafx;

import java.util.ArrayList;
import java.util.List;

public class GreetingList {
    List greetings = new ArrayList();

    public List getGreetings() {
        return greetings;
    }

    public void setGreetings(List greetings) {
        this.greetings = greetings;
    }

    public GreetingList(List greetings) {
        this.greetings = greetings;
    }

    public GreetingList() {

    }
}
