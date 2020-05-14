package com.example.verdantu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.verdantu.R;
import com.example.verdantu.activities.RecipeActivity;
import com.example.verdantu.activities.ShowFoods;
import com.example.verdantu.activities.UpdateListActivity;
import com.example.verdantu.models.Consumption;
import com.example.verdantu.ui.viewmodels.HomeViewModel;
import com.github.clans.fab.FloatingActionButton;

import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button addFood;
    Boolean isRotate = false;
    FloatingActionButton fabAddByCategory;
    FloatingActionButton fabAddByRecipe;
    FloatingActionButton fabEditFood;
    TextView dailyEmissionText;
    List<Consumption> dailyEmission;
    private TextView textView1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        fabAddByCategory = root.findViewById(R.id.fabAddByCategory);
        fabAddByRecipe = root.findViewById(R.id.fabAddProduct);
        fabEditFood = root.findViewById(R.id.fabEditFood);
//        dailyEmissionText = root.findViewById(R.id.textView3);
//        textView1 = root.findViewById(R.id.textView4);
        fabAddByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFoodsByCategory = new Intent(getActivity(), ShowFoods.class);
                startActivity(addFoodsByCategory);
            }
        });

        fabAddByRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFoodsByRecipe = new Intent(getActivity(), RecipeActivity.class);
                startActivity(addFoodsByRecipe);
            }
        });

        fabEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFoodsByRecipe = new Intent(getActivity(), UpdateListActivity.class);
                startActivity(addFoodsByRecipe);
            }
        });

//        textView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent addFoodsByCategory = new Intent(getActivity(), ShowFoods.class);
//                startActivity(addFoodsByCategory);
//            }
//        });

        return root;
    }
}

//    public void onStart() {
//        super.onStart();
//
//        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
//        Call<List<Consumption>> call = service.getEmissionForCurrentDay();
//        dailyEmission = new ArrayList<>();
//        call.enqueue(new Callback<List<Consumption>>() {
//            @Override
//            public void onResponse(Call<List<Consumption>> call, Response<List<Consumption>> response) {
//
//                dailyEmission = response.body();
//                System.out.println("List From Retro in daily emission " + dailyEmission);
//                if(dailyEmission.get(0).getEmission() == 0.0) {
//
//                    dailyEmissionText.setText("   Lets add some food for today!!");
//                    dailyEmissionText.setTextSize(25f);
//                    System.out.println("Daily Emissions is called inside null");
//                }
//
//                else
//                {
//                    float foodEmissionsDaily = dailyEmission.get(0).getEmission();
//                    String foodEmissionsDailyString = String.valueOf(foodEmissionsDaily);
//                    dailyEmissionText.setText("   Your Carbon Emission for Today ");
//                    textView1.setText("       " + foodEmissionsDailyString + " KgCo2/100g");
//                    dailyEmissionText.setTextSize(25f);
//                    System.out.println("Daily Emissions is called inside some value");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Consumption>> call, Throwable t) {
//
//                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//                System.out.println(" Throwable error : " + t);
//            }
//        });
//

