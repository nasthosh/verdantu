package com.example.verdantu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.verdantu.R;

public class AddFoodItems extends AppCompatActivity {
    String foodName;
    String foodCarbonFootPrints;
    TextView foodItemName;
    TextView foodItemCarbonEmission;
    TextView totalFoodEmissions;
    EditText foodQuantity;
    String foodQty;
    Button showCarbonFootPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_items);
        Intent intent = getIntent();
        foodName = intent.getStringExtra("foodItems");
        foodCarbonFootPrints = intent.getStringExtra("carbonEmissions");
        foodItemName = findViewById(R.id.textView_FoodName);
        foodItemName.setText("Selected Food : " + foodName);
        foodItemCarbonEmission = findViewById(R.id.textView_FoodEmissions);
        foodItemCarbonEmission.setText("Emission : " + foodCarbonFootPrints + " Kg/Co2");
        foodQuantity = findViewById(R.id.editTextView_Qty);
        showCarbonFootPrint = findViewById(R.id.btn_ShowCarbonFootPrint);

        showCarbonFootPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                foodQty = foodQuantity.getText().toString();
                Double qty = Double.parseDouble(foodQty);
                Double convertKgToGram = qty/1000;
                Double totalCarbonEmissions = convertKgToGram * (Double.parseDouble(foodCarbonFootPrints));
                totalFoodEmissions = findViewById(R.id.textView_TotalFoodEmissions);
                Double roundDecimalTotalFoodEmission = (double)Math.round(totalCarbonEmissions * 100000d) / 100000d;
                String finalTotalEmissions = String.valueOf(roundDecimalTotalFoodEmission);
                totalFoodEmissions.setText("Total Emissions : " + finalTotalEmissions +  " Kg/Co2");
                System.out.println("Emission : " + foodName);
            }
        });
    }
}
