package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.InoculationDao;

import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;

import com.atyume.ibabym.ui.notifications.EditUser;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditBaby extends AppCompatActivity {

    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.button_add_baby)
    QMUIRoundButton mbtnAddBaby;

    @BindView(R.id.edit_babyName)
    EditText mEditBabyName;
    @BindView(R.id.edit_babyBirth)
    EditText mEditBabyBirth;
    @BindView(R.id.edit_babySex)
    RadioGroup mEditBabySex;
    @BindView(R.id.edit_homead)
    EditText mEditHomeAd;
    @BindView(R.id.edit_nowad)
    EditText mEditNowAd;

    String babyName,babyHome,babyNow;

    private InoculationDao babydao = MyApplication.getInstances().getDaoSession().getInoculationDao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbaby);
        ButterKnife.bind(this);
        /*mdaoUtils = new DaoUtils(this);*/

        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBaby.this.finish();
            }
        });

        mbtnAddBaby.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getEditText();
                if (TextUtils.isEmpty(babyName)){
                    Toast.makeText(EditBaby.this,"请输入宝宝姓名",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    insertBaby(babyName,babyHome,babyNow,userId);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("babyId",selectBabyId(babyName));
                    editor.apply();

                    Intent intent = new Intent(EditBaby.this, MainActivity.class);
                    Toast.makeText(EditBaby.this,"添加了"+babyName+"宝宝",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
            }

        });
    }
    private void getEditText(){
        babyName = mEditBabyName.getText().toString();
        babyHome = mEditHomeAd.getText().toString();
        babyNow = mEditNowAd.getText().toString();
    }
    private void insertBaby(String babyName,String babyHome,String babyNow,Long userId){
        babydao.insert(new Inoculation(babyName,babyHome,babyNow,userId));
    }
    private Long selectBabyId(String babyName){
        Inoculation inoculation = babydao.queryBuilder().where(InoculationDao.Properties.InoculBaby.eq(babyName)).unique();
        return inoculation.getId();
    }

}
