package com.example.verdantu.models;

public class FoodEmissions {
    int emissionId;
    String foodItems;
    String carbonEmissions;

    public FoodEmissions(int emissionId, String foodItems, String carbonEmissions) {
        this.emissionId = emissionId;
        this.foodItems = foodItems;
        this.carbonEmissions = carbonEmissions;
    }

    public FoodEmissions(String foodItems, String carbonEmissions) {
        this.foodItems = foodItems;
        this.carbonEmissions = carbonEmissions;
    }

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
