package com.example.javafxclient.httprequesters;

import com.example.javafxclient.Material;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


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

        //ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapper = JsonMapper.builder().build();

        List<Material>materials = mapper.readValue(response.body(),new TypeReference<List<Material>>(){});
        //materials.forEach(System.out::println);
        return materials;
    }
}

