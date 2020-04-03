package com.atyume.ibabym.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ParentInfoDao;
import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.Model.ParentModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ParentInfo;
import com.atyume.ibabym.utils.MD5Utils;
import com.mob.MobSDK;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ResetPwd extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.edit_ResetTell)
    EditText mEdtTell;
    @BindView(R.id.edit_ResetVerifyCode)
    EditText mEdtVerify;
    @BindView(R.id.button_get_reset_verifycode)
    Button mbtnGetResetVerify;
    @BindView(R.id.edit_SetNewPwd)
    EditText mEditSetNewPwd;
    @BindView(R.id.edit_ConfirmNewPwd)
    EditText mEditConfirmNewPwd;
    @BindView(R.id.btn_resetPwd)
    Button mbtnResetPwd;

    private boolean flag = true;
    String userTell,userNewPwd,userReNewPwd;
    String verifyCode;

    ParentModel parentModel = new ParentModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwderror);
        ButterKnife.bind(this);
        MobSDK.init(this);
        mbtnGetResetVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_code(v);
            }
        });
        //返回键
        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPwd.this.finish();
            }
        });
        //确定重置按钮
        mbtnResetPwd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getEditString();
                check_code(view);
                if(TextUtils.isEmpty(userNewPwd)||TextUtils.isEmpty(userReNewPwd)){
                    myToast("密码不能为空");
                    return;
                }

                if(!userNewPwd.equals(userReNewPwd)){
                    myToast("两次输入密码不一致");
                    return;
                }
                if(!parentModel.judgeHaveUser(userTell)){
                    myToast("该账号尚未注册");
                    return;
                }
                if(!flag){
                    myToast("验证码不正确");
                    return;
                }
                else{
                    ParentInfo parentInfo = parentModel.judgeUpdatePwd(userTell,userNewPwd);
                    Toast.makeText(ResetPwd.this, parentInfo.getId()+"Tell:"+parentInfo.getParentTell()+"Pwd:"+parentInfo.getParentPwd(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPwd.this, MainActivity.class);
                    startActivity(intent);//返回页面1
                    finish();
                }
            }
        });
    }
    private void myToast(final String s) {
        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(ResetPwd.this, "" + s, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
    }

    private void getEditString(){
        userTell = mEdtTell.getText().toString();
        userNewPwd = mEditSetNewPwd.getText().toString();
        userReNewPwd = mEditConfirmNewPwd.getText().toString();
        verifyCode = mEdtVerify.getText().toString();
    }

    /**
     * 发送验证码
     */
    public void send_code(View view){
        if(!judPhone()){
            return;
        }
        if(!parentModel.judgeHaveUser(userTell)){
            myToast("该账号尚未注册");
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

}
