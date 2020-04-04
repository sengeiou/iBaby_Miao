package com.atyume.ibabym.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.Model.AdminModel;
import com.atyume.ibabym.Model.ParentModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.ui.admin.AdminActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener {
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
    @BindView(R.id.login_user)
    TextView loginIdentify;
    @BindView(R.id.change_admin)
    RelativeLayout mbtnChangeAdmin;
    String userTell,userPwd;

    AdminModel adminModel = new AdminModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginIdentify.setText("普通用户");
        mBtnLogin.setOnClickListener(this);
        mBtnGoRegister.setOnClickListener(this);
        mBtnLoginError.setVisibility(View.INVISIBLE);
        mbtnChangeAdmin.setOnClickListener(this);

    }
    public void onClick(View view) {
        userTell = mEdtLoginTell.getText().toString();
        userPwd = mEdtLoginPwd.getText().toString();
        switch (view.getId()) {
            case R.id.btn_login:
                login(userTell,userPwd);
                break;
            case R.id.btn_go_register:
                goToRegister();
                break;
            case R.id.change_admin:
                changeAdmin();
                break;
            default:
                break;
        }
    }
    private void myToast(final String s) {
        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(AdminLogin.this, "" + s, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
    }
    protected void login(String userTell, String userPwd){
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!judgePhone()){
                    return;
                }
                if(TextUtils.isEmpty(userPwd)){
                    myToast("请输入密码");
                    return;
                }
                if(!adminModel.judgeHaveUser(userTell)){
                    myToast("该账号还没注册");
                    return;
                }
                if(!adminModel.judgeTellPwd(userTell,userPwd)){
                    myToast("密码输入错误");
                    return;
                }
                else{
                    Long userId = adminModel.getUserId(userTell);
                    //保存登陆状态到SharedPreferences中
                    saveLoginStatus(false,userId);      //不是家长所以为false
                    myToast(userId+"成功");
                    Intent intent = new Intent(AdminLogin.this, AdminActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    protected void goToRegister(){
        mBtnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myToast("点击了注册");
                Intent intent = new Intent(AdminLogin.this, AdminRegister.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    protected void changeAdmin(){
        mbtnChangeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myToast("前往用户页面");
                Intent intent = new Intent(AdminLogin.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean judgePhone()
    {
        if(TextUtils.isEmpty(mEdtLoginTell.getText().toString().trim()))
        {
            myToast("请输入您的电话号码");
            mEdtLoginTell.requestFocus();
            return false;
        }
        else if(mEdtLoginTell.getText().toString().trim().length()!=11)
        {
            myToast("您的电话号码位数不正确");
            mEdtLoginTell.requestFocus();
            return false;
        }
        else
        {
            userTell=mEdtLoginTell.getText().toString().trim();
            String num="[1][358]\\d{9}";
            if(userTell.matches(num))
                return true;
            else
            {
                myToast("请输入正确的手机号码");
                return false;
            }
        }
    }

    /**
     *保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status,long userId){
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isParent", status);
        //存入登录状态时的用户名
        editor.putLong("loginUserId", userId);
        //提交修改
        editor.apply();
    }

    /**
     * 注册成功的数据返回至此
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_user_name 控件
                mEdtLoginTell.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                mEdtLoginTell.setSelection(userName.length());
            }
        }
    }

}
