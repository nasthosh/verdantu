package com.example.verdantu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.verdantu.R;
import com.example.verdantu.adapters.UpdateRawFoodListAdapter;
import com.example.verdantu.adapters.UpdateRecipeListAdapter;
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Consumption;
import com.example.verdantu.models.RecipeConsumption;
import com.example.verdantu.rest.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateListActivity extends AppCompatActivity {

    RadioGroup foodInformation;
    int radioID;
    RadioButton checkedFoodInformation;

    Handler handler;

    List<Consumption> consumedRawFoodList;
    List<Consumption> tempConsumedFoodList;

    List<RecipeConsumption> consumedRecipeList;
    List<RecipeConsumption> tempConsumedRecipeList;

    UpdateRawFoodListAdapter listRawFoodAdapter;
    UpdateRecipeListAdapter listRecipeAdapter;


    ListView listView;

    String foodInformationStr;
    String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        this.handler = new Handler();
        runnable.run();
        listView = findViewById(R.id.listItems);
        deviceId = DeviceData.getDeviceId(getApplicationContext());
        foodInformation = findViewById(R.id.radioCategory);

    }
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
           // Toast.makeText(UpdateListActivity.this,"in runnable",Toast.LENGTH_SHORT).show();
            UpdateListActivity.this.handler.postDelayed(runnable,2000);
        }
    };

    public void onResume() {
        //EditActivity closeActivity = new EditActivity();
        //closeActivity.finish();
        super.onResume();
        //super.onStart();
        foodInformation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                radioID = foodInformation.getCheckedRadioButtonId();
                checkedFoodInformation = findViewById(radioID);
                foodInformationStr = checkedFoodInformation.getText().toString();
                if (foodInformationStr.equalsIgnoreCase("Raw Food")) {

                    getConsumedRawFood();
                } else if (foodInformationStr.equalsIgnoreCase("Recipe")) {
                    getConsumedRecipe();
                }

            }

        });
    }

    public void getConsumedRawFood() {

        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Consumption>> call = service.getConsumedRawFood(deviceId);
        consumedRawFoodList = new ArrayList<>();
        call.enqueue(new Callback<List<Consumption>>() {
            @Override
            public void onResponse(Call<List<Consumption>> call, Response<List<Consumption>> response) {
                System.out.println("Response in update activity after delete food : " + response.body());
                if(response.body()!=null) {
                    consumedRawFoodList = response.body();
                    if(!(consumedRawFoodList.get(0).getFoods()).equalsIgnoreCase("No food found"))
                    consumedRawFoodList();
                    else
                        Toast.makeText(getApplicationContext(), "No food to view", Toast.LENGTH_SHORT).show();
                    System.out.println("List from retrofit : " + consumedRawFoodList);
                }else{
                    Toast.makeText(getApplicationContext(), "No food to view", Toast.LENGTH_SHORT).show();
                   // System.out.println(" Throwable error : " + t);
                }
            }

            @Override
            public void onFailure(Call<List<Consumption>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void getConsumedRecipe() {

        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<RecipeConsumption>> call = service.getConsumedRecipe(deviceId);
        consumedRecipeList = new ArrayList<>();
        call.enqueue(new Callback<List<RecipeConsumption>>() {
            @Override
            public void onResponse(Call<List<RecipeConsumption>> call, Response<List<RecipeConsumption>> response) {
                System.out.println("Response in update activity after delete food : " + response.body().size());
                //String responseMessage = response.message();
                consumedRecipeList = response.body();
                if(!(consumedRecipeList.get(0).getRecipeName()).equalsIgnoreCase("No recipe found"))
                consumedRecipeList();
                else
                    Toast.makeText(getApplicationContext(), "No recipe found", Toast.LENGTH_SHORT).show();
               // foodByCategory();
                System.out.println("List from retrofit : " + consumedRecipeList);
            }

            @Override
            public void onFailure(Call<List<RecipeConsumption>> call, Throwable t) {
                Toast.makeText(UpdateListActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void consumedRawFoodList(){
        tempConsumedFoodList = consumedRawFoodList;
        listRawFoodAdapter = new UpdateRawFoodListAdapter(tempConsumedFoodList, UpdateListActivity.this);
        listView.setClickable(true);
        listView.setAdapter(listRawFoodAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String foodName = tempConsumedFoodList.get(i).getFoods();
                float oldFoodQuantity = tempConsumedFoodList.get(i).getFoodQuantity();
                String oldFoodQuantityStr = String.valueOf(oldFoodQuantity);
                          int objId = tempConsumedFoodList.get(i).getObjId();
                  String obj = String.valueOf(objId);
        float foodCarbonEmisiion = tempConsumedFoodList.get(i).getEmission();
        String carbonEmissionStr = String.valueOf(foodCarbonEmisiion);
                String consumedType = "rawfood";
                Intent intent = new Intent(UpdateListActivity.this,
                        EditActivity.class);
        intent.putExtra("foodCarbonEmissions", carbonEmissionStr);
                intent.putExtra("foodName", foodName);
                        intent.putExtra("type", consumedType);
                        intent.putExtra("foodObjId", obj);
                intent.putExtra("oldFoodQuantity", oldFoodQuantityStr);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), carbonEmissionStr, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void consumedRecipeList(){
        tempConsumedRecipeList = consumedRecipeList;
        //foodList = tempConsumedRecipeList;
        listRecipeAdapter = new UpdateRecipeListAdapter(tempConsumedRecipeList, UpdateListActivity.this);
        listView.setClickable(true);
        listView.setAdapter(listRecipeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String recipeName = tempConsumedRecipeList.get(i).getRecipeName();
                float recipeServingAmount = tempConsumedRecipeList.get(i).getServingAmount();
                String recipeServingAmountString = String.valueOf(recipeServingAmount);
                    int objId = tempConsumedRecipeList.get(i).getObjId();
                  String obj = String.valueOf(objId);
                  String consumedType = "recipe";
         float foodCarbonEmisiion = tempConsumedRecipeList.get(i).getRecipeEmission();
         String carbonEmissionStr = String.valueOf(foodCarbonEmisiion);
                Intent intent = new Intent(UpdateListActivity.this,
                        EditActivity.class);
                intent.putExtra("recipeName", recipeName);
        intent.putExtra("recipeCarbonEmissions", carbonEmissionStr);
                        intent.putExtra("recipeObjId", obj);
                  intent.putExtra("type",consumedType);
                intent.putExtra("recipeServingAmount", recipeServingAmountString);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), carbonEmissionStr, Toast.LENGTH_LONG).show();
            }
        });
    }
}
