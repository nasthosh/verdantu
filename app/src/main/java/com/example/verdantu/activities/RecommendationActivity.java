package com.example.verdantu.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.verdantu.R;
import com.example.verdantu.adapters.RecommendationListAdapter;
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Recommendation;
import com.example.verdantu.rest.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationActivity extends AppCompatActivity {


    List<Recommendation> recommendationRecipeList;
    List<Recommendation> recipeItems;
    Call<List<Recommendation>> call;
    Call<List<Recommendation>> recipeCall;

    RecommendationListAdapter listAdapter;
    RecommendationListAdapter listViewDataAdapter;

    List<Recommendation> recommendationFoodList;
    List<Recommendation> foodItemsRecommended;

    ListView listView;
    RadioGroup recommendedFood;
    int radioID;
    RadioButton checkedItemCategory;
    String checkedItemCat;
    String deviceId;
    TextView headingRecommendation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
//        if(restorePrefData()) {
//            AlertDialog alertDialog = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_map)
//                    .setTitle("Info").setMessage("Please add some food to get better recommendations/suggestions")
//                    .setPositiveButton("Return", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            savePrefsData();
//                            Toast.makeText(getApplicationContext(), "Thanks", Toast.LENGTH_SHORT).show();
//
//                        }
//                    })
//                    .show();
//        }
        listView = findViewById(R.id.listRecommendations);
        deviceId = DeviceData.getDeviceId(this);
        headingRecommendation = findViewById(R.id.recommendation_heading);
        recommendedFood = findViewById(R.id.recommendationRadioCategory);

        recommendedFood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioID = recommendedFood.getCheckedRadioButtonId();
                checkedItemCategory = findViewById(radioID);
                checkedItemCat = checkedItemCategory.getText().toString();
                if (checkedItemCat.equalsIgnoreCase("By Raw Food")) {
                    headingRecommendation.setText("      Consumed Food              Recommended Food");
                    getRecommendedFoods();

                }else if(checkedItemCat.equalsIgnoreCase("By Recipe")){
                    headingRecommendation.setText("      Consumed Food              Recommended Food");
                    getRecommendedRecipes();
                }
            }
        });
    }

    public void getRecommendedFoods(){
        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
//        if(restorePrefData()) {
//
//            call = service.getRecommendedFoodNewUser();
//            savePrefsData();
//        }
//        else
            call =service.getRecommendedRawFood(deviceId);
        recommendationFoodList = new ArrayList<>();
        call.enqueue(new Callback<List<Recommendation>>() {
            @Override
            public void onResponse(Call<List<Recommendation>> call, Response<List<Recommendation>> response) {
                recommendationFoodList = response.body();
                System.out.println("List from retrofit  in food recom: " + recommendationFoodList);
                emissionFromFoods();
            }

            @Override
            public void onFailure(Call<List<Recommendation>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void getRecommendedRecipes()
    {
        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
//        if(restorePrefData()){
//            recipeCall = service.getRecommendedRecipeNewUser();
//            // savePrefsData();
//        }else
            recipeCall = service.getRecommendedRecipe(deviceId);
        recommendationRecipeList = new ArrayList<>();
        recipeCall.enqueue(new Callback<List<Recommendation>>() {
            @Override
            public void onResponse(Call<List<Recommendation>> recipeCall, Response<List<Recommendation>> response) {
                recommendationRecipeList = response.body();
                System.out.println("List from retrofit in recipe activity : " + recommendationRecipeList);
                recipeItems();
            }

            @Override
            public void onFailure(Call<List<Recommendation>> recipeCall, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void emissionFromFoods() {
        foodItemsRecommended = recommendationFoodList;
        //tableFoodList = foodEmissionsList;
        listAdapter = new RecommendationListAdapter(foodItemsRecommended, this);
        listView.setClickable(true);
        listView.setAdapter(listAdapter);

    }

    public void recipeItems() {
        recipeItems = recommendationRecipeList;
        //recipeList = recipeItems;
        listViewDataAdapter = new RecommendationListAdapter(recipeItems, this);
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
