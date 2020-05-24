package com.example.verdantu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.verdantu.R;
import com.example.verdantu.models.Recommendation;

import java.util.ArrayList;
import java.util.List;

public class RecommendationListAdapter extends BaseAdapter {


    List<Recommendation> foodList;
    ArrayList<Recommendation> tempFood;
    List<Recommendation> suggestions;
    Context ctx;
    Activity getBaseActivity;

    public RecommendationListAdapter(List<Recommendation> foodList, Context ctx) {
        this.foodList = foodList;
        this.ctx = ctx;
        this.tempFood = new ArrayList<Recommendation>();
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.activity_recommendation_list,parent,false);

        TextView listYourFood = (TextView) convertView.findViewById(R.id.list_food_consumed);
        TextView listRecommendedFood = (TextView)convertView.findViewById(R.id.list_food_recommended);
        listYourFood.setText(foodList.get(position).getConsumedFood());
        listRecommendedFood.setText(foodList.get(position).getRecommendedFood());
        //listFood.setText(foodList.get(position).getFoodName());
        //listEmissions.setText(String.valueOf(foodList.get(position).getFoodEmissions()));
        return convertView;
    }

//    public void filter(String charText){
//        charText = charText.toLowerCase(Locale.getDefault());
//        foodList.clear();
//        if (charText.length()==0){
//            foodList.addAll(tempFood);
//        }
//        else {
//            for (Recommendation food : tempFood){
//                if (food.getFoodName().toLowerCase(Locale.getDefault())
//                        .contains(charText)){
//                    foodList.add(food);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
}
