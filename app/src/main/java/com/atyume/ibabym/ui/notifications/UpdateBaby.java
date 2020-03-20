package com.atyume.ibabym.ui.notifications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.InoculationDao;
import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.ui.home.EditBaby;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateBaby  extends AppCompatActivity {

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


        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);

        Inoculation inoculation = selectBaby(userId);
        initView(inoculation);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateBaby.this.finish();
            }
        });

        mbtnAddBaby.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getEditText();
                if (TextUtils.isEmpty(babyName)){
                    Toast.makeText(UpdateBaby.this,"请输入宝宝姓名",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    updateBaby(inoculation,babyName,babyHome,babyNow);

                    Intent intent = new Intent(UpdateBaby.this, MainActivity.class);
                    Toast.makeText(UpdateBaby.this,"修改了"+babyName+"宝宝",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
            }

        });
    }
    private void initView(Inoculation inoculation){
        mEditBabyName.setText(inoculation.getInoculBaby());
        mEditHomeAd.setText(inoculation.getBabyHome());
        mEditNowAd.setText(inoculation.getBabyAdress());
    }
    private void getEditText(){
        babyName = mEditBabyName.getText().toString();
        babyHome = mEditHomeAd.getText().toString();
        babyNow = mEditNowAd.getText().toString();
    }
    private void updateBaby(Inoculation inoculation,String babyName,String babyHome,String babyNow){
        inoculation.setInoculBaby(babyName);
        inoculation.setBabyHome(babyHome);
        inoculation.setBabyAdress(babyNow);
        babydao.update(inoculation);
    }

    private void updateBaby(Long babyId,String babyName,String babyHome,String babyNow){
        Inoculation inoculation = babydao.queryBuilder().where(InoculationDao.Properties.Id.eq(babyId)).unique();
        inoculation.setInoculBaby(babyName);
        inoculation.setBabyHome(babyHome);
        inoculation.setBabyAdress(babyNow);
        babydao.update(inoculation);
    }
    private Inoculation selectBaby(Long parentId){
        Inoculation inoculation = babydao.queryBuilder().where(InoculationDao.Properties.ParentId.eq(parentId)).unique();
        return inoculation;
    }
}
