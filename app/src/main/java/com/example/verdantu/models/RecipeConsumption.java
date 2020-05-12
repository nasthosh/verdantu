package com.example.verdantu.models;

import com.google.gson.annotations.SerializedName;

public class RecipeConsumption {

    @SerializedName("RecipeName")
    String recipeName;

    @SerializedName("totalEmission")
    float recipeEmission;

    @SerializedName("deviceID")
    String deviceId;

    @SerializedName("serves")
    float servingAmount;

    @SerializedName("ObjectID")
    int objId;

    public RecipeConsumption(String recipeName, float recipeEmission) {
        this.recipeName = recipeName;
        this.recipeEmission = recipeEmission;
    }

    public RecipeConsumption(String deviceId, String recipeName,  float recipeEmission, float servingAmount) {
        this.recipeName = recipeName;
        this.deviceId = deviceId;
        this.recipeEmission = recipeEmission;
        this.servingAmount = servingAmount;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                ", recipeEmission=" + recipeEmission +
                ", deviceId='" + deviceId + '\'' +
                ", servingAmount=" + servingAmount +
                '}';
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public float getServingAmount() {
        return servingAmount;
    }

    public void setServingAmount(float servingAmount) {
        this.servingAmount = servingAmount;
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