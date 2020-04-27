package com.example.verdantu.ui.notifications;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.verdantu.R;
import com.example.verdantu.models.NewsModel;

import java.util.List;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.verdantu.AddFoods;
import com.example.verdantu.R;
import com.example.verdantu.models.NewsModel;
import com.example.verdantu.rest.NewsAPI;
import com.example.verdantu.rest.RestClient;
import com.example.verdantu.ui.dashboard.DashboardFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NewsListAdapter extends BaseAdapter {

    //private List<ListViewItemDTO> listViewItemDtoList = null;

    List<NewsModel> newsList;
    private Activity activity;

    public NewsListAdapter(List<NewsModel> newsList, Activity activity) {
        this.newsList = newsList;
        this.activity = activity;
    }

    public NewsListAdapter(List<NewsModel> newsList) {
        this.newsList = newsList;
    }


    @Override
    public int getCount() {
        int ret = 0;
        if (newsList != null) {
            ret = newsList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        Object ret = null;
        if (newsList != null) {
            ret = newsList.get(position);
        }
        return ret;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_row, null);

            TextView listItemText = (TextView) convertView.findViewById(R.id.list_view_item_text);
            NewsModel title = newsList.get(position);
            listItemText.setText(title.getTitle());
            //listItemText.setText();



        }
        return convertView;
    }
}

