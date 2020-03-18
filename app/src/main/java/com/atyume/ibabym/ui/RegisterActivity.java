package com.atyume.ibabym.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.edt_setusertell)
    EditText mEdtTell;
    @BindView(R.id.edt_verifycode)
    EditText mEdtVerify;
    @BindView(R.id.button_getverifycode)
    QMUIRoundButton mbtnGetVerify;
    @BindView(R.id.edt_setpassword)
    EditText mEdtSetPwd;
    @BindView(R.id.edt_confirm_password)
    EditText mEdtConfirmPwd;
    @BindView(R.id.btn_goBackLogin)
    Button mbtnBackLogin;

    String userTell,userPwd,userRePwd;
    String verifyCode;

    private ParentInfoDao parentDao = MyApplication.getInstances().getDaoSession().getParentInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditText();
                if(TextUtils.isEmpty(userTell)){
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userPwd)||TextUtils.isEmpty(userRePwd)){
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(verifyCode)){
                    Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!userPwd.equals(userRePwd)){
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(judgeHaveUser(userTell)){
                    Toast.makeText(RegisterActivity.this, "该账号已存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Toast.makeText(RegisterActivity.this, "点击了注册", Toast.LENGTH_SHORT).show();
                    insertUser(userTell,userPwd);
                    /*Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);//返回页面1
                    finish();*/
                    //注册成功后把账号传递到LoginActivity.java中
                    // 返回值到loginActivity显示
                    Intent data = new Intent();
                    data.putExtra("userTell", userTell);
                    setResult(RESULT_OK, data);
                    //RESULT_OK为Activity系统常量，状态码为-1，
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    RegisterActivity.this.finish();
                }

                //  startActivity(intent);
            }

        });

        mbtnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void getEditText(){
        userTell = mEdtTell.getText().toString();
        userPwd = mEdtSetPwd.getText().toString();
        userRePwd = mEdtConfirmPwd.getText().toString();
        verifyCode = mEdtVerify.getText().toString();
    }
    protected boolean judgeHaveUser(String userTell){
        /*if(parentDao.load())*/
        List<ParentInfo> parentList = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).list();
        if(parentList.size()==0){
            return false;                 //没有注册过
        }
        return true;                      //注册过
    }
    private void insertUser(String userTell,String userPwd){
        //密码加密
        String md5Pwd = MD5Utils.md5(userPwd);
        parentDao.insert(new ParentInfo(userTell,md5Pwd));
    }

    /**
     * 保存账号和密码到SharedPreferences中SharedPreferences
     */
    private void saveRegisterInfo(String userName,String userPwd){
        String md5Psw = MD5Utils.md5(userPwd);//把密码用MD5加密
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, md5Psw);
        //提交修改 editor.commit();
        editor.apply();
    }
}