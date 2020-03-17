package com.atyume.ibabym.ui.dashboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atyume.ibabym.Dao.DaoUtils;
import com.atyume.ibabym.basics.Inoculation;

public class ViewBabyModel extends ViewModel {

    private MutableLiveData<String> mText;

    private final String TAG = ViewBabyInfo.class.getSimpleName();
    DaoUtils mdaoUtils;

    public ViewBabyModel() {

        Inoculation baby = mdaoUtils.queryMeiziById(1001L);
        Log.i(TAG, mdaoUtils.queryMeiziById(1001L).toString());

        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
