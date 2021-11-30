package com.example.javafxclient.httprequesters;

import com.example.javafxclient.Article;
import com.example.javafxclient.MaterialHistory;
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

public class HttpRequesterMaterialHistory {
    public List getRequest() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept","application/json")
                .uri(URI.create("http://localhost:8080/API/materialHistory"))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        //mapper.registerModule(new JavaTime);
        //System.out.println(response.body());
        List<MaterialHistory>materialHistories = mapper.readValue(response.body(),new TypeReference<List<MaterialHistory>>(){});
        //System.out.println(materialHistories);
        return materialHistories;
    }
}
