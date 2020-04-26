package com.example.verdantu.rest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * This class is used to connected to the server using the REST API
 * Servers are connected using the HTTP Protocol
 *
 */
public class RestClient {

    private static String BASE_URL = "http://52.63.154.13:3000/"; // Mapping to instance IP

    public static String getItemsList() {
        final String methodPath = "api/food_Carbon_Emission"; // Calling the server API
        URL url = null;
        HttpURLConnection conn = null;
        String carbonEmissions = "";
        try
        {
            url = new URL(BASE_URL + methodPath);
            System.out.println("URL Carbon Emission : " + url);

            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            conn = (HttpURLConnection) url.openConnection();

            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            //set the connection method to GET
            conn.setRequestMethod("GET");

            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());

            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                carbonEmissions += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return carbonEmissions;

    }
}
