package com.example.verdantu.models;

public class Consumption {
    String userId;
    String foods;
    float emission;
    String categoryName;

    public Consumption(String userId, String foods, float emission, String categoryName) {
        this.userId = userId;
        this.foods = foods;
        this.emission = emission;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "userId=" + userId +
                ", foods='" + foods + '\'' +
                ", emission=" + emission +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
