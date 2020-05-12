package com.example.verdantu.models;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("RecipeName")
    String recipeName;

    @SerializedName("EmissionsPerServe")
    float recipeEmission;

    public Recipe(String recipeName, float recipeEmission) {
        this.recipeName = recipeName;
        this.recipeEmission = recipeEmission;
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