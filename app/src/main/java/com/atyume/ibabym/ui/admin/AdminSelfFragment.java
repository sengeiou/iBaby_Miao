package com.atyume.ibabym.ui.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.atyume.ibabym.Model.AdminModel;

import com.atyume.ibabym.R;
import com.atyume.ibabym.ui.AdminLogin;

import com.atyume.ibabym.ui.navbar_title.nav_bar;
import com.atyume.ibabym.ui.notifications.EditPassword;

import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class AdminSelfFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    AdminModel adminModel = new AdminModel();

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

    private String userName = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, root);
        initTopBar();
        initUser();

        mbtnEditPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditPassword.class);
                startActivity(intent);
            }
        });

        //退出登录
        mbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminLogin.class);
                startActivity(intent);
                //清除
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
            }
        });

        return root;
    }

    private void initTopBar() {
        mbtnMineTopBar.setTitle("个人中心");
        mbtUserInfo.setVisibility(View.GONE);
        mbtnUserBaby.setVisibility(View.GONE);
    }

    private void initUser() {
        userName = adminModel.getAdminTell(getUserId());
        String adminName = "管理员" + userName;
        mshowUserName.setText(adminName);
    }

    private Long getUserId() {
        sharedPreferences = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId", 0L);
        return userId;
    }
}