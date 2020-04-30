package com.example.verdantu.ui.food;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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
import com.example.verdantu.models.FoodEmissions;
import com.example.verdantu.rest.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    List<FoodEmissions> foodEmissionsList;
    List<FoodEmissions> tableFoodList;
    int count = 0;
    String editTextFood;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodViewModel =
                ViewModelProviders.of(this).get(FoodViewModel.class);
        root = inflater.inflate(R.layout.fragment_food_items, container, false); // Inflating the view model

        tableLayout = root.findViewById(R.id.TableLayout01); // Identifying layouts using the corresponding ID's

        FoodItemsAsyncTask getFoodItem = new FoodItemsAsyncTask(); // Calling the asynctask method to get the data from the database
        getFoodItem.execute();

        editText = root.findViewById(R.id.editText);
        search = root.findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View v) {  // Defining the button on click activity for search
                for (FoodEmissions foodItem : foodEmissionsList) {
                    System.out.println("Entering search food item : ");
                    editTextFood = editText.getText().toString();
                    System.out.println("Food Name searched: " + editTextFood);
                    if(foodItem.getFoodItems().equalsIgnoreCase(editTextFood)){ // Checking if the searched item is present in the list
                        System.out.println("Searched Item : " + editTextFood);
                        tableRow = new TableRow(root.getContext());

                        text01 = new TextView(root.getContext());
                        text02 = new TextView(root.getContext());

                        String foodName = foodItem.getFoodItems();
                        String foodEmissions = foodItem.getCarbonEmissions();

                        //Clean the Table after every search
                        cleanTable(tableLayout,1);

                        text01.setText(foodName);
                        text02.setText(foodEmissions);

                        // Add the data to the view
                        tableRow.addView(text01);
                        tableRow.addView(text02);
                        tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    }else if(editTextFood.equalsIgnoreCase("")  ){
                        cleanTable(tableLayout,0);
                        Toast.makeText(getActivity(),"Invalid Input",10).show();
                    }
                }
            }
        });

        return root;
    }

    public class FoodItemsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.getItemsList();
        } // Rest client call to send request to the server

        protected void onPostExecute(String result) {
            emissionFromFoods(result);
        }
    }

    public List<FoodEmissions> getFoodItems(String result) {
        List<FoodEmissions> emissionsList = new ArrayList<>();
        try {
            // Converting the result to a JSON object
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("recordset");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject foodEmissionsList = jsonArray.getJSONObject(i);
                    String foodName = foodEmissionsList.getString("Foods");
                    String foodEmission = foodEmissionsList.getString("Emissions");
                    FoodEmissions foodItemsEmissions = new FoodEmissions(foodName, foodEmission);
                    emissionsList.add(foodItemsEmissions);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Emission from food in JSOn : " + emissionsList);
        return emissionsList;
    }

    public void emissionFromFoods(String result){
        foodEmissionsList = getFoodItems(result);
        tableFoodList = foodEmissionsList;
        for (FoodEmissions emission : tableFoodList) {
            LinearLayout ll = new LinearLayout(root.getContext());
            tableRow = new TableRow(root.getContext());
            text01 = new TextView(root.getContext());
            text02 = new TextView(root.getContext());
            String foodName = emission.getFoodItems();
            String foodEmissions = emission.getCarbonEmissions();
            text01.setText(foodName);
            text02.setText(foodEmissions);
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

