package com.example.verdantu.rest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NewsAPI {

    private static final String API_KEY = "d014e9d416d041cea7297eeaf17bf954";
    public static String getNews() {
        //keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        try {
            url = new URL("http://newsapi.org/v2/everything?q=globalwarming&sortBy=publishedAt&apiKey=" + API_KEY );
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

}
