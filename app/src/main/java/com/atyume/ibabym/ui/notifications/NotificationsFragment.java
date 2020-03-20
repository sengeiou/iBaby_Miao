package com.atyume.ibabym.ui.notifications;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.atyume.greendao.gen.ParentInfoDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ParentInfo;
import com.atyume.ibabym.ui.LoginActivity;
import com.atyume.ibabym.ui.dashboard.ViewBabyInfo;
import com.atyume.ibabym.ui.navbar_title.nav_bar;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class NotificationsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private ParentInfoDao parentDao = MyApplication.getInstances().getDaoSession().getParentInfoDao();
    /*private NotificationsViewModel notificationsViewModel;*/

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

    @BindView(R.id.show_UserName)
    TextView mshowUserName;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this,root);
        initTopBar();

        sharedPreferences = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);
        initUser(getUserNick(userId));

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

        //退出登录
        mbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                //清除
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
            }
        });

        /*notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mshowUserName.setText(s);
            }
        });*/
        return root;
    }
    private void initTopBar(){
        mbtnMineTopBar.setTitle("我的");
    }
    private void initUser(String userName){
        mshowUserName.setText(userName);
    }
    private String getUserNick(Long userId){
        ParentInfo parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.Id.eq(userId)).unique();
        if (parentInfo.getParentNick()==null || parentInfo.getParentNick().equals("")){
            return parentInfo.getParentTell();
        }
        return parentInfo.getParentNick();
    }
}