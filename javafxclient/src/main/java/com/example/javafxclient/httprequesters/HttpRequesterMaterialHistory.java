package com.example.javafxclient.httprequesters;

import com.example.javafxclient.Article;
import com.example.javafxclient.MaterialHistory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpRequesterMaterialHistory {
    private HttpURLConnection connection;
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

    public void addRequest (MaterialHistory materialHistory){
        try {
            URL url = new URL("http://localhost:8080/API/materialHistory");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json; utf-8");
            connection.setDoOutput(true);
            String jsonInputString ="{\"materialId\": \""
                    + materialHistory.getMaterialId()
                    +"\", \"change\": \""
                    + materialHistory.getChange()
                    + "\", \"timeStamp\": \""
                    + materialHistory.getTimeStamp()
                    + "\"}";
            try(OutputStream os = connection.getOutputStream()){
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input,0,input.length);
            }
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            Integer status = connection.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
