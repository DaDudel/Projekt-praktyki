package com.example.javafxclient.httprequesters;



import com.example.javafxclient.*;
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
import java.time.LocalDate;
import java.util.List;

public class HttpRequesterArticle {
    private HttpURLConnection connection;
    private Functions functions = new Functions();
    private HttpRequesterArticleHistory httpRequesterArticleHistory = new HttpRequesterArticleHistory();

    public HttpRequesterArticle() {
    }

    public void deleteRequest (Integer id){
        try {
            URL url = new URL("http://localhost:8080/API/articles/"+id);
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

    public void addRequest (Article article){
        try {
            URL url = new URL("http://localhost:8080/API/articles");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json; utf-8");
            connection.setDoOutput(true);
            String jsonInputString ="{\"name\": \""
                    + article.getName()
                    +"\", \"quantity\": \""
                    + article.getQuantity()
                    + "\", \"price\": \""
                    + article.getPrice()
                    + "\", \"materials\": \""
                    + article.getMaterials()
                    + "\", \"workPrice\": \""
                    + article.getWorkPrice()
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

    public void editRequest(Article article){
        try {
            String fixedName = article.getName();
            fixedName = fixedName.replaceAll(" ","%20");
            fixedName = functions.removePolish(fixedName);
            String editLink = "name="+fixedName+"&quantity="+article.getQuantity()+"&price="+article.getPrice()+"&materials="+article.getMaterials()+"&workPrice="+article.getWorkPrice();
            URL url = new URL("http://localhost:8080/API/articles/"+article.getId()+"?"+editLink);
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

    public List getRequest() throws  IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept","application/json")
                .uri(URI.create("http://localhost:8080/API/articles"))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        List<Article>articles = mapper.readValue(response.body(),new TypeReference<List<Article>>(){});
        return articles;
    }

    public void doHistory(Article oldArticle, Article newArticle){
        ArticleHistory articleHistory = new ArticleHistory(oldArticle.getId(),
                newArticle.getQuantity()- oldArticle.getQuantity(),
                LocalDate.now());
        if(articleHistory.getChange()==0){
            return;
        }
        httpRequesterArticleHistory.addRequest(articleHistory);
    }


}

