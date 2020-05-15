package com.example.verdantu.models;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("RecipeName")
    String recipeName;

    @SerializedName("EmissionsPerServe")
    float recipeEmission;

    @SerializedName("RecipeProteinPerServe")
    private float recipeProtein;

    @SerializedName("RecipeFatPerServe")
    private float recipeFat;

    @SerializedName("RecipeCarbsPerServe")
    private float recipeCarbs;

    public Recipe(String recipeName, float recipeEmission) {
        this.recipeName = recipeName;
        this.recipeEmission = recipeEmission;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                ", recipeEmission=" + recipeEmission +
                ", foodProtein=" + recipeProtein +
                ", foodFat=" + recipeFat +
                ", foodCarbs=" + recipeCarbs +
                '}';
    }

    public float getRecipeProtein() {
        return recipeProtein;
    }

    public void setRecipeProtein(float recipeProtein) {
        this.recipeProtein = recipeProtein;
    }

    public float getRecipeFat() {
        return recipeFat;
    }

    public void setRecipeFat(float recipeFat) {
        this.recipeFat = recipeFat;
    }

    public float getRecipeCarbs() {
        return recipeCarbs;
    }

    public void setRecipeCarbs(float recipeCarbs) {
        this.recipeCarbs = recipeCarbs;
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