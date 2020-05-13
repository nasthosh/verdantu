package com.example.verdantu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.verdantu.R;
import com.example.verdantu.models.RecipeConsumption;

import java.util.ArrayList;
import java.util.List;

public class UpdateRecipeListAdapter extends BaseAdapter {


    List<RecipeConsumption> recipeFoodList;
    ArrayList<RecipeConsumption> tempRawFood;
    List<RecipeConsumption> suggestions;
    Context ctx;
    Activity getBaseActivity;

    public UpdateRecipeListAdapter(List<RecipeConsumption> recipeFoodList, Context ctx) {
        this.recipeFoodList = recipeFoodList;
        this.ctx = ctx;
    }

    public int getCount() {
        int ret = 0;
        if (recipeFoodList != null) {
            ret = recipeFoodList.size();
        }
        return ret;
    }


    public Object getItem(int position) {
        Object ret = null;
        if (recipeFoodList != null) {
            ret = recipeFoodList.get(position);
        }
        return ret;
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(ctx).inflate(R.layout.activity_update_list,parent,false);

        TextView listFood = (TextView) convertView.findViewById(R.id.listFoodItemsForUpdate);
        listFood.setText(recipeFoodList.get(position).getRecipeName());
        return convertView;
    }
}
