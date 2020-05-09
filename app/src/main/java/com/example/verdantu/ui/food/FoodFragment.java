package com.example.verdantu.ui.food;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.verdantu.R;
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Food;
import com.example.verdantu.rest.RetrofitClientInstance;
import com.example.verdantu.ui.viewmodels.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * This class is the fragment for listing the food items in the bottom navigation activity
 *
 *
 */

public class FoodFragment extends Fragment {

    private FoodViewModel foodViewModel;
    TableLayout tableLayout;
    TableRow tableRow;
    TextView text01;
    TextView text02;
    View root;
    EditText editText;
    Button search;
    List<Food> foodEmissionsList;
    List<Food> tableFoodList;
    List<Food> emissionsListRetro;
    int count = 0;
    String editTextFood;
    ProgressDialog progressDoalog;
    Boolean isElementPresent = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodViewModel =
                ViewModelProviders.of(this).get(FoodViewModel.class);
        root = inflater.inflate(R.layout.fragment_food_items, container, false); // Inflating the view model

        tableLayout = root.findViewById(R.id.TableLayout01); // Identifying layouts using the corresponding ID's

        progressDoalog = new ProgressDialog(root.getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Food>> call = service.getFood();
        emissionsListRetro = new ArrayList<>();
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                progressDoalog.dismiss();
                emissionsListRetro = response.body();
                emissionFromFoods();
                System.out.println("List from retrofit : " + emissionsListRetro);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(root.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });

        editText = root.findViewById(R.id.editText);
        search = root.findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View v) {  // Defining the button on click activity for search
                for (Food foodItem : tableFoodList) {
                    editTextFood = editText.getText().toString();
                    //isElementPresent = foodItem.getFoodName().contains(editTextFood);
                    System.out.print("Edittext food : " + editTextFood);

                    System.out.print("Boolean Contains : " + isElementPresent);

                    if(foodItem.getFoodName().equalsIgnoreCase(editTextFood)){ // Checking if the searched item is present in the list

                        System.out.println("              Searched food item : " + foodItem.getFoodName());
                        tableRow = new TableRow(root.getContext());

                        text01 = new TextView(root.getContext());
                        text02 = new TextView(root.getContext());

                        String foodName = foodItem.getFoodName();
                        float foodEmissions = foodItem.getFoodEmissions();
                        String foodEmissionsStr = String.valueOf(foodEmissions);
                        //Clean the Table after every search
                        cleanTable(tableLayout,1);

                        text01.setText(foodName);
                        text02.setText(foodEmissionsStr);

                        System.out.println("              Searched food item emissions : " + foodEmissionsStr);

                        // Add the data to the view
                        tableRow.addView(text01);
                        tableRow.addView(text02);
                        tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    }
                    else if(editTextFood.equalsIgnoreCase("") ){
                        cleanTable(tableLayout,0);
                        Toast.makeText(getActivity(),"Invalid Input",10).show();
                        break;
                        //emissionFromFoods();
                    }
                }
            }
        });

        return root;
    }



    public void emissionFromFoods(){
        foodEmissionsList = emissionsListRetro;
        tableFoodList = foodEmissionsList;
        for (Food emission : tableFoodList) {
            LinearLayout ll = new LinearLayout(root.getContext());
            tableRow = new TableRow(root.getContext());
            text01 = new TextView(root.getContext());
            text02 = new TextView(root.getContext());
            String foodName = emission.getFoodName();
            float foodEmissions = emission.getFoodEmissions();
            String foodEmissionStr = String.valueOf(foodEmissions);
            text01.setText(foodName);
            text02.setText(foodEmissionStr);
            tableRow.addView(text01);
            tableRow.addView(text02);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    private void cleanTable(TableLayout table, int count) {

        int childCount = table.getChildCount();

        if (childCount > count) {
            table.removeViews(count, childCount - count);
        }
    }



}

