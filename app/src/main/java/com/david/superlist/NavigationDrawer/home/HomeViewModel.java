package com.david.superlist.NavigationDrawer.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to our app!");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setTextUserName(String userName) {
        mText.setValue(userName + ", welcome to SuperList!");
    }
}