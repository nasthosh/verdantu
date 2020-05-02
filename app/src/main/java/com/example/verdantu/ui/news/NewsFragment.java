package com.example.verdantu.ui.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.verdantu.R;
import com.example.verdantu.adapters.NewsListAdapter;
import com.example.verdantu.models.NewsModel;
import com.example.verdantu.rest.NewsAPI;
import com.example.verdantu.ui.viewmodels.NewsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    Button news;
    List<NewsModel> newsList;
    List<NewsModel> newsArticleList;
    ListView listView;
    NewsListAdapter listViewDataAdapter;
    TableLayout tableLayout;
    TableRow tableRow;
    TextView text01;

    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        root = inflater.inflate(R.layout.fragment_news, container, false);
        listView = root.findViewById(R.id.list);

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
            listViewDataAdapter = new NewsListAdapter(newsArticleList, root.getContext());
            listView.setAdapter(listViewDataAdapter);
    }

    public List<NewsModel> getNewsArticles(String result) {
        System.out.println("Get news aricle inside JSON : " + result);
        newsList = new ArrayList<>();
        URL newURL;
        URI image;
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
                    NewsModel newsArticle = new NewsModel(articleTitle, articleDescription, newURL );
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
