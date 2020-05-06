package com.example.verdantu.models;

import com.google.gson.annotations.SerializedName;

public class Consumption {

    @SerializedName("deviceID")
    String deviceId;

    @SerializedName("foods")
    String foods;

    @SerializedName("Emission")
    float emission;

    @SerializedName("categoryName")
    String categoryName;

    @SerializedName("Date")
    String day;

    public Consumption(String deviceId, String foods, float emission, String categoryName, String day) {
        this.deviceId = deviceId;
        this.foods = foods;
        this.emission = emission;
        this.categoryName = categoryName;
        this.day = day;
    }

    public Consumption(String deviceId, String foods, float emission, String categoryName) {
        this.deviceId = deviceId;
        this.foods = foods;
        this.emission = emission;
        this.categoryName = categoryName;
    }

    public Consumption(float emission, String categoryName) {

        this.emission = emission;
        this.categoryName = categoryName;
    }


    @Override
    public String toString() {
        return "Consumption{" +
                "deviceId='" + deviceId + '\'' +
                ", foods='" + foods + '\'' +
                ", emission=" + emission +
                ", categoryName='" + categoryName + '\'' +
                ", now=" + day +
                '}';
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }

    public float getEmission() {
        return emission;
    }

    public void setEmission(float emission) {
        this.emission = emission;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
