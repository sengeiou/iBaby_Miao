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
import com.atyume.ibabym.ui.LoginActivity;
import com.atyume.ibabym.utils.MD5Utils;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPassword extends AppCompatActivity {

    @BindView(R.id.comeBack)
    TextView mComeBack;
    @BindView(R.id.edit_oldPwd)
    EditText mEditOldPwd;
    @BindView(R.id.edit_newPwd)
    EditText mEditNewPwd;
    @BindView(R.id.edit_newRePwd)
    EditText mEditNewRePwd;
    @BindView(R.id.button_modifyPwd)
    QMUIRoundButton mbtnModifyPwd;

    String oldPwd,newPwd,newRePwd;

    private ParentInfoDao parentDao = MyApplication.getInstances().getDaoSession().getParentInfoDao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpwd);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);
        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditPassword.this.finish();
            }
        });


        mbtnModifyPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();
                if (TextUtils.isEmpty(oldPwd)){
                    Toast.makeText(EditPassword.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(newPwd)||TextUtils.isEmpty(newRePwd)){
                    Toast.makeText(EditPassword.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!judgePwd(userId, oldPwd)){
                    Toast.makeText(EditPassword.this, "旧密码输入有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    if(!newPwd.equals(newRePwd)){
                        Toast.makeText(EditPassword.this, "两次新密码不符", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        judgeUpdatePwd(userId, newPwd);
                        Toast.makeText(EditPassword.this, "修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditPassword.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }
    private void getEditText(){
        oldPwd = mEditOldPwd.getText().toString();
        newPwd = mEditNewPwd.getText().toString();
        newRePwd = mEditNewRePwd.getText().toString();
    }

    //判断旧密码是否正确
    private boolean judgePwd(Long userId, String userPwd){
        ParentInfo parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.Id.eq(userId)).unique();
        String md5Pwd = MD5Utils.md5(userPwd);
        return md5Pwd.equals(parentInfo.getParentPwd());
    }

    protected void judgeUpdatePwd(Long userId, String userNewPwd){
        ParentInfo parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.Id.eq(userId)).unique();
        String md5Pwd = MD5Utils.md5(userNewPwd);
        parentInfo.setParentPwd(md5Pwd);
        parentDao.update(parentInfo);
    }
}
