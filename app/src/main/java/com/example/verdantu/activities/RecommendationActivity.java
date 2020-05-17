package com.example.verdantu.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.verdantu.R;
import com.example.verdantu.adapters.FoodItemsListAdapter;
import com.example.verdantu.adapters.RecipeListAdapter;
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Food;
import com.example.verdantu.models.Recipe;
import com.example.verdantu.rest.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationActivity extends AppCompatActivity {


    List<Recipe> recommendationRecipeList;
    List<Recipe> recipeItems;
    Call<List<Food>> call;
    Call<List<Recipe>> recipeCall;

    FoodItemsListAdapter listAdapter;
    RecipeListAdapter listViewDataAdapter;

    List<Food> recommendationFoodList;
    List<Food> foodItemsRecommended;

    ListView listView;
    RadioGroup recommendedFood;
    int radioID;
    RadioButton checkedItemCategory;
    String checkedItemCat;
    String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        if(!restorePrefData()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_map)
                    .setTitle("Info").setMessage("As you're logging in for the first time, we are showing some basic food recommendations but it will vary based on your food input")
                    .setPositiveButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            savePrefsData();
                            Toast.makeText(getApplicationContext(), "Thanks", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .show();
        }
        listView = findViewById(R.id.listRecommendations);
        deviceId = DeviceData.getDeviceId(this);
        recommendedFood = findViewById(R.id.recommendationRadioCategory);

        recommendedFood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioID = recommendedFood.getCheckedRadioButtonId();
                checkedItemCategory = findViewById(radioID);
                checkedItemCat = checkedItemCategory.getText().toString();
                if (checkedItemCat.equalsIgnoreCase("By Raw Food")) {
                    getRecommendedFoods();

                }else if(checkedItemCat.equalsIgnoreCase("By Recipe")){
                    getRecommendedRecipes();
                }
            }
        });
    }

    public void getRecommendedFoods(){
        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        if(!restorePrefData()) {
            call = service.getRecommendedFoodNewUser();
            savePrefsData();
        }
        else
            call =service.getRecommendedRawFood(deviceId);
        recommendationFoodList = new ArrayList<>();
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                recommendationFoodList = response.body();
                System.out.println("List from retrofit  in food recom: " + recommendationFoodList);
                emissionFromFoods();
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void getRecommendedRecipes()
    {
        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        if(!restorePrefData()){
            recipeCall = service.getRecommendedRecipeNewUser();
           // savePrefsData();
        }else
            recipeCall = service.getRecommendedRecipe(deviceId);
        recommendationRecipeList = new ArrayList<>();
        recipeCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> recipeCall, Response<List<Recipe>> response) {
                recommendationRecipeList = response.body();
                System.out.println("List from retrofit in recipe activity : " + recommendationRecipeList);
                recipeItems();
            }

            @Override
            public void onFailure(Call<List<Recipe>> recipeCall, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void emissionFromFoods() {
        foodItemsRecommended = recommendationFoodList;
        //tableFoodList = foodEmissionsList;
        listAdapter = new FoodItemsListAdapter(foodItemsRecommended, this);
        listView.setClickable(true);
        listView.setAdapter(listAdapter);

    }

    public void recipeItems() {
        recipeItems = recommendationRecipeList;
        //recipeList = recipeItems;
        listViewDataAdapter = new RecipeListAdapter(recipeItems, this,true);
        listView.setClickable(true);
        listView.setAdapter(listViewDataAdapter);
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
        System.out.println("Coming inside shared preferences : ");
    }


}
