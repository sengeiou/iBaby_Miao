package com.atyume.ibabym;

import android.os.Bundle;

import com.atyume.ibabym.adapter.MainViewAdapter;
import com.atyume.ibabym.listener.OnTabSelectedListener;
import com.atyume.ibabym.ui.home.HomeFragment;
import com.atyume.ibabym.ui.order.OrderFragment;
import com.atyume.ibabym.ui.dashboard.DashboardFragment;
import com.atyume.ibabym.ui.notifications.NotificationsFragment;
import com.atyume.ibabym.ui.widget.Tab;
import com.atyume.ibabym.ui.widget.TabContainerView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化状态栏

        setContentView(R.layout.activity_main);
        TabContainerView tabContainerView = (TabContainerView) findViewById(R.id.tab_container);
        MainViewAdapter mainViewAdapter=new MainViewAdapter(getSupportFragmentManager(),
                new Fragment[] {new DashboardFragment(), new OrderFragment(), new NotificationsFragment()});
//        mainViewAdapter.setHasMsgIndex(3);
        tabContainerView.setAdapter(mainViewAdapter);
        tabContainerView.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {

            }
        });
    }

}
