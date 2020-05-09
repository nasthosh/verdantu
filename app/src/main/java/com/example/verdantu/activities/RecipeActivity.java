package com.example.verdantu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.verdantu.R;
import com.example.verdantu.adapters.RecipeListAdapter;
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Recipe;
import com.example.verdantu.rest.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipeActivity extends AppCompatActivity {
    List<Recipe> emissionListByRecipe;
    List<Recipe> recipeItems;
    List<Recipe> recipeList;
    RecipeListAdapter listViewDataAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        listView = findViewById(R.id.listRecipe);

        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Recipe>> call = service.getRecipeEmissions();
        emissionListByRecipe = new ArrayList<>();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                emissionListByRecipe = response.body();
                System.out.println("List from retrofit in recipe activity : " + emissionListByRecipe);
                recipeItems();

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void recipeItems(){
        recipeItems = emissionListByRecipe;
        recipeList = recipeItems;
        listViewDataAdapter = new RecipeListAdapter(recipeList, RecipeActivity.this);
        listView.setClickable(true);
        listView.setAdapter(listViewDataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String recipeItem = recipeList.get(i).getRecipeName();
                float recipeCarbonEmisiion = recipeList.get(i).getRecipeEmission();
                String recipeCarbonEmisiionStr = String.valueOf(recipeCarbonEmisiion);
                Intent intent = new Intent(RecipeActivity.this, AddRecipe.class);
                intent.putExtra("recipe", recipeItem);
                intent.putExtra("recipeCarbonEmissions", recipeCarbonEmisiionStr);
                //intent.putExtra("checkedFoodCategory", strFoodCategory);
                startActivity(intent);
            }
        });
    }
}
