package com.example.verdantu.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.verdantu.R;
import com.example.verdantu.models.FoodEmissions;
import com.example.verdantu.models.NewsModel;

import java.util.List;
import java.util.UUID;

public class FoodListAdapter extends BaseAdapter {


    List<FoodEmissions> foodsList;
    Context ctx;
    Activity getBaseActivity;

    public FoodListAdapter(List<FoodEmissions> foodsList, Context ctx) {
        this.foodsList = foodsList;
        this.ctx = ctx;
    }
    //String id = UUID.randomUUID().toString();




    public int getCount() {
        int ret = 0;
        if (foodsList != null) {
            ret = foodsList.size();
        }
        return ret;
    }


    public Object getItem(int position) {
        Object ret = null;
        if (foodsList != null) {
            ret = foodsList.get(position);
        }
        return ret;
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(ctx).inflate(R.layout.activity_add_foods_list,parent,false);

        TextView listFood = (TextView) convertView.findViewById(R.id.text_listFood);
        listFood.setText(foodsList.get(position).getFoodItems());
//        Button addButton = convertView.findViewById(R.id.addButton);
//        addButton.setBackgroundColor(Color.TRANSPARENT);
//
//
//        addButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//            }
//        });
        return convertView;
    }
}
