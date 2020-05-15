package com.example.verdantu.ui.food;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.verdantu.R;
import com.example.verdantu.adapters.FoodItemsListAdapter;
import com.example.verdantu.adapters.RecipeListAdapter;
import com.example.verdantu.modelinterfaces.GetService;
import com.example.verdantu.models.Food;
import com.example.verdantu.models.Recipe;
import com.example.verdantu.rest.RetrofitClientInstance;
import com.example.verdantu.ui.viewmodels.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * This class is the fragment for listing the food items in the bottom navigation activity
 *
 *
 */

public class FoodFragment extends Fragment {

    private FoodViewModel foodViewModel;
    View root;
    List<Food> foodEmissionsList;
    List<Food> tableFoodList;
    List<Food> emissionsListRetro;
    FoodItemsListAdapter listAdapter;
    ListView listView;
    RadioGroup foodType;
    int radioID;
    RadioButton checkedButton;
    String foodTypeString;
    List<Recipe> emissionListByRecipe;
    List<Recipe> recipeItems;
    List<Recipe> recipeList;
    RecipeListAdapter listViewDataAdapter;
    SearchView search;
    Boolean isDialogShown = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodViewModel =
                ViewModelProviders.of(this).get(FoodViewModel.class);
        root = inflater.inflate(R.layout.fragment_food_items, container, false); // Inflating the view model


        listView = root.findViewById(R.id.listFoodDetails);

        foodType = root.findViewById(R.id.radioCategory);
        search = root.findViewById(R.id.inputSearchFragment);

        foodType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                radioID = foodType.getCheckedRadioButtonId();
                checkedButton = root.findViewById(radioID);
                foodTypeString = checkedButton.getText().toString();
                if (foodTypeString.equalsIgnoreCase("By Raw Food")) {
                    //isRecipeCalled=false;
                    getFoodList();
                    search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            System.out.println(" Coming inside search 1 " );
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            if (TextUtils.isEmpty(s)){
                                listAdapter.filter("");
                                listView.clearTextFilter();
                            }
                            else {
                                listAdapter.filter(s);
                            }
                            return true;
                        }
                    });


                } else if (foodTypeString.equalsIgnoreCase("By Recipe")) {
                    //isRecipeCalled=true;
                    getRecipeList();
                    search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            System.out.println(" Coming inside search 1 " );
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            if (TextUtils.isEmpty(s)){
                                listViewDataAdapter.filter("");
                                listView.clearTextFilter();
                            }
                            else {
                                listViewDataAdapter.filter(s);
                            }
                            return true;
                        }
                    });


                }
            }
        });
        return root;
    }

    public void onResume() {

        super.onResume();
        if(!isDialogShown) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_map)
                    .setTitle("Info").setMessage("Please select the category to show the list of items based on the category that will be selected!")
                    .setPositiveButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getActivity(), "Thanks", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
            isDialogShown = true;
        }


    }

    public void getFoodList(){
        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Food>> call = service.getFood();
        emissionsListRetro = new ArrayList<>();
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                emissionsListRetro = response.body();
                System.out.println("List from retrofit : " + emissionsListRetro);
                emissionFromFoods();
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Toast.makeText(root.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void getRecipeList()
    {
        GetService service = RetrofitClientInstance.getRetrofitInstance().create(GetService.class);
        Call<List<Recipe>> call = service.getRecipeEmissions();
        emissionListByRecipe = new ArrayList<>();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                emissionListByRecipe = response.body();
                System.out.println("List from retrofit in recipe activity : " + emissionListByRecipe);
                recipeItems();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(root.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                System.out.println(" Throwable error : " + t);
            }
        });
    }

    public void emissionFromFoods() {
        foodEmissionsList = emissionsListRetro;
        tableFoodList = foodEmissionsList;
        listAdapter = new FoodItemsListAdapter(tableFoodList, getActivity());
        listView.setClickable(true);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                float foodProtein = tableFoodList.get(i).getFoodProtein();
                float foodFat = tableFoodList.get(i).getFoodFat();
                float foodCarbs = tableFoodList.get(i).getFoodCarbs();
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_map)
                        .setTitle("Info").setMessage("Carbs : " + String.valueOf(foodCarbs) + "\n" + "Proteins : " + String.valueOf(foodProtein) + "\n" + "Fats : " + String.valueOf(foodFat))
                        .setPositiveButton("Return", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(getActivity(), "Thanks", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
            });

        }

    public void recipeItems() {
        recipeItems = emissionListByRecipe;
        recipeList = recipeItems;
        listViewDataAdapter = new RecipeListAdapter(recipeList, getActivity(),true);
        listView.setClickable(true);
        listView.setAdapter(listViewDataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                float foodProtein = recipeList.get(i).getFoodProtein();
                float foodFat = recipeList.get(i).getFoodFat();
                float foodCarbs = recipeList.get(i).getFoodCarbs();
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_map)
                        .setTitle("Info").setMessage("Carbs : " + String.valueOf(foodCarbs) + "\n" + "Proteins : " + String.valueOf(foodProtein) + "\n" + "Fats : " + String.valueOf(foodFat))
                        .setPositiveButton("Return", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(getActivity(), "Thanks", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }

}

