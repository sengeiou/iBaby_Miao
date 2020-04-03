package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.HosInfoDao;
import com.atyume.greendao.gen.VaccinDao;
import com.atyume.ibabym.Model.HosInfoModel;
import com.atyume.ibabym.Model.VaccinModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.HosInfo;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.Vaccin;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HosInfoActivity extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.edit_hosName)
    EditText mEditHosName;
    @BindView(R.id.edit_hosAddress)
    EditText mEditHosAdress;
    @BindView(R.id.edit_miaoId_Hos)
    EditText mEditMiaoIdHos;
    @BindView(R.id.edit_miaoAmount_Hos)
    EditText mEditMiaoAmountHos;
    @BindView(R.id.button_add_hos_miao)
    QMUIRoundButton mbtnAddHosMiao;

    String hosName,hosAdress;
    Long hosMiaoId,hosMiaoAmount;

    HosInfoModel hosInfoModel = new HosInfoModel();
    VaccinModel vaccinModel = new VaccinModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edithos);
        ButterKnife.bind(this);

        setEditText();

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HosInfoActivity.this.finish();
            }
        });
        mbtnAddHosMiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();
                if(TextUtils.isEmpty(hosName)){
                    Toast.makeText(HosInfoActivity.this, "请输入医院名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(hosAdress)) {
                    Toast.makeText(HosInfoActivity.this, "请输入医院地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!vaccinModel.judgeMiaoExist(hosMiaoId)){
                    Toast.makeText(HosInfoActivity.this, "输入的疫苗编号不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!hosInfoModel.judgeHosIsExist(hosName,hosAdress,hosMiaoId)){
                    hosInfoModel.updateHosById(getHosId(),hosName,hosAdress,hosMiaoId,hosMiaoAmount);
                    Intent intent = new Intent(HosInfoActivity.this, HosViewActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void setEditText(){
        HosInfo hosInfo = new HosInfo();
        hosInfo = getHos();
        mEditHosName.setText(hosInfo.getHosName());
        mEditHosAdress.setText(hosInfo.getHosAdress());
        mEditMiaoAmountHos.setText(hosInfo.getVaccinAmount().toString());
        mEditMiaoIdHos.setText(hosInfo.getVaccinId().toString());
    }

    private void getEditText(){
        hosName = mEditHosName.getText().toString();
        hosAdress = mEditHosAdress.getText().toString();
        hosMiaoId = Long.parseLong(mEditMiaoIdHos.getText().toString());
        hosMiaoAmount = Long.parseLong((mEditMiaoAmountHos.getText().toString()));
    }

    private Long getHosId(){
        Intent intent = getIntent();
        Long hosId = intent.getLongExtra("manageHosId",0L);
        return hosId;
    }

    public HosInfo getHos(){
        HosInfo hosInfo = new HosInfo();
        hosInfo = hosInfoModel.getHosInfo(getHosId());
        return hosInfo;
    }
}