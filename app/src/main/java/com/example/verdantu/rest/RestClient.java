package com.example.verdantu.rest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestClient {

    private static String BASE_URL = "http://52.63.154.13:3000/";

    public static String getItemsList() {
        final String methodPath = "api/food_Carbon_Emission";
        URL url = null;
        HttpURLConnection conn = null;
        String carbonEmissions = "";
        try
        {
            url = new URL(BASE_URL + methodPath);
            System.out.println("URL Carbon Emission : " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                carbonEmissions += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        System.out.println("Carbon Emissions :  " + carbonEmissions);

        return carbonEmissions;

    }
}
