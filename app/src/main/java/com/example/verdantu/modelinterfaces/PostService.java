package com.example.verdantu.modelinterfaces;

import com.example.verdantu.models.Food;
import com.example.verdantu.models.Recipe;
import com.example.verdantu.models.RecipeConsumption;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostService {


    @Headers("Content-Type: application/json")
    @POST("api/addEmission")
    Call<List<Food>> addConsumption(@Body RequestBody consumption);

    @Headers("Content-Type: application/json")
    @PUT("api/update_raw_food")
    Call<List<Food>> updateFoodConsumption(@Body RequestBody consumption);

    @Headers("Content-Type: application/json")
    @DELETE("api/delete_raw_food")
    Call<List<Food>> deleteFoodConsumption(@Body RequestBody consumption);

    @Headers("Content-Type: application/json")
    @POST("api/add_recipe_emission")
    Call<List<Recipe>> addRecipeConsumption(@Body RequestBody recipeConsumption);

    @Headers("Content-Type: application/json")
    @PUT("api/update_recipe_food")
    Call<List<Recipe>> updateRecipeConsumption(@Body RequestBody recipeConsumption);

    @DELETE("api/delete_recipe_food_new/{id}")
    Call<List<RecipeConsumption>> deleteRecipeConsumption(@Path("id") int id);
}
