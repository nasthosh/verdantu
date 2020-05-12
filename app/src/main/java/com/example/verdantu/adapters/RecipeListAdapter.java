package com.example.verdantu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.verdantu.R;
import com.example.verdantu.models.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeListAdapter extends BaseAdapter {


    List<Recipe> recipeList;
    ArrayList<Recipe> tempRecipe;
    List<Recipe> suggestions;
    Context ctx;
    Activity getBaseActivity;

    public RecipeListAdapter(List<Recipe> recipeList, Context ctx) {
        this.recipeList = recipeList;
        this.ctx = ctx;
        this.tempRecipe = new ArrayList<Recipe>();
        this.tempRecipe.addAll(recipeList);
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

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        recipeList.clear();
        if (charText.length()==0){
            recipeList.addAll(tempRecipe);
        }
        else {
            for (Recipe recipe : tempRecipe){
                if (recipe.getRecipeName().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    recipeList.add(recipe);
                }
            }
        }
        notifyDataSetChanged();
    }
}
