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

public class ResetPwd extends AppCompatActivity {

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

        mbtnResetPwd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                userTell = mEdtResetTell.getText().toString();
                userNewPwd = mEditSetNewPwd.getText().toString();
                userReNewPwd = mEditConfirmNewPwd.getText().toString();
                verifyCode = mEditResetVerify.getText().toString();

                if(userTell==null||userTell.equals("")){
                    Toast.makeText(ResetPwd.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                if(userNewPwd==null||userNewPwd.equals("")||userReNewPwd==null||userReNewPwd.equals("")){
                    Toast.makeText(ResetPwd.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                if(verifyCode==null||verifyCode.equals("")){
                    Toast.makeText(ResetPwd.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                if(!userNewPwd.equals(userReNewPwd)){
                    Toast.makeText(ResetPwd.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                }
                if(!judgeHaveUser(userTell)){
                    Toast.makeText(ResetPwd.this, "该账号尚未注册", Toast.LENGTH_SHORT).show();
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
    protected boolean judgeHaveUser(String userTell){
        List<ParentInfo> parentList = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).list();
        if(parentList.size()==0){
            return false;                 //没有注册过
        }
        return true;                      //注册过
    }
    protected ParentInfo judgeUpdatePwd(String userTell, String userNewPwd){
        ParentInfo parentInfo = parentDao.queryBuilder().where(ParentInfoDao.Properties.ParentTell.eq(userTell)).unique();

        parentInfo.setParentPwd(userNewPwd);

        ParentInfo new_parentInfo = new ParentInfo();
        new_parentInfo.setId(parentInfo.getId());
        new_parentInfo.setParentName(parentInfo.getParentName());
        new_parentInfo.setParentNick(parentInfo.getParentNick());
        new_parentInfo.setParentPwd(userNewPwd);
        new_parentInfo.setParentTell(parentInfo.getParentTell());
        new_parentInfo.setParentWorkAdress(parentInfo.getParentWorkAdress());

        parentDao.update(parentInfo);
        return parentInfo;
    }
}
