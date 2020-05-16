package com.example.verdantu.modelinterfaces;

import com.example.verdantu.models.Consumption;
import com.example.verdantu.models.Food;
import com.example.verdantu.models.Recipe;
import com.example.verdantu.models.RecipeConsumption;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetService {

    @GET("api/food_Carbon_Emission")
    Call<List<Food>> getFood();

    @GET("api/food_Carbon_Emission_category/{categoryName}") // Add device ID
    Call<List<Food>> getFoodByCategory(@Path("categoryName") String categoryName);

    @GET("api/weekly_report/{deviceId}") // device id
    Call<List<Consumption>> getReportByWeek(@Path("deviceId") String deviceId);

    @GET("/api/weekly_category_wise/{deviceId}") // device id
    Call<List<Consumption>> getReportByCategory(@Path("deviceId") String deviceId);

    @GET("api/Recipe_Carbon_Emission")
    Call<List<Recipe>> getRecipeEmissions();

    @GET("api/Landing_page/{deviceId}") // device id
    Call<List<Consumption>> getEmissionForCurrentDay(@Path("deviceId") String deviceId);

    @GET("/api/view_food_added/{deviceId}") // device id
    Call<List<Consumption>> getConsumedRawFood(@Path("deviceId") String deviceId);

    @GET("/api/view_recipe_added/{deviceId}") // device id
    Call<List<RecipeConsumption>> getConsumedRecipe(@Path("deviceId") String deviceId);

    @GET("api/get_nutrition_food/{deviceId}") // device id
    Call<List<Food>> getNutritionReport(@Path("deviceId") String deviceId);

// get nutrition food needs device id
}
