package com.example.javafxclient.httprequesters;



import com.example.javafxclient.Functions;
import com.example.javafxclient.Material;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequesterMaterial {
    private HttpURLConnection connection;
    private Functions functions = new Functions();

    public void deleteRequest (Integer id){
        try {
            URL url = new URL("http://localhost:8080/API/materials/"+id);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("DELETE");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            Integer status = connection.getResponseCode();
            //System.out.println(status);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void editRequest(Material material){
        try {
            String fixedName = material.getName();
            fixedName = fixedName.replaceAll(" ","%20");
            fixedName = functions.removePolish(fixedName);
            System.out.println(fixedName);
            String editLink = "name="+fixedName+"&quantity="+material.getQuantity()+"&price="+material.getPrice();
            URL url = new URL("http://localhost:8080/API/materials/"+material.getId()+"?"+editLink);
            //System.out.println(url);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            Integer status = connection.getResponseCode();
            //System.out.println(status);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addRequest (Material material){
        try {
            URL url = new URL("http://localhost:8080/API/materials");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json; utf-8");
            connection.setDoOutput(true);
            String jsonInputString ="{\"name\": \""
                    + material.getName()
                    +"\", \"quantity\": \""
                    + material.getQuantity()
                    + "\", \"price\": \"" + material.getPrice() + "\"}";
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

    public HttpRequesterMaterial() {
    }
}

