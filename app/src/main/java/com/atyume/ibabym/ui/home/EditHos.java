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
import com.atyume.ibabym.ui.dashboard.ViewMiaoDetail;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditHos extends AppCompatActivity {

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

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditHos.this.finish();
            }
        });
        mbtnAddHosMiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();

                if(TextUtils.isEmpty(hosName)){
                    Toast.makeText(EditHos.this, "请输入医院名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(hosAdress)) {
                    Toast.makeText(EditHos.this, "请输入医院地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!vaccinModel.judgeMiaoExist(hosMiaoId)){
                    Toast.makeText(EditHos.this, "输入的疫苗编号不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!hosInfoModel.judgeHosIsExist(hosName,hosAdress,hosMiaoId)){

                    hosInfoModel.insertHosInfo(hosName,hosAdress,hosMiaoId,hosMiaoAmount);

                    Intent intent = new Intent(EditHos.this, HosViewActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    hosInfoModel.updateHosInfo(hosName,hosAdress,hosMiaoId,hosMiaoAmount);
                    Intent intent = new Intent(EditHos.this, HosViewActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void getEditText(){
        hosName = mEditHosName.getText().toString();
        hosAdress = mEditHosAdress.getText().toString();
        hosMiaoId = Long.parseLong(mEditMiaoIdHos.getText().toString());
        hosMiaoAmount = Long.parseLong((mEditMiaoAmountHos.getText().toString()));
    }

}