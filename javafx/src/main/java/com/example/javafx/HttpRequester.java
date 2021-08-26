package com.example.javafx;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequester {
    private HttpURLConnection connection;

    public void deleteRequest (Integer id){
        try {
            URL url = new URL("http://localhost:8080/API/materials/"+id);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("DELETE");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            Integer status = connection.getResponseCode();
            System.out.println(status);
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
            String editLink = "name="+fixedName+"&quantity="+material.getQuantity()+"&price="+material.getPrice();
            URL url = new URL("http://localhost:8080/API/materials/"+material.getId()+"?"+editLink);
            System.out.println(url);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            Integer status = connection.getResponseCode();
            System.out.println(status);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public HttpRequester() {
    }
}
