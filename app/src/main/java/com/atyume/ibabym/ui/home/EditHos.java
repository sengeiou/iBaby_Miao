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

    private HosInfoDao hosInfoDao = MyApplication.getInstances().getDaoSession().getHosInfoDao();
    private VaccinDao vaccinDao = MyApplication.getInstances().getDaoSession().getVaccinDao();

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
                if(!judgeMiaoExist(hosMiaoId)){
                    Toast.makeText(EditHos.this, "输入的疫苗编号不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!judgeIsExist(hosName,hosAdress,hosMiaoId)){
                    insertData();
                    Intent intent = new Intent(EditHos.this, HosViewActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    updateData(hosName,hosAdress,hosMiaoId,hosMiaoAmount);
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

    private void insertData() {
        HosInfo hosInfo = new HosInfo(hosName,hosAdress,hosMiaoId,hosMiaoAmount);
        long insert = hosInfoDao.insert(hosInfo);
        if (insert > 0) {
            Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateData(String hosName,String hosAdress,Long hosMiaoId,Long hosMiaoAmount){
       HosInfo hosInfo = hosInfoDao.queryBuilder().where(HosInfoDao.Properties.HosName.eq(hosName),
                HosInfoDao.Properties.HosAdress.eq(hosAdress),HosInfoDao.Properties.VaccinId.eq(hosMiaoId)).unique();
       Long newAmount = hosInfo.getVaccinAmount()+hosMiaoAmount;
       hosInfo.setVaccinAmount(newAmount);
       hosInfoDao.update(hosInfo);
    }
    private boolean judgeIsExist(String hosName,String hosAdress,Long hosMiaoId){
        List<HosInfo> hosInfoList = hosInfoDao.queryBuilder().where(HosInfoDao.Properties.HosName.eq(hosName),
                HosInfoDao.Properties.HosAdress.eq(hosAdress),HosInfoDao.Properties.VaccinId.eq(hosMiaoId)).list();
        if (hosInfoList==null || hosInfoList.size()==0){
            return false;    //不存在
        }
        return true;
    }
    private boolean judgeMiaoExist(Long MiaoId){
        List<Vaccin> vaccinList = vaccinDao.queryBuilder().where(VaccinDao.Properties.Id.eq(MiaoId)).list();
        if(vaccinList==null || vaccinList.size()==0){
            return false;
        }
        return true;
    }

}