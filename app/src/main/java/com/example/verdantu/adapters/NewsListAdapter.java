package com.example.verdantu.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.verdantu.R;
import com.example.verdantu.models.NewsModel;

import java.util.List;
import java.util.UUID;

import android.view.LayoutInflater;


public class NewsListAdapter extends BaseAdapter {

    List<NewsModel> newsList;
    Context ctx;
    String id = UUID.randomUUID().toString();



    public NewsListAdapter(List<NewsModel> newsList, Context ctx) {
        this.newsList = newsList;
        this.ctx = ctx;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(ctx).inflate(R.layout.activity_list_row,parent,false);

        TextView listTitle = (TextView) convertView.findViewById(R.id.text_title);
        listTitle.setText(newsList.get(position).getTitle());
        Button urlButton = convertView.findViewById(R.id.urlButton);
        urlButton.setBackgroundColor(Color.TRANSPARENT);

        urlButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(String.valueOf(newsList.get(position).getUrl())));
                ctx.startActivity(intent);
            }
        });
        return convertView;
    }
}

