package com.atyume.ibabym;

import android.os.Bundle;

import com.atyume.ibabym.adapter.MainViewAdapter;
import com.atyume.ibabym.listener.OnTabSelectedListener;
import com.atyume.ibabym.ui.dashboard.DashboardFragment;
import com.atyume.ibabym.ui.home.HomeFragment;
import com.atyume.ibabym.ui.notifications.NotificationsFragment;
import com.atyume.ibabym.ui.widget.Tab;
import com.atyume.ibabym.ui.widget.TabContainerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabContainerView tabContainerView = (TabContainerView) findViewById(R.id.tab_container);
        MainViewAdapter mainViewAdapter=new MainViewAdapter(getSupportFragmentManager(),
                new Fragment[] {new HomeFragment(), new DashboardFragment(),new NotificationsFragment()});
        mainViewAdapter.setHasMsgIndex(3);
        tabContainerView.setAdapter(mainViewAdapter);
        tabContainerView.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {

            }
        });
    }

}
