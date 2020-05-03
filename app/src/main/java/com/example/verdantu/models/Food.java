package com.example.verdantu.models;

import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("EmissionsId")
    private int emissionId;

    @SerializedName("Foods")
    private String foodName;

    @SerializedName("Emissions")
    float foodEmissions;

//    @SerializedName("CategoryId")
//    private int categoryId;


    public Food(int emissionId, String foodName, float foodEmissions) {
        this.emissionId = emissionId;
        this.foodName = foodName;
        this.foodEmissions = foodEmissions;
    }

    @Override
    public String toString() {
        return "Food{" +
                "emissionId=" + emissionId +
                ", foodName='" + foodName + '\'' +
                ", foodEmissions=" + foodEmissions +
                '}';
    }

    public int getEmissionId() {
        return emissionId;
    }

    public void setEmissionId(int emissionId) {
        this.emissionId = emissionId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getFoodEmissions() {
        return foodEmissions;
    }

    public void setFoodEmissions(float foodEmissions) {
        this.foodEmissions = foodEmissions;
    }
}
