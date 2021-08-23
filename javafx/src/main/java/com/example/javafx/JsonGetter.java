package com.example.javafx;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class JsonGetter {
    //private static final String POSTS_API_URL ="http://localhost:8080/greeting/?name=Maciej";
    private static final String POSTS_API_URL ="http://localhost:8080/materials";
    private MaterialList materialList = new MaterialList();

    public JsonGetter() {
    }

    public List getJson () throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept","application/json")
                .uri(URI.create(POSTS_API_URL))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        System.out.println();

        ObjectMapper mapper = new ObjectMapper();
        materialList = mapper.readValue(response.body(),new TypeReference<MaterialList>(){});
        //List<Material>greetings = mapper.readValue(response.body(),new TypeReference<List<Material>>(){});
        //greetings.forEach(System.out::println);
        //return greetings;

        return materialList.getMaterials();
    }
}
