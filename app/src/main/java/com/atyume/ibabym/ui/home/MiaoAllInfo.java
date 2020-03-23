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
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.Vaccin;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MiaoAllInfo extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.edit_miao_topbar)
    TextView mTopBar;

    @BindView(R.id.edit_miaoName)
    EditText mEditMiaoName;
    @BindView(R.id.edit_miaoDetial)
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
        setEditText();

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiaoAllInfo.this.finish();
            }
        });

        mbtnAddMiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();
                updateData();
                Intent intent = new Intent(MiaoAllInfo.this, MiaoViewActivity.class);
                startActivity(intent);//返回页面1
                finish();
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
    private void setEditText(){
        Vaccin vaccin = getThis();
        mEditMiaoName.setText(vaccin.getVaccinName());
        mEditMiaoDetail.setText(vaccin.getVaccinEffect());
        mEditMiaoNo.setText(vaccin.getVaccinNo());
        mEditMiaoFactory.setText(vaccin.getProduceCompany());
        mEditProperAge.setText(vaccin.getVaccinAge());
        mEditCertiProces.setText(vaccin.getVaccinProcess());
        mEditMiaoPrice.setText(vaccin.getVaccinPrice().toString());
        mEditMiaoAmount.setText(vaccin.getVaccinAmount().toString());
        mEditMiaoAttention.setText(vaccin.getVaccinAttention());
        mEditMiaoEffect.setText(vaccin.getVaccinDisadv());
    }
    private void updateData() {
        Vaccin vaccin = getThis();
        vaccin.setVaccinName(MiaoName);
        vaccin.setVaccinEffect(MiaoDetail);
        vaccin.setVaccinNo(MiaoNo);
        vaccin.setProduceCompany(MiaoFactory);
        vaccin.setVaccinAge(MiaoProperAge);
        vaccin.setVaccinProcess(MiaoCertiProcess);
        vaccin.setVaccinPrice(MiaoPrice);
        vaccin.setVaccinAmount(MiaoAmount);
        vaccin.setVaccinDisadv(MiaoEffect);

        vaccinDao.update(vaccin);
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }
    private Vaccin getThis(){
        Intent intentGetId = getIntent();
        Long miaoId = intentGetId.getLongExtra("manageMiaoId",0L);
        Vaccin vaccin = vaccinDao.queryBuilder().where(VaccinDao.Properties.Id.eq(miaoId)).unique();
        return vaccin;
    }

    private void initTop(){
        mTopBar.setText("修改疫苗信息");
    }
}

