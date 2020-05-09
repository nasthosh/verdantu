package com.example.verdantu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.verdantu.R;
import com.example.verdantu.adapters.FoodListAdapter;
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Food;
import com.example.verdantu.rest.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowFoods extends AppCompatActivity {

    RadioGroup foodCategory;
    int radioID;
    RadioButton checkedRadioButtonCategory;
    String strFoodCategory;

    List<Food> foodByCategory;
    List<Food> foodList;

    List<Food> emissionsListByCategory;

    ListView listView;
    FoodListAdapter listViewDataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_foods);
        listView = findViewById(R.id.listFoods);

        foodCategory = findViewById(R.id.radioCategory);
        foodCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                radioID = foodCategory.getCheckedRadioButtonId();
                checkedRadioButtonCategory = findViewById(radioID);
                strFoodCategory = checkedRadioButtonCategory.getText().toString();
                GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
                Call<List<Food>> call = service.getFoodByCategory(strFoodCategory);
                emissionsListByCategory = new ArrayList<>();
                call.enqueue(new Callback<List<Food>>() {
                    @Override
                    public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                        emissionsListByCategory = response.body();
                        foodByCategory();
                        System.out.println("List from retrofit : " + emissionsListByCategory);
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

    public void foodByCategory(){
        foodByCategory = emissionsListByCategory;
        foodList = foodByCategory;
        listViewDataAdapter = new FoodListAdapter(foodList, ShowFoods.this);
        listView.setClickable(true);
        listView.setAdapter(listViewDataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String foodItem = foodList.get(i).getFoodName();
                float foodCarbonEmisiion = foodList.get(i).getFoodEmissions();
                String carbonEmissionStr = String.valueOf(foodCarbonEmisiion);
                Intent intent = new Intent(ShowFoods.this,
                        AddFoodItems.class);
                intent.putExtra("foodItems", foodItem);
                intent.putExtra("carbonEmissions", carbonEmissionStr);
                intent.putExtra("checkedFoodCategory", strFoodCategory);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), carbonEmissionStr, Toast.LENGTH_LONG).show();
            }
        });
    }

}
