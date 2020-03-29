package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.VaccinDao;
import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.Vaccin;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditMiao extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;
    @BindView(R.id.edit_miao_topbar)
    TextView mTopBar;

    @BindView(R.id.edit_miaoName)
    EditText mEditMiaoName;
    @BindView(R.id.edit_miaoDetial)    //预防疾病
    EditText mEditMiaoDetail;
    @BindView(R.id.edit_miaoNo)
    EditText mEditMiaoNo;
    @BindView(R.id.edit_miaoFactory)
    EditText mEditMiaoFactory;
    @BindView(R.id.edit_properAge)
    EditText mEditProperAge;
    @BindView(R.id.edit_certiProces)
    EditText mEditCertiProces;
    @BindView(R.id.edit_miaoPrice)
    EditText mEditMiaoPrice;
    @BindView(R.id.edit_miaoAmount)
    EditText mEditMiaoAmount;
    @BindView(R.id.edit_miaoAttention)
    EditText mEditMiaoAttention;
    @BindView(R.id.edit_miaoEffect)
    EditText mEditMiaoEffect;
    @BindView(R.id.button_add_miao)
    QMUIRoundButton mbtnAddMiao;

    String MiaoName,MiaoDetail,MiaoNo,MiaoFactory,MiaoProperAge,MiaoCertiProcess,MiaoAttention,MiaoEffect;
    Double MiaoPrice;
    Long MiaoAmount;
    private VaccinDao vaccinDao = MyApplication.getInstances().getDaoSession().getVaccinDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmiao);
        ButterKnife.bind(this);

        initTop();

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMiao.this, MiaoViewActivity.class);
                startActivity(intent);//返回页面1
                finish();
            }
        });

        mbtnAddMiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();
                if(TextUtils.isEmpty(MiaoName)){
                    Toast.makeText(EditMiao.this, "请输入疫苗名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(MiaoNo)){
                    Toast.makeText(EditMiao.this, "请输入疫苗生产批号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(MiaoProperAge)) {
                    Toast.makeText(EditMiao.this, "请输入疫苗适用年龄", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(MiaoCertiProcess)) {
                    Toast.makeText(EditMiao.this, "请输入疫苗接种程序", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    insertData();
                    Intent intent = new Intent(EditMiao.this, MiaoViewActivity.class);
                    startActivity(intent);//返回页面1
                    finish();
                }

            }
        });

    }
    private void getEditText(){
        MiaoName = mEditMiaoName.getText().toString();
        MiaoDetail = mEditMiaoDetail.getText().toString();
        MiaoNo = mEditMiaoNo.getText().toString();
        MiaoFactory = mEditMiaoFactory.getText().toString();
        MiaoProperAge = mEditProperAge.getText().toString();
        MiaoCertiProcess = mEditCertiProces.getText().toString();
        MiaoPrice = Double.parseDouble(mEditMiaoPrice.getText().toString());
        MiaoAmount = Long.parseLong(mEditMiaoAmount.getText().toString());
        MiaoAttention = mEditMiaoAttention.getText().toString();
        MiaoEffect = mEditMiaoEffect.getText().toString();
    }
    private void insertData() {
        Vaccin vaccin = new Vaccin(MiaoName,MiaoDetail,MiaoAttention,MiaoEffect,MiaoProperAge,MiaoCertiProcess,MiaoPrice,MiaoFactory,MiaoNo,MiaoAmount);
        long insert = vaccinDao.insert(vaccin);
        if (insert > 0) {
            Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
        }
    }
    private void initTop(){
        mTopBar.setText("新增疫苗");
    }

}
