package com.atyume.ibabym.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.atyume.ibabym.R;
import com.atyume.ibabym.ui.LoginActivity;
import com.atyume.ibabym.ui.dashboard.ViewBabyInfo;
import com.atyume.ibabym.ui.navbar_title.nav_bar;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    @BindView(R.id.mine_topbar)
    QMUITopBar mbtnMineTopBar;
    @BindView(R.id.user_info)
    nav_bar mbtUserInfo;
    @BindView(R.id.edit_pwd)
    nav_bar mbtnEditPwd;
    @BindView(R.id.user_baby)
    nav_bar mbtnUserBaby;
    @BindView(R.id.logout)
    QMUIButton mbtnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this,root);
        initTopBar();

        mbtUserInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewUser.class);
                startActivity(intent);
            }
        });

        mbtnEditPwd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditPassword.class);
                startActivity(intent);
            }
        });

        mbtnUserBaby.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewBabyInfo.class);
                startActivity(intent);
            }
        });

        mbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        /*final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
    private void initTopBar(){
        mbtnMineTopBar.setTitle("我的");
    }

}