package com.example.javafxclient.httprequesters;

import com.example.javafxclient.Functions;
import com.example.javafxclient.Orders;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

public class HttpRequesterOrders {
    private HttpURLConnection connection;
    private Functions functions = new Functions();

    public HttpRequesterOrders() {
    }

    public List getRequest() throws  IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept","application/json")
                .uri(URI.create("http://localhost:8080/API/orders"))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        List<Orders>orders = mapper.readValue(response.body(),new TypeReference<List<Orders>>(){});
        return orders;
    }

    public void deleteRequest (Integer id){
        try {
            URL url = new URL("http://localhost:8080/API/orders/"+id);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("DELETE");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            Integer status = connection.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addRequest (Orders order){
        try {
            URL url = new URL("http://localhost:8080/API/orders");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json; utf-8");
            connection.setDoOutput(true);
            String jsonInputString ="{\"transId\": \""
                    + order.getTransId()
                    +"\", \"client\": \""
                    + order.getClient()
                    + "\", \"bruttoPrice\": \""
                    + order.getBruttoPrice()
                    + "\", \"nettoPrice\": \""
                    + order.getNettoPrice()
                    + "\", \"discount\": \""
                    + order.getDiscount()
                    + "\", \"items\": \""
                    + order.getItems()
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

    public void editRequest(Orders order){
        try {
            String fixedClient = order.getClient();
            fixedClient = fixedClient.replaceAll(" ","%20");
            fixedClient = functions.removePolish(fixedClient);
            String editLink = "transId="+order.getTransId()+"&client="+fixedClient+"&bruttoPrice="+order.getBruttoPrice()
                    +"&nettoPrice="+order.getNettoPrice()+"&discount="+order.getDiscount()+"&items="+order.getItems();
            URL url = new URL("http://localhost:8080/API/orders/"+order.getId()+"?"+editLink);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");
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
