package com.example.verdantu.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.verdantu.R;
import com.example.verdantu.activities.DeviceData;
import com.example.verdantu.activities.RecipeActivity;
import com.example.verdantu.activities.RecommendationActivity;
import com.example.verdantu.activities.ShowFoods;
import com.example.verdantu.activities.UpdateListActivity;
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Consumption;
import com.example.verdantu.rest.RetrofitClientInstance;
import com.example.verdantu.ui.food.FoodFragment;
import com.example.verdantu.ui.viewmodels.HomeViewModel;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FloatingActionButton fabAddByCategory;
    FloatingActionButton fabAddByRecipe;
    FloatingActionButton fabEditFood;
    List<Consumption> dailyEmission;
    String deviceId;
    ImageButton nutritionButton;
    ProgressBar emissionIntensity;
    TextView intensityValues;
    TextView dailyEmissions;
    ImageButton recommendationButton;
    TextView intensityLevels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        deviceId = DeviceData.getDeviceId(root.getContext());
        fabAddByCategory = root.findViewById(R.id.fabAddByCategory);
        fabAddByRecipe = root.findViewById(R.id.fabAddProduct);
        fabEditFood = root.findViewById(R.id.fabEditFood);
        intensityLevels = root.findViewById(R.id.textProgressBar);
        nutritionButton = root.findViewById(R.id.dailyEmissionsImageButton);
//        intensityValues = root.findViewById(R.id.textProgressBarValues);
        emissionIntensity = root.findViewById(R.id.intensityProgress);
        recommendationButton = root.findViewById(R.id.image2);
        dailyEmissions = root.findViewById(R.id.textEmissionsInBold);
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

        nutritionButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodFragment foodFragment = new FoodFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,foodFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }));

        recommendationButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startRecommendationActivity = new Intent(getActivity(), RecommendationActivity.class);
                startActivity(startRecommendationActivity);
            }
        }));

                GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
                Call<List<Consumption>> call = service.getEmissionForCurrentDay(deviceId);
                dailyEmission = new ArrayList<>();
                call.enqueue(new Callback<List<Consumption>>() {
                    @Override
                    public void onResponse(Call<List<Consumption>> call, Response<List<Consumption>> response) {

                        dailyEmission = response.body();
                        System.out.println("List From Retro in daily emission " + dailyEmission);
                        if (response.body() != null) {
                            if (dailyEmission.get(0).getEmission() == 0.0) {
                                dailyEmissions.setText("              Let add some food for today");
                                System.out.println("Daily Emissions is called inside null");
                            } else {
                                float foodEmissionsDaily = dailyEmission.get(0).getEmission();
                                String foodEmissionsDailyString = String.valueOf(foodEmissionsDaily);
                                dailyEmissions.setText("                  Your Emission for Today  " + "\n" + "                         " + foodEmissionsDailyString + " KgCo2/100g" + "\n" + "          Your Scale of Emission for Today ");

                                if (dailyEmission.get(0).getEmission() > 8.2) {
                                    intensityLevels.setVisibility(View.VISIBLE);
                                    dailyEmissions.setText("                         " + foodEmissionsDailyString + " KgCo2/100g" );
dailyEmissions.setTextColor(Color.RED);
                                    emissionIntensity.setVisibility(View.VISIBLE);
                                    emissionIntensity.setMax(100);
                                    emissionIntensity.getProgressDrawable().setColorFilter(
                                            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                    emissionIntensity.setProgress(100);
                                } else if ((dailyEmission.get(0).getEmission() >= 4.1) && (dailyEmission.get(0).getEmission() <= 8.2)) {
                                    intensityLevels.setVisibility(View.VISIBLE);
                                    dailyEmissions.setText("                         " + foodEmissionsDailyString + " KgCo2/100g" );
                                    dailyEmissions.setTextColor(Color.rgb(255, 165, 0));
                                    emissionIntensity.setVisibility(View.VISIBLE);
                                    emissionIntensity.setMax(100);
                                    emissionIntensity.getProgressDrawable().setColorFilter(
                                            Color.rgb(255, 165, 0), android.graphics.PorterDuff.Mode.SRC_IN);
                                    emissionIntensity.setProgress(50);
                                } else if ((dailyEmission.get(0).getEmission() <= 4.1)) {
                                    intensityLevels.setVisibility(View.VISIBLE);
                                    dailyEmissions.setText("                         " + foodEmissionsDailyString + " KgCo2/100g" );
                                    dailyEmissions.setTextColor(Color.GREEN);
                                    emissionIntensity.setVisibility(View.VISIBLE);
                                    emissionIntensity.setMax(100);
                                    emissionIntensity.getProgressDrawable().setColorFilter(
                                            Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
                                    emissionIntensity.setProgress(25);
                                }else{
                                    emissionIntensity.setVisibility(View.GONE);
                                }

                                //intensityValues.setText("    <=2.05                               =4.1                                     >=8.2");
                            }
                        } else {
                            dailyEmissions.setText("                 Let add some food for today");
                            emissionIntensity.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Consumption>> call, Throwable t) {

                        Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        System.out.println(" Throwable error : " + t);
                    }
                });
        return root;
    }
}

