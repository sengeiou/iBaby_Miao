package com.atyume.ibabym.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
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
import com.atyume.ibabym.utils.TimeCountUtil;
import com.mob.MobSDK;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.edt_setusertell)
    EditText mEdtTell;
    @BindView(R.id.edt_verifycode)
    EditText mEdtVerify;
    @BindView(R.id.button_getverifycode)
    Button mbtnGetVerify;
    @BindView(R.id.edt_setpassword)
    EditText mEdtSetPwd;
    @BindView(R.id.edt_confirm_password)
    EditText mEdtConfirmPwd;
    @BindView(R.id.btn_goBackLogin)
    Button mbtnBackLogin;

    String userTell,userPwd,userRePwd;
    String verifyCode;

    private TimeCountUtil mTimeCountUtil;
    private boolean flag = true;
    private ParentInfoDao parentDao = MyApplication.getInstances().getDaoSession().getParentInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mTimeCountUtil = new TimeCountUtil(mbtnGetVerify, 60000, 1000);

        MobSDK.init(this);

        mbtnGetVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_code(v);
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditText();
                check_code(view);

                if(TextUtils.isEmpty(userPwd)||TextUtils.isEmpty(userRePwd)){
                    myToast("密码不能为空");
                    return;
                }

                if(!userPwd.equals(userRePwd)){
                    myToast("两次输入密码不一致");
                    return;
                }
                if(!flag){
                    return;
                }
                else{
                    myToast("点击了注册");
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
    private void myToast(final String s) {
        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(RegisterActivity.this, "" + s, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
    }

    private void getEditText(){
        userTell = mEdtTell.getText().toString();
        userPwd = mEdtSetPwd.getText().toString();
        userRePwd = mEdtConfirmPwd.getText().toString();
        verifyCode = mEdtVerify.getText().toString();
    }

    /**
     * 发送验证码
     */
    public void send_code(View view){
        if(!judPhone()){
            return;
        }
        if(judgeHaveUser(userTell)){
            myToast("该账号已注册");
            return;
        }
        String phone = mEdtTell.getText().toString();
        sendCode("86",phone);
    }

    /**
     * 验证验证码
     */
    public void check_code(View view) {
        String phone = mEdtTell.getText().toString();
        if(TextUtils.isEmpty(verifyCode)){
            myToast("验证码不能为空");
            return;
        }
        String verify = mEdtVerify.getText().toString();
        submitCode("86", phone, verify);
    }


    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    Log.i("yzyz","send ok");
                    myToast("验证码已发送");
                } else{
                    // TODO 处理错误的结果
                    Log.i("yzyz","send error");
                    myToast("验证码发送失败，请稍后再试");
                }
                //用完回调要注销掉，否则可能会出现内存泄露
                SMSSDK.unregisterAllEventHandler();
            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    Log.i("yzyz","yes");
                    flag = true;
                    myToast("验证码验证成功");
                } else{
                    // TODO 处理错误的结果
                    Log.i("yzyz","no");
                    flag = false;
                    myToast("验证码验证失败");
                }
                //用完回调要注销掉，否则可能会出现内存泄露
                SMSSDK.unregisterAllEventHandler();
            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }

    private boolean judPhone()
    {
        if(TextUtils.isEmpty(mEdtTell.getText().toString().trim()))
        {
            myToast("请输入您的电话号码");
            mEdtTell.requestFocus();
            return false;
        }
        else if(mEdtTell.getText().toString().trim().length()!=11)
        {
            myToast("您的电话号码位数不正确");
            mEdtTell.requestFocus();
            return false;
        }
        else
        {
            userTell=mEdtTell.getText().toString().trim();
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