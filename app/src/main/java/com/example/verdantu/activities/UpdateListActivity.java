package com.example.verdantu.activities;

import android.os.Bundle;
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

    List<Consumption> consumedRawFoodList;
    List<Consumption> tempConsumedFoodList;

    List<RecipeConsumption> consumedRecipeList;
    List<RecipeConsumption> tempConsumedRecipeList;

    UpdateRawFoodListAdapter listRawFoodAdapter;
    UpdateRecipeListAdapter listRecipeAdapter;

    ListView listView;

    String foodInformationStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        listView = findViewById(R.id.listFoods);

        foodInformation = findViewById(R.id.radioCategory);
        foodInformation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                radioID = foodInformation.getCheckedRadioButtonId();
                checkedFoodInformation = findViewById(radioID);
                foodInformationStr = checkedFoodInformation.getText().toString();
                if (foodInformationStr.equalsIgnoreCase("Raw Food")) {
                    getConsumedRawFood();
                }
                else if(foodInformationStr.equalsIgnoreCase("Recipe")){
                    getConsumedRecipe();
                }

            }

        });
    }


    public void getConsumedRawFood() {

        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Consumption>> call = service.getConsumedRawFood();
        consumedRawFoodList = new ArrayList<>();
        call.enqueue(new Callback<List<Consumption>>() {
            @Override
            public void onResponse(Call<List<Consumption>> call, Response<List<Consumption>> response) {
                consumedRawFoodList = response.body();
                consumedRawFoodList();
                System.out.println("List from retrofit : " + consumedRawFoodList);
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
        Call<List<RecipeConsumption>> call = service.getConsumedRecipe();
        consumedRecipeList = new ArrayList<>();
        call.enqueue(new Callback<List<RecipeConsumption>>() {
            @Override
            public void onResponse(Call<List<RecipeConsumption>> call, Response<List<RecipeConsumption>> response) {
                consumedRecipeList = response.body();
               // foodByCategory();
                System.out.println("List from retrofit : " + consumedRecipeList);
            }

            @Override
            public void onFailure(Call<List<RecipeConsumption>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void consumedRawFoodList(){
        tempConsumedFoodList = consumedRawFoodList;
        listRawFoodAdapter = new UpdateRawFoodListAdapter(tempConsumedFoodList, UpdateListActivity.this);
        listView.setClickable(true);
        listView.setAdapter(listRawFoodAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                String foodName = tempConsumedFoodList.get(i).getFoods();
//                float oldFoodQuantity = tempConsumedFoodList.get(i).getFoodQuantity();
//                String oldFoodQuantityStr = String.valueOf(oldFoodQuantity);
        //                  int objId = tempConsumedFoodList.get(i).getObjId();
        //          String obj = String.valueOf(objId);
        //        String consumedType = "rawfood";
//                Intent intent = new Intent(UpdateListActivity.this,
//                        EditActivity.class);
//                intent.putExtra("foodName", foodName);
        //                intent.putExtra("typeFood", consumedType);
        //                intent.putExtra("objId", obj);
//                intent.putExtra("oldFoodQuantity", oldFoodQuantityStr);
//                intent.putExtra("checkedFoodCategory", strFoodCategory);
//                startActivity(intent);
//                //Toast.makeText(getApplicationContext(), carbonEmissionStr, Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public void consumedRecipeList(){
        tempConsumedRecipeList = consumedRecipeList;
        //foodList = tempConsumedRecipeList;
        listRecipeAdapter = new UpdateRecipeListAdapter(tempConsumedRecipeList, UpdateListActivity.this);
        listView.setClickable(true);
        listView.setAdapter(listRecipeAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                String recipeName = tempConsumedRecipeList.get(i).getRecipeName();
//                float recipeServingAmount = tempConsumedRecipeList.get(i).getServingAmount();
//                String recipeServingAmountString = String.valueOf(recipeServingAmount);
  //                  int objId = tempConsumedRecipeList.get(i).getObjId();
        //          String obj = String.valueOf(objId);
        //          String consumedType = "recipe";
//                Intent intent = new Intent(UpdateListActivity.this,
//                        EditActivity.class);
//                intent.putExtra("recipeName", recipeName);
        //                intent.putExtra("objId", obj);
        //          intent.putExtra("typeRecipe",consumedType);
//                intent.putExtra("recipeServingAmount", recipeServingAmountString);
//                intent.putExtra("checkedFoodCategory", strFoodCategory);
//                startActivity(intent);
//                //Toast.makeText(getApplicationContext(), carbonEmissionStr, Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
