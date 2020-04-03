package com.atyume.ibabym.ui.notifications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ParentInfoDao;
import com.atyume.ibabym.Model.ParentModel;
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

    ParentModel parentModel = new ParentModel();

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
                    myToast("请输入旧密码");
                    return;
                }
                if (TextUtils.isEmpty(newPwd)||TextUtils.isEmpty(newRePwd)){
                    myToast("请输入新密码");
                    return;
                }
                if (!parentModel.judgePwd(userId, oldPwd)){
                    myToast("旧密码输入有误");
                    return;
                }
                else{
                    if(!newPwd.equals(newRePwd)){
                        myToast("两次密码输入不符");
                        return;
                    }
                    else {
                        parentModel.judgeUpdatePwd(userId, newPwd);
                        myToast("修改成功");
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
    private void myToast(final String s) {
        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(EditPassword.this, "" + s, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
    }

}
