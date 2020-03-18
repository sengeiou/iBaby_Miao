package com.atyume.ibabym.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewUserViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ViewUserViewModel(String userName) {
        mText = new MutableLiveData<>();
        mText.setValue(userName);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
