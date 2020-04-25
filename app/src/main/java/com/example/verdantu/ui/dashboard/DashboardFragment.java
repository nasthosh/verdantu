package com.example.verdantu.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.verdantu.R;
import com.example.verdantu.models.FoodEmissions;
import com.example.verdantu.rest.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    TableLayout tableLayout;
    TableRow tableRow;
    TextView t1;
    TextView t2;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        root = inflater.inflate(R.layout.fragment_food_items, container, false);
        tableLayout = root.findViewById(R.id.TableLayout01);


//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        FoodItemsAsyncTask getFoodItem = new FoodItemsAsyncTask();
        getFoodItem.execute();


        return root;
    }

    public class FoodItemsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.getItemsList();
        }

        protected void onPostExecute(String result) {
            System.out.println("JSON Data : " + result);
            List<FoodEmissions> foodEmissionsList = getFoodItems(result);
            for (FoodEmissions emission : foodEmissionsList) {
                LinearLayout ll = new LinearLayout(root.getContext());

                tableRow = new TableRow(root.getContext());

                t1 = new TextView(root.getContext());
                t2 = new TextView(root.getContext());
                String foodName = emission.getFoodItems();
                String foodEmissions = emission.getCarbonEmissions();
                t1.setText(foodName);
                t2.setText(foodEmissions);
                ll.addView(t1);
                ll.addView(t2);
                tableRow.addView(ll);
               // tableLayout.addView(tableRow);
                tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }

            //tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    public List<FoodEmissions> getFoodItems(String result) {
        List<FoodEmissions> emissionsList = new ArrayList<>();
        try {
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
        System.out.println("Emissions list inside method : " + emissionsList);
        return emissionsList;
    }
}

//    public  void listTable(String result){
//        List<FoodEmissions> foodEmissionList = getFoodItems(result);
//        for(FoodEmissions emission : foodEmissionList){
//            t1 = new TextView(root.getContext());
//            t2 = new TextView(root.getContext());
//            String foodName = emission.getFoodItems();
//            String foodEmissions = emission.getCarbonEmissions();
//            t1.setText(foodName);
//            t2.setText(foodEmissions);
//            tableRow.addView(t1);
//            tableRow.addView(t2);
//
//        }
//        tableLayout.addView(tableRow,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
//    }
