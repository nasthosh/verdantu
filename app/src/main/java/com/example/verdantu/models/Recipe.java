package com.example.verdantu.models;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("RecipeName")
    String recipeName;

    @SerializedName("EmissionsPerServe")
    float recipeEmission;

    @SerializedName("Protein")
    private float foodProtein;

    @SerializedName("Fat")
    private float foodFat;

    @SerializedName("Carbohydrate")
    private float foodCarbs;

    public Recipe(String recipeName, float recipeEmission) {
        this.recipeName = recipeName;
        this.recipeEmission = recipeEmission;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                ", recipeEmission=" + recipeEmission +
                ", foodProtein=" + foodProtein +
                ", foodFat=" + foodFat +
                ", foodCarbs=" + foodCarbs +
                '}';
    }

    public float getFoodProtein() {
        return foodProtein;
    }

    public void setFoodProtein(float foodProtein) {
        this.foodProtein = foodProtein;
    }

    public float getFoodFat() {
        return foodFat;
    }

    public void setFoodFat(float foodFat) {
        this.foodFat = foodFat;
    }

    public float getFoodCarbs() {
        return foodCarbs;
    }

    public void setFoodCarbs(float foodCarbs) {
        this.foodCarbs = foodCarbs;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public float getRecipeEmission() {
        return recipeEmission;
    }

    public void setRecipeEmission(float recipeEmission) {
        this.recipeEmission = recipeEmission;
    }
}