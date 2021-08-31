package com.example.javafx.httprequesters;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.example.javafx.Material;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class JsonGetter {
    private static final String POSTS_API_URL ="http://localhost:8080/API/materials";

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
        //System.out.println(response.body());
        //System.out.println();

        ObjectMapper mapper = new ObjectMapper();
        List<Material>materials = mapper.readValue(response.body(),new TypeReference<List<Material>>(){});
        //materials.forEach(System.out::println);
        return materials;
    }
}