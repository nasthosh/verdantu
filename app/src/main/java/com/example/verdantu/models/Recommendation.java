package com.example.verdantu.models;

import com.google.gson.annotations.SerializedName;

public class Recommendation {

    @SerializedName("YourFood")
    String consumedFood;

    @SerializedName("BetterFood")
    String recommendedFood;

    public Recommendation(String consumedFood, String recommendedFood) {
        this.consumedFood = consumedFood;
        this.recommendedFood = recommendedFood;
    }

    public String getConsumedFood() {
        return consumedFood;
    }

    public void setConsumedFood(String consumedFood) {
        this.consumedFood = consumedFood;
    }

    public String getRecommendedFood() {
        return recommendedFood;
    }

    public void setRecommendedFood(String recommendedFood) {
        this.recommendedFood = recommendedFood;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "consumedFood='" + consumedFood + '\'' +
                ", recommendedFood='" + recommendedFood + '\'' +
                '}';
    }
}
