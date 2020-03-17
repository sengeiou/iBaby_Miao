package com.atyume.ibabym.ui;

import android.content.Intent;
import android.os.Bundle;
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
                userTell = mEdtTell.getText().toString();
                userPwd = mEdtSetPwd.getText().toString();
                userRePwd = mEdtConfirmPwd.getText().toString();
                verifyCode = mEdtVerify.getText().toString();

                if(userTell==null||userTell.equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                if(userPwd==null||userPwd.equals("")||userRePwd==null||userRePwd.equals("")){
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                if(verifyCode==null||verifyCode.equals("")){
                    Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                if(!userPwd.equals(userRePwd)){
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                }
                if(judgeHaveUser(userTell)){
                    Toast.makeText(RegisterActivity.this, "该账号已存在", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "点击了注册", Toast.LENGTH_SHORT).show();
                    parentDao.insert(new ParentInfo(userTell,userPwd));
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);//返回页面1
                    finish();
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
    protected boolean judgeHaveUser(String userTell){
        /*if(parentDao.load())*/
        List<ParentInfo> parentList = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).list();
        if(parentList.size()==0){
            return false;                 //没有注册过
        }
        return true;                      //注册过
    }
}