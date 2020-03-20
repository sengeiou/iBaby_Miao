package com.atyume.ibabym.ui.notifications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ParentInfoDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ParentInfo;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditUser extends AppCompatActivity {

    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.button_modify_myself)
    QMUIRoundButton mbtnModifyMyself;

    @BindView(R.id.edit_myNick)
    EditText mEditUserNick;
    @BindView(R.id.edit_myName)
    EditText mEditUserName;
    @BindView(R.id.edit_myWork)
    EditText mEditUserWork;

    String userNick,userName,userWork;

    private ParentInfoDao parentDao = MyApplication.getInstances().getDaoSession().getParentInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmyself);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUser.this.finish();
            }
        });

        mbtnModifyMyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();
                modifyUser(userId,userNick,userName,userWork);
                Toast.makeText(EditUser.this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditUser.this, ViewUser.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void modifyUser(Long userId,String userNick,  String userName, String userWork){
        ParentInfo parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.Id.eq(userId)).unique();
        if(TextUtils.isEmpty(userNick)){
            userNick = parentInfo.getParentTell();
        }
        parentInfo.setParentName(userName);
        parentInfo.setParentNick(userNick);
        parentInfo.setParentWorkAdress(userWork);
        parentDao.update(parentInfo);
    }
    private void getEditText(){
        userName = mEditUserName.getText().toString();
        userNick = mEditUserNick.getText().toString();
        userWork = mEditUserWork.getText().toString();
    }
}
