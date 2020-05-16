package com.example.verdantu.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.verdantu.R;
import com.example.verdantu.activities.DeviceData;
import com.example.verdantu.activities.RecipeActivity;
import com.example.verdantu.activities.ShowFoods;
import com.example.verdantu.activities.UpdateListActivity;
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Consumption;
import com.example.verdantu.rest.RetrofitClientInstance;
import com.example.verdantu.ui.viewmodels.HomeViewModel;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button addFood;
    Boolean isRotate = false;
    FloatingActionButton fabAddByCategory;
    FloatingActionButton fabAddByRecipe;
    FloatingActionButton fabEditFood;
    TextView dailyEmissionText;
    List<Consumption> dailyEmission;
    String deviceId;
    private TextView textView1;
    ImageButton dailyEmissionButton;
    ProgressBar emissionIntensity;
    TextView intensityValues;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        deviceId = DeviceData.getDeviceId(root.getContext());
        fabAddByCategory = root.findViewById(R.id.fabAddByCategory);
        fabAddByRecipe = root.findViewById(R.id.fabAddProduct);
        fabEditFood = root.findViewById(R.id.fabEditFood);
        dailyEmissionButton = root.findViewById(R.id.dailyEmissionsImageButton);
        intensityValues = root.findViewById(R.id.textProgressBarValues);
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

        dailyEmissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
                Call<List<Consumption>> call = service.getEmissionForCurrentDay(deviceId);
                dailyEmission = new ArrayList<>();
                call.enqueue(new Callback<List<Consumption>>() {
                    @Override
                    public void onResponse(Call<List<Consumption>> call, Response<List<Consumption>> response) {

                        dailyEmission = response.body();
                        System.out.println("List From Retro in daily emission " + dailyEmission);
                        if(dailyEmission.get(0).getEmission() == 0.0) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_map)
                                    .setTitle("Info").setMessage("   Lets add some food for today!!")
                                    .setPositiveButton("Return", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(getActivity(), "Thanks", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .show();
                            System.out.println("Daily Emissions is called inside null");
                        }

                        else
                        {
                            float foodEmissionsDaily = dailyEmission.get(0).getEmission();
                            String foodEmissionsDailyString = String.valueOf(foodEmissionsDaily);
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_map)
                                    .setTitle("Info").setMessage("Emission for Today   :    " + foodEmissionsDailyString + " KgCo2/100g")
                                    .setPositiveButton("Return", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(getActivity(), "Thanks", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .show();
                            System.out.println("Daily Emissions is called inside some value");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Consumption>> call, Throwable t) {

                        Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        System.out.println(" Throwable error : " + t);
                    }
                });
            }
        });

        emissionIntensity = root.findViewById(R.id.intensityProgress);
        if((8.3 >= 2.05)){
            emissionIntensity.setMax(100);
            emissionIntensity.getProgressDrawable().setColorFilter(
                    Color.rgb(255, 165, 0), android.graphics.PorterDuff.Mode.SRC_IN);
            emissionIntensity.setProgress(50);
        }
        intensityValues.setText("    <=2.05                               =4.1                                     >=8.2");

//        emissionIntensity = root.findViewById(R.id.intensityProgress);
//
//        if(dailyEmission.get(0).getEmission() > 8.2) {
//            emissionIntensity.setMax(100);
//            emissionIntensity.getProgressDrawable().setColorFilter(
//                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
//            emissionIntensity.setProgress(100);
//        }else if((dailyEmission.get(0).getEmission() >= 4.1) && (dailyEmission.get(0).getEmission() <= 8.2)){
//            emissionIntensity.setMax(100);
//            emissionIntensity.getProgressDrawable().setColorFilter(
//                    Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
//            emissionIntensity.setProgress(50);
//        }else if((dailyEmission.get(0).getEmission() >= 2.05) && (dailyEmission.get(0).getEmission() <= 4.1)){
//            emissionIntensity.setMax(100);
//            emissionIntensity.getProgressDrawable().setColorFilter(
//                    Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
//            emissionIntensity.setProgress(25);
//        }


        return root;
    }
}

