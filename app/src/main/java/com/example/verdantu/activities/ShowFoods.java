package com.example.verdantu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.verdantu.R;
import com.example.verdantu.adapters.FoodListAdapter;
import com.example.verdantu.models.FoodEmissions;
import com.example.verdantu.rest.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowFoods extends AppCompatActivity {

    RadioGroup foodCategory;
    int radioID;
    RadioButton checkedRadioButtonCategory;
    String strFoodCategory;

    List<FoodEmissions> foodByCategory;
    List<FoodEmissions> foodList;

    ListView listView;
    FoodListAdapter listViewDataAdapter;
    int rgSelected;
    String rgCategorySelectedInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_foods);
        listView = findViewById(R.id.listFoods);

        foodCategory = findViewById(R.id.radioCategory);
        //rgSelected = foodCategory.getCheckedRadioButtonId();
        //rgCategorySelectedInt = String.valueOf(rgSelected);
        foodCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                radioID = foodCategory.getCheckedRadioButtonId();
                checkedRadioButtonCategory = findViewById(radioID);
                strFoodCategory = checkedRadioButtonCategory.getText().toString();
                FoodCategoryAsyncTask getEmissionsByCategories= new FoodCategoryAsyncTask();
                getEmissionsByCategories.execute(strFoodCategory);
            }

        });
    }

    public class FoodCategoryAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.getFoodsByCategory(params[0]);
        }

        protected void onPostExecute(String result) {
            foodByCategory(result);
        }
    }

    public void foodByCategory(String result){
        foodByCategory = getFoodItemsByCategory(result);
        foodList = foodByCategory;
        listViewDataAdapter = new FoodListAdapter(foodList, ShowFoods.this);
        listView.setClickable(true);
        listView.setAdapter(listViewDataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String foodItem = foodList.get(i).getFoodItems();
                String foodCarbonEmisiion = foodList.get(i).getCarbonEmissions();
                Intent intent = new Intent(ShowFoods.this,
                        AddFoodItems.class);
                intent.putExtra("foodItems", foodItem);
                intent.putExtra("carbonEmissions", foodCarbonEmisiion);
                intent.putExtra("checkedFoodCategory", strFoodCategory);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), foodCarbonEmisiion, Toast.LENGTH_LONG).show();
            }
        });
    }

    public List<FoodEmissions> getFoodItemsByCategory(String result) {
        List<FoodEmissions> foodByCategory = new ArrayList<>();
        try {
            // Converting the result to a JSON object
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("recordset");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject foodEmissionsList = jsonArray.getJSONObject(i);
                    String foodName = foodEmissionsList.getString("Foods");
                    String foodEmission = foodEmissionsList.getString("Emissions");
                    FoodEmissions foodItemsEmissions = new FoodEmissions(foodName,foodEmission);
                    foodByCategory.add(foodItemsEmissions);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Returning food category list : " + foodByCategory);
        return foodByCategory;
    }
}
