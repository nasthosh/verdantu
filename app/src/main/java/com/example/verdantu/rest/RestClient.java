package com.example.verdantu.rest;

import android.util.Log;

import com.example.verdantu.models.Consumption;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
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

    public static String getFoodsByCategory(String category) {
        final String methodPath = "api/food_Carbon_Emission_" + category; // Calling the server API
        URL url = null;
        HttpURLConnection conn = null;
        String categoryEmissions = "";
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
                categoryEmissions += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return categoryEmissions;

    }

    public static void addConsFood(String food, String foodEmission, String foodCategory){
        URL url = null;
        HttpURLConnection conn = null;
        String response = "";
        final String methodPath="api/addConsumedNew/";
        try {
            float foodEmissionInFloat = Float.parseFloat(foodEmission);

            Map<String,Object> params = new LinkedHashMap<>();
            //params.put("userId", userId);
            params.put("foodName", food);
            params.put("foodEmission", foodEmissionInFloat);
            params.put("foodCategory", foodCategory);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                System.out.println("postData:"+postData);
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            url = new URL(BASE_URL + methodPath);
            System.out.print("The URL is "+url);
            System.out.print("The URL is "+url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            response = sb.toString();
            System.out.print(Log.d("error",response));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }
    }

    public static void addFoodEmission(String deviceId, String food, String foodEmission, String foodCategory){
        URL url = null;
        HttpURLConnection conn = null;
        String response = "";
        final String methodPath="api/addEmission/";
        try {
            float foodEmissionInFloat = Float.parseFloat(foodEmission);

            Map<String,Object> params = new LinkedHashMap<>();
            params.put("userId", deviceId);
            params.put("foodName", food);
            params.put("foodCarbonEmission", foodEmissionInFloat);
            params.put("foodCategory", foodCategory);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                System.out.println("postData:"+postData);
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            url = new URL(BASE_URL + methodPath);
            System.out.print("The URL is "+url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            response = sb.toString();
            System.out.print(Log.d("error",response));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }
    }

    public static void addConsumedFood(Consumption consumed) {
        //initialise
        final String methodPath = "api/addEmission/";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringappuserJson=gson.toJson(consumed);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringappuserJson.getBytes().length);
//add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
//Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringappuserJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }
    }

    public static void testData(String dataCol)
    {
        final String methodPath = "api/testData/";
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(BASE_URL + methodPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            conn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setDoOutput(true);
        try {
            conn.getOutputStream().write(dataCol.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
                conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
