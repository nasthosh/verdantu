package com.example.verdantu.modelinterfaces;

import com.example.verdantu.models.Food;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetService {

    @GET("api/food_Carbon_Emission")
    Call<List<Food>> getFood();
}
