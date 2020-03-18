package com.atyume.ibabym.ui.notifications;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String userName;

    public NotificationsViewModel() {
        /*SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId", 0L);*/
        mText = new MutableLiveData<>();
        mText.setValue("111");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}