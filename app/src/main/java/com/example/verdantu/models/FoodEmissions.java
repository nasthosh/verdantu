package com.example.verdantu.models;


/**
 *
 * This class is the model for the food emissions database
 * The attributes are based on the database schema
 *
 */

public class FoodEmissions {
    int emissionId;
    String foodItems;
    String carbonEmissions;

    // Constructor for the food emission class
    public FoodEmissions(int emissionId, String foodItems, String carbonEmissions) {
        this.emissionId = emissionId;
        this.foodItems = foodItems;
        this.carbonEmissions = carbonEmissions;
    }

    public FoodEmissions(String foodItems, String carbonEmissions) {
        this.foodItems = foodItems;
        this.carbonEmissions = carbonEmissions;
    }

    // Defining getter and setter methods
    public String getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(String foodItems) {
        this.foodItems = foodItems;
    }

    public String getCarbonEmissions() {
        return carbonEmissions;
    }

    public void setCarbonEmissions(String carbonEmissions) {
        this.carbonEmissions = carbonEmissions;
    }

    public int getEmissionId() {
        return emissionId;
    }

    public void setEmissionId(int emissionId) {
        this.emissionId = emissionId;
    }

    @Override
    public String toString() {
        return "FoodEmissions{" +
                "emissionId=" + emissionId +
                ", foodItems='" + foodItems + '\'' +
                ", carbonEmissions='" + carbonEmissions + '\'' +
                '}';
    }
}
