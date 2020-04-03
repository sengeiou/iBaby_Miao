package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.InoculationDao;

import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.Model.InoculationModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;

import com.atyume.ibabym.ui.notifications.EditUser;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditBaby extends AppCompatActivity{

    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.button_add_baby)
    QMUIRoundButton mbtnAddBaby;

    @BindView(R.id.edit_babyName)
    EditText mEditBabyName;
    @BindView(R.id.edit_babyBirth)
    EditText mEditBabyBirth;
    @BindView(R.id.img_babyBirth)
    ImageView img_datePicker;
    @BindView(R.id.edit_babySexMale)
    RadioButton mMale;
    @BindView(R.id.edit_babySexFemale)
    RadioButton mFemale;
    @BindView(R.id.edit_homead)
    EditText mEditAdress;
    @BindView(R.id.edit_nowad)
    EditText mEditNowAd;

    String babyName,babySex,babyDate,babyAdreess,babyHome;
    InoculationModel inoculationModel = new InoculationModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbaby);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);

        initView(userId);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBaby.this.finish();
            }
        });
        img_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        }
        });
        mbtnAddBaby.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view){
                getEditText();
                if (TextUtils.isEmpty(babyName)){
                    Toast.makeText(EditBaby.this,"请输入宝宝姓名",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    inoculationModel.insertBaby(babyName,babyDate,babySex,babyAdreess,babyHome,userId);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Long babyId = inoculationModel.getBabyIdByName(babyName);
                    editor.putLong("babyId",babyId);
                    editor.apply();

                    Intent intent = new Intent(EditBaby.this, MainActivity.class);
                    Toast.makeText(EditBaby.this,"添加了"+babyName+"宝宝",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void initView(Long userId){
        Inoculation inoculation = new Inoculation();
        if(inoculationModel.judgeBabyIsExit(userId)){
            inoculation = inoculationModel.selectBabyByParent(userId);
            mEditBabyName.setText(inoculation.getInoculBaby());
            mEditBabyBirth.setText(inoculation.getBabyData());
            mEditAdress.setText(inoculation.getBabyAdress());
            mEditNowAd.setText(inoculation.getBabyHome());
            if((inoculation.getBabySex()).equals("男")){
                mMale.setChecked(true);
                mFemale.setChecked(false);
            }
            if((inoculation.getBabySex()).equals("女")){
                mMale.setChecked(false);
                mFemale.setChecked(true);
            }
        }

    }

    private void getEditText(){
        babyName = mEditBabyName.getText().toString();
        babyAdreess = mEditAdress.getText().toString();
        babyHome = mEditNowAd.getText().toString();
        babyDate = mEditBabyBirth.getText().toString();
        if(mMale.isChecked()){
            babySex = "男";
        }
        else if(mFemale.isChecked()){
            babySex = "女";
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}
