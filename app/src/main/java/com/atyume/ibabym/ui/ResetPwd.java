package com.atyume.ibabym.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class ResetPwd extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.edit_ResetTell)
    EditText mEdtResetTell;
    @BindView(R.id.edit_ResetVerifyCode)
    EditText mEditResetVerify;
    @BindView(R.id.button_get_reset_verifycode)
    QMUIRoundButton mbtnGetResetVerify;
    @BindView(R.id.edit_SetNewPwd)
    EditText mEditSetNewPwd;
    @BindView(R.id.edit_ConfirmNewPwd)
    EditText mEditConfirmNewPwd;
    @BindView(R.id.btn_resetPwd)
    Button mbtnResetPwd;

    String userTell,userNewPwd,userReNewPwd;
    String verifyCode;

    private ParentInfoDao parentDao = MyApplication.getInstances().getDaoSession().getParentInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwderror);
        ButterKnife.bind(this);
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
                if(TextUtils.isEmpty(userTell)){
                    Toast.makeText(ResetPwd.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userNewPwd)||TextUtils.isEmpty(userReNewPwd)){
                    Toast.makeText(ResetPwd.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(verifyCode)){
                    Toast.makeText(ResetPwd.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!userNewPwd.equals(userReNewPwd)){
                    Toast.makeText(ResetPwd.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!judgeHaveUser(userTell)){
                    Toast.makeText(ResetPwd.this, "该账号尚未注册", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    ParentInfo parentInfo = judgeUpdatePwd(userTell,userNewPwd);
                    Toast.makeText(ResetPwd.this, parentInfo.getId()+"Tell:"+parentInfo.getParentTell()+"Pwd:"+parentInfo.getParentPwd(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPwd.this, MainActivity.class);
                    startActivity(intent);//返回页面1
                    finish();
                }
            }
        });
    }
    private void getEditString(){
        userTell = mEdtResetTell.getText().toString();
        userNewPwd = mEditSetNewPwd.getText().toString();
        userReNewPwd = mEditConfirmNewPwd.getText().toString();
        verifyCode = mEditResetVerify.getText().toString();
    }
    protected boolean judgeHaveUser(String userTell){
        List<ParentInfo> parentList = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).list();
        if(parentList.size()==0){
            return false;                 //没有注册过
        }
        return true;                      //注册过
    }
    protected ParentInfo judgeUpdatePwd(String userTell, String userNewPwd){
        ParentInfo parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).unique();

        String md5Pwd = MD5Utils.md5(userNewPwd);
        parentInfo.setParentPwd(md5Pwd);

        parentDao.update(parentInfo);
        return parentInfo;
    }
}
