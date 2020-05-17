package com.example.verdantu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.verdantu.R;
import com.example.verdantu.models.Consumption;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateRawFoodListAdapter extends BaseAdapter {


    List<Consumption> rawFoodList;
    ArrayList<Consumption> tempRawFood;
    List<Consumption> suggestions;
    Context ctx;
    Activity getBaseActivity;

    public UpdateRawFoodListAdapter(List<Consumption> rawFoodList, Context ctx) {
        this.rawFoodList = rawFoodList;
        this.ctx = ctx;
    }

    public int getCount() {
        int ret = 0;
        if (rawFoodList != null) {
            ret = rawFoodList.size();
        }
        return ret;
    }


    public Object getItem(int position) {
        Object ret = null;
        if (rawFoodList != null) {
            ret = rawFoodList.get(position);
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
        listFood.setText(rawFoodList.get(position).getFoods());
        TextView listDate = convertView.findViewById(R.id.list_date);
        Date date = rawFoodList.get(position).getConsumedDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        listDate.setText(strDate);
        return convertView;
    }
}
