package com.example.verdantu.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.verdantu.R;
import com.example.verdantu.modelinterfaces.PostService;
import com.example.verdantu.models.Consumption;
import com.example.verdantu.models.Food;
import com.example.verdantu.rest.RestClient;
import com.example.verdantu.rest.RetrofitClientInstance;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodItems extends AppCompatActivity {
    String foodName;
    String foodCarbonFootPrints;
    TextView foodItemName;
    TextView foodItemCarbonEmission;
    TextView totalFoodEmissions;
    EditText foodQuantity;
    String foodQty;
    Button showCarbonFootPrint;
    String deviceId;
    String foodCategory;
    int rgCategorySelected;
    Button addFood;
    String finalTotalEmissions;
    String userId = "10";

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_items);
        deviceId = getDeviceId(getApplicationContext());
        System.out.println("Device ID  : " + deviceId);
        Intent intent = getIntent();
        foodName = intent.getStringExtra("foodItems");
        foodCarbonFootPrints = intent.getStringExtra("carbonEmissions");
        foodCategory = intent.getStringExtra("checkedFoodCategory");
        System.out.println("Category ID  : " + foodCategory);
        foodItemName = findViewById(R.id.textView_FoodName);
        foodItemName.setText("Selected Food : " + foodName);
        foodItemCarbonEmission = findViewById(R.id.textView_FoodEmissions);
        foodItemCarbonEmission.setText("Emission : " + foodCarbonFootPrints + " Kg/Co2");
        foodQuantity = findViewById(R.id.editTextView_Qty);
        showCarbonFootPrint = findViewById(R.id.btn_ShowCarbonFootPrint);
        addFood = findViewById(R.id.btn_Add);
        showCarbonFootPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                foodQty = foodQuantity.getText().toString();
                float qty = Float.parseFloat(foodQty);
                float convertKgToGram = qty/1000;
                float totalCarbonEmissions = convertKgToGram * (Float.parseFloat(foodCarbonFootPrints));
                totalFoodEmissions = findViewById(R.id.textView_TotalFoodEmissions);
                final float roundDecimalTotalFoodEmission = (float) ((double) Math.round(totalCarbonEmissions * 100000d) / 100000d);
                finalTotalEmissions = String.valueOf(roundDecimalTotalFoodEmission);
                totalFoodEmissions.setText("Total Emissions : " + finalTotalEmissions +  " Kg/Co2");
                System.out.println("Emission : " + foodName);
                System.out.println("Name : " + foodName + "Emission" + finalTotalEmissions + "Category"+ foodCategory);
                addFood.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        PostService service = RetrofitClientInstance.getRetrofitInstance().create(PostService.class);
                        Gson gson = new Gson();
                        ArrayList<Consumption> consumedData = new ArrayList<>();
                        Consumption addConsumed = new Consumption(deviceId, foodName, roundDecimalTotalFoodEmission, foodCategory);
                        consumedData.add(addConsumed);
                        RequestBody postData = RequestBody.create(MediaType.parse("application/json"),gson.toJson(consumedData));
                        service.addConsumption(postData).enqueue(new Callback<List<Food>>() {
                            @Override
                            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        Log.i("onSuccess", response.body().toString());
                                    } else {
                                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Food>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                                System.out.println(" Throwable error : " + t);
                            }


                        });



                    }
                });
            }
        });
    }

    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }
}
