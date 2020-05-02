package com.example.verdantu.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 *
 * View Model for the items fragment
 *
 *
 */

public class FoodViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FoodViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}