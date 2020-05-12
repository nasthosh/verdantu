package com.example.verdantu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.verdantu.R;
import com.example.verdantu.models.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodItemsListAdapter extends BaseAdapter {


    List<Food> foodList;
    ArrayList<Food> tempFood;
    List<Food> suggestions;
    Context ctx;
    Activity getBaseActivity;

    public FoodItemsListAdapter(List<Food> foodList, Context ctx) {
        this.foodList = foodList;
        this.ctx = ctx;
        this.tempFood = new ArrayList<Food>();
        this.tempFood.addAll(foodList);
    }

    public int getCount() {
        int ret = 0;
        if (foodList != null) {
            ret = foodList.size();
        }
        return ret;
    }


    public Object getItem(int position) {
        Object ret = null;
        if (foodList != null) {
            ret = foodList.get(position);
        }
        return ret;
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(ctx).inflate(R.layout.fragment_food_items_list,parent,false);

        TextView listFood = (TextView) convertView.findViewById(R.id.listFoodItems);
        TextView listEmissions = (TextView)convertView.findViewById(R.id.listFoodEmissions);
        listFood.setText(foodList.get(position).getFoodName());
        listEmissions.setText(String.valueOf(foodList.get(position).getFoodEmissions()));
        return convertView;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        foodList.clear();
        if (charText.length()==0){
            foodList.addAll(tempFood);
        }
        else {
            for (Food food : tempFood){
                if (food.getFoodName().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    foodList.add(food);
                }
            }
        }
        notifyDataSetChanged();
    }
}
