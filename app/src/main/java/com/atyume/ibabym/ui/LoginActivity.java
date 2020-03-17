package com.atyume.ibabym.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ParentInfoDao;
import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ParentInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_go_register)
    Button mBtnGoRegister;
    @BindView(R.id.login_error)
    Button mBtnLoginError;

    @BindView(R.id.edt_loginTell)
    EditText mEdtLoginTell;
    @BindView(R.id.edt_loginPwd)
    EditText mEdtLoginPwd;

    String userTell,userPwd;

    private ParentInfoDao parentDao = MyApplication.getInstances().getDaoSession().getParentInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mBtnLogin.setOnClickListener(this);
        mBtnGoRegister.setOnClickListener(this);
        mBtnLoginError.setOnClickListener(this);

    }
    public void onClick(View view) {
        userTell = mEdtLoginTell.getText().toString();
        userPwd = mEdtLoginPwd.getText().toString();
        switch (view.getId()) {
            // 跳转到注册界面
            case R.id.btn_login:
                login(userTell,userPwd);
                break;
            case R.id.btn_go_register:
                goToRegister();
                break;
            case R.id.login_error:
                loginError();
                break;
            default:
                break;
        }
    }
    protected void login(String userTell, String userPwd){
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userTell==null||userTell.equals("")){
                    Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                }
                if(userPwd==null||userPwd.equals("")){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }
                if(!judgeHaveUser(userTell)){
                    Toast.makeText(LoginActivity.this, "该账号还没注册", Toast.LENGTH_SHORT).show();
                }
                if(!judgeTellPwd(userTell,userPwd)){
                    Toast.makeText(LoginActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, userPwd+"成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    protected void goToRegister(){
        mBtnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "点击了注册", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    protected void loginError(){
        mBtnLoginError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "点击了忘记密码", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, ResetPwd.class);
                startActivity(intent);
                finish();
            }
        });
    }
    protected boolean judgeHaveUser(String userTell){
        /*if(parentDao.load())*/
        List<ParentInfo> parentList = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).list();
       if(parentList.size()==0){
            return false;                 //没有注册过
        }
        return true;                      //注册过
    }
    protected boolean judgeTellPwd(String userTell, String userPwd){
        ParentInfo parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).unique();
        Log.d("parentInfo:",parentInfo.getId()+" "+parentInfo.getParentPwd());
        return parentInfo.getParentPwd().equals(userPwd);
    }

}
