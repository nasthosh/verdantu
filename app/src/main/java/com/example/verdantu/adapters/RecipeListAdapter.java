package com.example.verdantu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.verdantu.R;
import com.example.verdantu.models.Recipe;

import java.util.List;

public class RecipeListAdapter extends BaseAdapter {


    List<Recipe> recipeList;
    List<Recipe> searchRecipe;
    Context ctx;
    Activity getBaseActivity;
    EditText inputSearch;

    public RecipeListAdapter(List<Recipe> recipeList, Context ctx) {
        this.recipeList = recipeList;
        this.ctx = ctx;
    }

    public int getCount() {
        int ret = 0;
        if (recipeList != null) {
            ret = recipeList.size();
        }
        return ret;
    }


    public Object getItem(int position) {
        Object ret = null;
        if (recipeList != null) {
            ret = recipeList.get(position);
        }
        return ret;
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(ctx).inflate(R.layout.activity_recipe_list,parent,false);

        TextView listRecipe = (TextView) convertView.findViewById(R.id.text_listRecipe);
        listRecipe.setText(recipeList.get(position).getRecipeName());

        return convertView;
    }
}
