package com.example.verdantu.ui.notifications;

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
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    Button news;
    List<NewsModel> newsList;
    List<NewsModel> newsArticleList;
    ListView listViewWithCheckbox;
    NewsListAdapter listViewDataAdapter;
    TableLayout tableLayout;
    TableRow tableRow;
    TextView text01;

    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        tableLayout = root.findViewById(R.id.TableLayout01);
//        final TextView textView = root.findViewById(R.id.textNotifications);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

//        news = root.findViewById(R.id.news);
//        news.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewsAsyncTask getFoodItem = new NewsAsyncTask(); // Calling the asynctask method to get the data from the database
//                getFoodItem.execute();
//            }
//        });

        //ArrayAdapter<String> listDataAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_row,R.id.list_view_item_text,newsArticleList);

        NewsAsyncTask getFoodItem = new NewsAsyncTask(); // Calling the asynctask method to get the data from the database
        getFoodItem.execute();

        return root;
    }

    public class NewsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return NewsAPI.getNews();
        } // Rest client call to send request to the server

        protected void onPostExecute(String result) {
            getRelatedNewsArticles(result);
        }
    }

    public void getRelatedNewsArticles(String result){
        newsArticleList = getNewsArticles(result);
        for (NewsModel articleList : newsArticleList) {
            tableRow = new TableRow(root.getContext());
            text01 = new TextView(root.getContext());
            String title = articleList.getTitle();
            String description = articleList.getDescription();
            text01.setText(title);
            text01.setText(description);
            tableRow.addView(text01);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
//       // for (NewsModel articleList : newsArticleList) {
//            listViewWithCheckbox = root.findViewById(R.id.list);
//            listViewDataAdapter = new NewsListAdapter(newsArticleList);
//            listViewWithCheckbox.setAdapter(listViewDataAdapter);

    }

    public List<NewsModel> getNewsArticles(String result) {
        System.out.println("Get news aricle inside JSON : " + result);
        newsList = new ArrayList<>();
        URL newURL;
       // URL newURLImage;
        try {
            // Converting the result to a JSON object
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject NewsModelList = jsonArray.getJSONObject(i);
                    String articleTitle = NewsModelList.getString("title");
                    String articleDescription = NewsModelList.getString("description");
                    String articleURL = NewsModelList.getString("url");

                    String articleURLImage = NewsModelList.getString("urlToImage");
                    newURL = new URL(articleURL);
                    //newURLImage = new URL (articleURLImage);
                    //Image urlImage = ImageIo.read(newURLImage);
                    NewsModel newsArticle = new NewsModel(articleTitle, articleDescription, newURL );
                   // NewsModel newsArticle = new NewsModel(articleTitle, articleDescription, newURL, newURLImage );
                    newsList.add(newsArticle);
                }
            }
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("Returning news list : " + newsList);
        return newsList;
    }

}
