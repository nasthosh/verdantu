package com.example.verdantu.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.verdantu.R;
import com.example.verdantu.modelinterfaces.PostService;
import com.example.verdantu.models.RecipeConsumption;
import com.example.verdantu.rest.RetrofitClientInstance;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRecipe extends AppCompatActivity {

    private String deviceId;
    private String recipeName;
    private String recipeCarbonFootPrint;
    private TextView recipeItemName;
    private TextView recipeEmissions;
    private EditText recipeServingAmount;
    private Button showCarbonEmissions;
    private Button addRecipe;
    private Button cancelRecipeAddition;
    private float servingAmount;
    private Boolean isValueShown = false;
    private TextView totalRecipeEmissions;
    private float totalEmissions;
    private float roundDecimalRecipeEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        deviceId = DeviceData.getDeviceId(getApplicationContext());
        Intent intent = getIntent();
        recipeName = intent.getStringExtra("recipe");
        recipeCarbonFootPrint = intent.getStringExtra("recipeCarbonEmissions");
        recipeItemName = findViewById(R.id.textView_RecipeName);
        recipeItemName.setText("Selected Recipe : " + recipeName);
        recipeEmissions = findViewById(R.id.textView_RecipeEmissions);
        recipeEmissions.setText("Emission : " + recipeCarbonFootPrint + "/serve");
        recipeServingAmount = findViewById(R.id.editTextView_Serving);
        showCarbonEmissions = findViewById(R.id.btn_ShowRecipeCarbonFootPrint);
        addRecipe = findViewById(R.id.btn_AddRecipe);
        cancelRecipeAddition = findViewById(R.id.btn_CancelRecipeAdd);
        showCarbonEmissions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRecipeCarbonEmissions();
            }
        });

        addRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addRecipe();
            }
        });

        cancelRecipeAddition.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                cancelRecipe();
            }
        });
    }

    public void showRecipeCarbonEmissions()
    {
        servingAmount = Float.parseFloat(recipeServingAmount.getText().toString());
        if (servingAmount > 0){
            totalRecipeEmissions = findViewById(R.id.textView_TotalRecipeEmissions);
            totalEmissions = servingAmount * Float.parseFloat(recipeCarbonFootPrint);
            roundDecimalRecipeEmission = (float) ((double) Math.round(totalEmissions * 100000d) / 100000d);
            totalRecipeEmissions.setText("Total Emissions : " + String.valueOf(roundDecimalRecipeEmission) + " KgCo2/100g");
            recipeServingAmount.setText("");
            isValueShown = true;

        }else
            Toast.makeText(getApplicationContext(), "Please enter the quantity", Toast.LENGTH_SHORT).show();

    }

    public void addRecipe()
    {
        if(isValueShown){
            PostService service = RetrofitClientInstance.getRetrofitInstance().create(PostService.class);
            Gson gson = new Gson();
            ArrayList<RecipeConsumption> recipeConsumption = new ArrayList<>();
            System.out.println("Emission value from ad food itesm : " + recipeCarbonFootPrint);
            RecipeConsumption addRecipeConsumption = new RecipeConsumption(deviceId, recipeName, Float.parseFloat(recipeCarbonFootPrint), servingAmount);
            recipeConsumption.add(addRecipeConsumption);
            RequestBody postData = RequestBody.create(MediaType.parse("application/json"), gson.toJson(recipeConsumption));
            System.out.println(" Reaching here !!!!!!");
            service.addRecipeConsumption(postData).enqueue(new Callback<List<RecipeConsumption>>() {
                @Override
                public void onResponse(Call<List<RecipeConsumption>> call, Response<List<RecipeConsumption>> response) {
                    System.out.println("Body response " + response.body());
                    if (response.isSuccessful()) {
                        System.out.println(" Response is successfulllllllll   ");
                        if (response.body() != null) {
                            Toast.makeText(getApplicationContext(), "Recipe Added", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<RecipeConsumption>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    System.out.println(" Throwable error : " + t);
                }
            });
        }
        else  {
            Toast.makeText(getApplicationContext(), "Please enter the quantity", Toast.LENGTH_SHORT).show();
        }

    }

    public void cancelRecipe()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(AddRecipe.this).setIcon(android.R.drawable.ic_dialog_map)
                .setTitle("Exit?").setMessage("Are you sure to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        finish();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        Toast.makeText(AddRecipe.this,"Back to Add",Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }
}
