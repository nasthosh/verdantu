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
    Boolean isItemsCalled = false;
    Activity getBaseActivity;

    public RecipeListAdapter(List<Recipe> recipeList, Context ctx, Boolean isItemsCalled) {
        this.recipeList = recipeList;
        this.ctx = ctx;
        this.isItemsCalled = isItemsCalled;
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

        if(!isItemsCalled) {
            TextView listRecipe = (TextView) convertView.findViewById(R.id.list_food_recipe);
            listRecipe.setText(recipeList.get(position).getRecipeName());
        }else{
            TextView listRecipe = (TextView) convertView.findViewById(R.id.list_food_recipe);
            TextView listRecipeEmissions = (TextView) convertView.findViewById(R.id.list_emissions_recipe);
            listRecipe.setText(recipeList.get(position).getRecipeName());
            listRecipeEmissions.setText(String.valueOf(recipeList.get(position).getRecipeEmission()));
        }
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
