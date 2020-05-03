package com.example.verdantu.modelinterfaces;

import com.example.verdantu.models.Consumption;
import com.example.verdantu.models.Food;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostService {


//    Call<List<Food>> addConsumption(@Field("deviceID") String deviceID,
//                                    @Field("foods") String foods,
//                                    @Field("emission") float emission, @Field("categoryName") String categoryName);

    @Headers("Content-Type: application/json")
    @POST("api/addEmission")
    Call<List<Food>> addConsumption(@Body RequestBody consumption);
}
