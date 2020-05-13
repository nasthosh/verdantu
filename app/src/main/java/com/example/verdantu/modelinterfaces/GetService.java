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

    @GET("api/food_Carbon_Emission_category/{categoryName}")
    Call<List<Food>> getFoodByCategory(@Path("categoryName") String categoryName);

    @GET("api/weekly_report")
    Call<List<Consumption>> getReportByWeek();

    @GET("/api/weekly_category_wise")
    Call<List<Consumption>> getReportByCategory();

    @GET("api/Recipe_Carbon_Emission")
    Call<List<Recipe>> getRecipeEmissions();

    @GET("api/Landing_page")
    Call<List<Consumption>> getEmissionForCurrentDay();

    @GET("api/view_food_added")
    Call<List<Consumption>> getConsumedRawFood();

    @GET("api/view_recipe_added")
    Call<List<RecipeConsumption>> getConsumedRecipe();

}
