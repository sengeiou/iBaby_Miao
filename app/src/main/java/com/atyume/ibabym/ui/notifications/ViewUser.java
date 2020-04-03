package com.atyume.ibabym.ui.notifications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.atyume.greendao.gen.ParentInfoDao;
import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.Model.ParentModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ParentInfo;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewUser extends AppCompatActivity {

    @BindView(R.id.comeBack)
    TextView mComeBack;
    @BindView(R.id.edit_myself)
    TextView mEditMyself;

    @BindView(R.id.show_myNick)
    TextView mShowMyNick;
    @BindView(R.id.show_myName)
    TextView mShowMyName;
    @BindView(R.id.show_myTell)
    TextView mShowMyTell;
    @BindView(R.id.show_myWork)
    TextView mShowMyWork;

    ParentModel parentModel = new ParentModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewuser);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);

        initView(userId);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUser.this.finish();
            }
        });

        mEditMyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewUser.this, EditUser.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initView(Long userId){
        ParentInfo parentInfo = new ParentInfo();
        parentInfo = parentModel.selectById(userId);
        mShowMyNick.setText(parentInfo.getParentNick());
        mShowMyName.setText(parentInfo.getParentName());
        mShowMyTell.setText(parentInfo.getParentTell());
        mShowMyWork.setText(parentInfo.getParentWorkAdress());
    }



}
