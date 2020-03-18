package com.atyume.ibabym.ui.notifications;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.atyume.ibabym.R;
import com.atyume.ibabym.ViewModel.ViewUserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewuser);
        ButterKnife.bind(this);

    }

}
