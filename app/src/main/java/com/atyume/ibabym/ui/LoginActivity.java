package com.atyume.ibabym.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.atyume.ibabym.utils.MD5Utils;

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
                if(TextUtils.isEmpty(userTell)){
                    Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userPwd)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!judgeHaveUser(userTell)){
                    Toast.makeText(LoginActivity.this, "该账号还没注册", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!judgeTellPwd(userTell,userPwd)){
                    Toast.makeText(LoginActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Long userId = getUserId(userTell);
                    //保存登陆状态到SharedPreferences中
                    saveLoginStatus(true,userId);
                    Toast.makeText(LoginActivity.this, userId+"成功", Toast.LENGTH_SHORT).show();
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
                startActivityForResult(intent, 1);
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
        //加密
        String md5Pwd = MD5Utils.md5(userPwd);
        return parentInfo.getParentPwd().equals(md5Pwd);
    }
    private Long getUserId(String userTell){
        ParentInfo parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).unique();
        return parentInfo.getId();
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
        editor.putBoolean("isLogin", status);
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
