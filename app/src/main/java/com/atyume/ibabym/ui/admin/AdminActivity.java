package com.atyume.ibabym.ui.admin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.AdminViewAdapter;
import com.atyume.ibabym.adapter.MainViewAdapter;
import com.atyume.ibabym.listener.OnTabSelectedListener;
import com.atyume.ibabym.ui.dashboard.DashboardFragment;
import com.atyume.ibabym.ui.home.HomeFragment;
import com.atyume.ibabym.ui.notifications.NotificationsFragment;
import com.atyume.ibabym.ui.order.OrderFragment;
import com.atyume.ibabym.ui.widget.Tab;
import com.atyume.ibabym.ui.widget.TabContainerView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化状态栏

        setContentView(R.layout.activity_main);
        TabContainerView tabContainerView = (TabContainerView) findViewById(R.id.tab_container);
        AdminViewAdapter adminViewAdapter =new AdminViewAdapter(getSupportFragmentManager(),
                new Fragment[] {new HomeFragment(), new AdminSelfFragment()});
        tabContainerView.setAdapter(adminViewAdapter);
        tabContainerView.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {

            }
        });
    }

}

