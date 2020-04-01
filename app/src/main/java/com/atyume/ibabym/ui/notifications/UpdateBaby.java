package com.atyume.ibabym.ui.notifications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    @BindView(R.id.img_babyBirth)
    ImageView mImgBirth;
    @BindView(R.id.edit_babySexMale)
    RadioButton mMale;
    @BindView(R.id.edit_babySexFemale)
    RadioButton mFemale;
    @BindView(R.id.edit_homead)
    EditText mEditHomeAd;
    @BindView(R.id.edit_nowad)
    EditText mEditNowAd;

    String babyName,babyDate,babySex,babyAdress,babyHome;
    Long babyId = 0L;
    private InoculationDao babydao = MyApplication.getInstances().getDaoSession().getInoculationDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbaby);
        ButterKnife.bind(this);

        Inoculation inoculation = getBaby();
        if(inoculation!=null){
            initView(inoculation);
            babyId = inoculation.getId();
        }

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
                    updateBaby(babyId,babyName,babyDate,babySex,babyAdress,babyHome);

                    Intent intent = new Intent(UpdateBaby.this, MainActivity.class);
                    Toast.makeText(UpdateBaby.this,"修改了"+babyName+"宝宝",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
            }

        });
    }
    private Inoculation getBaby(){
        Intent intentGetId = getIntent();
        Long babyId = intentGetId.getLongExtra("BabyId",0L);
        if(babyId == 0){
            SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
            Long userId = sharedPreferences.getLong("loginUserId",0L);

            Inoculation inoculation = new Inoculation();
            inoculation = selectBabyByParent(userId);
            return inoculation;
        }
        return selectBabyBySelf(babyId);

    }
    private void initView(Inoculation inoculation){
        mEditBabyName.setText(inoculation.getInoculBaby());
        mEditBabyBirth.setText(inoculation.getBabyData());
        mEditHomeAd.setText(inoculation.getBabyAdress());
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
    private void getEditText(){
        babyName = mEditBabyName.getText().toString();
        babyAdress = mEditHomeAd.getText().toString();
        babyHome = mEditNowAd.getText().toString();
        babyDate = mEditBabyBirth.getText().toString();
        if(mMale.isChecked()){
            babySex = "男";
        }
        else if(mFemale.isChecked()){
            babySex = "女";
        }
    }
    private void updateBaby(Inoculation inoculation,String babyName,String babyDate,String babySex,String babyAdress,String babyHome){
        inoculation.setInoculBaby(babyName);
        inoculation.setBabyData(babyDate);
        inoculation.setBabySex(babySex);
        inoculation.setBabyHome(babyAdress);
        inoculation.setBabyAdress(babyHome);
        babydao.update(inoculation);
    }

    private void updateBaby(Long babyId,String babyName,String babyDate,String babySex,String babyAdress,String babyHome){
        Inoculation inoculation = babydao.queryBuilder().where(InoculationDao.Properties.Id.eq(babyId)).unique();
        inoculation.setInoculBaby(babyName);
        inoculation.setBabyData(babyDate);
        inoculation.setBabySex(babySex);
        inoculation.setBabyHome(babyAdress);
        inoculation.setBabyAdress(babyHome);
        babydao.update(inoculation);
    }
    private Inoculation selectBabyBySelf(Long babyId){
        Inoculation inoculation = new Inoculation();
        inoculation = babydao.load(babyId);
        return inoculation;
    }
    private Inoculation selectBabyByParent(Long parentId){
        Inoculation inoculation = new Inoculation();
        inoculation = babydao.queryBuilder().where(InoculationDao.Properties.ParentId.eq(parentId)).unique();
        return inoculation;
    }
}

