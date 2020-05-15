package com.example.verdantu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.verdantu.R;
import com.example.verdantu.modelinterfaces.PostService;
import com.example.verdantu.models.Consumption;
import com.example.verdantu.models.Food;
import com.example.verdantu.models.Recipe;
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

public class EditActivity extends AppCompatActivity {
String foodName;
String recipeName;
String foodCarbonFootPrint;
String recipeCarbonFootPrint;
String servingAmount;
String foodQty;
String foodObjId;
String recipeObjId;
TextView foodSelected;
TextView recipeSelected;
    TextView foodSelectedName;
    TextView recipeSelectedName;
    TextView oldFoodQty;
    TextView oldRecipeQty;
    TextView newFoodQty;
    TextView newRecipeServingAmount;
    EditText enterNewFoodQty;
    EditText enterRecipeServing;
    Button foodCarbonEmissions;
    Button recipeCarbonEmissions;
    TextView totalFoodEmissions;
    TextView totalRecipeEmissions;
    float foodQuantity;
    float roundedTotalEmissions;
    float roundedTotalEmissionsRecipe;
    Button editRawFood;
    Button editRecipe;
    Button deleteFood;
    Button deleteRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String type = extras.getString("type");
            if (type.equals("rawfood")) {
            foodName = intent.getStringExtra("foodName");
                 foodCarbonFootPrint = intent.getStringExtra("foodCarbonEmissions");
                foodQty =  intent.getStringExtra("oldFoodQuantity");
                foodObjId = intent.getStringExtra("foodObjId");
                foodSelected = findViewById(R.id.list_name_for_update);
                foodSelected.setText("Food to Edit   : ");
                foodSelectedName = findViewById(R.id.list_food_name_for_update);
                foodSelectedName.setText(foodName);
                oldFoodQty = findViewById(R.id.old_qty_for_update);
                oldFoodQty.setText("Old Quantity(g)   :                 " + foodQty);
                newFoodQty = findViewById(R.id.new_qty_for_update);
                newFoodQty.setText("New Quantity : ");
                enterNewFoodQty = findViewById(R.id.new_qty_for_update_edit);
                foodCarbonEmissions = findViewById(R.id.button_foot_print);
                editRawFood = findViewById(R.id.button_edit_food);
                deleteFood = findViewById(R.id.button_delete_food);
                foodCarbonEmissions.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        showFoodEmissions();
                    }
                });

                editRawFood.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        editRawFoodEmission();
                    }
                });

                deleteFood.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        deleteRawFoodEmissions(Integer.parseInt(foodObjId));
                        System.out.println("Object id for food : " + Integer.parseInt(foodObjId) );
                    }
                });

            } else {
                recipeName = intent.getStringExtra("recipeName");
                recipeCarbonFootPrint = intent.getStringExtra("recipeCarbonEmissions");
                servingAmount =  intent.getStringExtra("recipeServingAmount");
                recipeObjId = intent.getStringExtra("recipeObjId");
                recipeSelected = findViewById(R.id.list_name_for_update);
                recipeSelected.setText("Recipe to Edit : ");
                recipeSelectedName = findViewById(R.id.list_food_name_for_update);
                recipeSelectedName.setText(recipeName);
                oldRecipeQty = findViewById(R.id.old_qty_for_update);
                oldRecipeQty.setText("Old Quantity : " + servingAmount);
                newRecipeServingAmount = findViewById(R.id.new_qty_for_update);
                newRecipeServingAmount.setText("New Serving Amount : ");
                enterRecipeServing = findViewById(R.id.new_qty_for_update_edit);
                recipeCarbonEmissions = findViewById(R.id.button_foot_print);
                editRecipe = findViewById(R.id.button_edit_food);
                deleteRecipe = findViewById(R.id.button_delete_food);
                recipeCarbonEmissions.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        showRecipeEmissions();
                    }
                });

                editRecipe.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        editRecipeEmission();
                    }
                });

                deleteRecipe.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        deleteRecipeFoodEmissions(Integer.parseInt(recipeObjId));
                        System.out.println("Object id for food : " + Integer.parseInt(recipeObjId) );
                    }
                });
            }

        }
    }

    public void showFoodEmissions(){
        if(!(enterNewFoodQty.getText().toString().equalsIgnoreCase(""))){
            foodQuantity = Float.parseFloat(enterNewFoodQty.getText().toString());
            float totalEmissions = (foodQuantity/100) * (Float.parseFloat(foodCarbonFootPrint));
            roundedTotalEmissions  = (float) ((double) Math.round(totalEmissions * 100000d) / 100000d);
            totalFoodEmissions = findViewById(R.id.new_carbon_footprint);
            totalFoodEmissions.setText("New Total Emissions : " + String.valueOf(roundedTotalEmissions) + " kgCO2/100g");
            enterNewFoodQty.setText("");
        }else if (enterNewFoodQty.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Please enter the quantity", Toast.LENGTH_SHORT).show();
        }

    }

    public void showRecipeEmissions(){
        if(Float.parseFloat(newRecipeServingAmount.getText().toString()) > 0){
            float totalEmissionsByServingAmount = Float.parseFloat(newRecipeServingAmount.getText().toString()) * Float.parseFloat(recipeCarbonFootPrint);
            roundedTotalEmissionsRecipe = (float) ((double) Math.round(totalEmissionsByServingAmount * 100000d) / 100000d);
            totalRecipeEmissions = findViewById(R.id.new_carbon_footprint);
            totalRecipeEmissions.setText("New Total Emissions : " + String.valueOf(totalRecipeEmissions) + " KgCo2/100g");
            enterNewFoodQty.setText("");
        }else
            Toast.makeText(getApplicationContext(), "Please enter the quantity", Toast.LENGTH_SHORT).show();
    }

    public void editRawFoodEmission()
    {
        PostService service = RetrofitClientInstance.getRetrofitInstance().create(PostService.class);
        Gson gson = new Gson();

        ArrayList<Consumption> editFood = new ArrayList<>();
        Consumption updateConsumedFood = new Consumption(Integer.parseInt(foodObjId), roundedTotalEmissions, foodQuantity,foodName);
        editFood.add(updateConsumedFood);
        RequestBody putData = RequestBody.create(MediaType.parse("application/json"), gson.toJson(editFood));
        service.updateFoodConsumption(putData).enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {

                System.out.println("Body response " + response.body());
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        Toast.makeText(getApplicationContext(), "Consumption Added", Toast.LENGTH_SHORT).show();
                        finish();
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

    public void editRecipeEmission(){
        PostService service = RetrofitClientInstance.getRetrofitInstance().create(PostService.class);
        Gson gson = new Gson();

        ArrayList<RecipeConsumption> recipeConsumptionUpdate = new ArrayList<>();
        RecipeConsumption editRecipeConsumption = new RecipeConsumption(Integer.parseInt(recipeObjId), Float.parseFloat(recipeCarbonFootPrint), Float.parseFloat(newRecipeServingAmount.getText().toString()),recipeName);
        recipeConsumptionUpdate.add(editRecipeConsumption);
        RequestBody putData = RequestBody.create(MediaType.parse("application/json"), gson.toJson(recipeConsumptionUpdate));
        service.updateRecipeConsumption(putData).enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                System.out.println("Message response " + response.message());

                if (response.isSuccessful()) {
                    if(response.body()!=null) {
                        Toast.makeText(getApplicationContext(), "Recipe Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void deleteRawFoodEmissions(int id){
        PostService service = RetrofitClientInstance.getRetrofitInstance().create(PostService.class);
        Gson gson = new Gson();

        ArrayList<Consumption> deleteFood = new ArrayList<>();
        Consumption deleteConsumedFood = new Consumption(id);
        deleteFood.add(deleteConsumedFood);
        RequestBody deleteData = RequestBody.create(MediaType.parse("application/json"), gson.toJson(deleteFood));
        service.deleteFoodConsumption(deleteData).enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {

                System.out.println("Body response " + response.body());
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        Toast.makeText(getApplicationContext(), "Consumption Added", Toast.LENGTH_SHORT).show();
                        finish();
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

    public void deleteRecipeFoodEmissions(int id){
        PostService service = RetrofitClientInstance.getRetrofitInstance().create(PostService.class);
        Call<List<RecipeConsumption>> call = service.deleteRecipeConsumption(id);
        call.enqueue(new Callback<List<RecipeConsumption>>() {
            @Override
            public void onResponse(Call<List<RecipeConsumption>> call, Response<List<RecipeConsumption>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Post Deleted", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<RecipeConsumption>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
