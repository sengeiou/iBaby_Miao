package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.HosInfoDao;
import com.atyume.greendao.gen.InoculationDao;
import com.atyume.greendao.gen.OrderVaccinDao;
import com.atyume.greendao.gen.VaccinDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.HosInfo;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderVaccin;
import com.atyume.ibabym.basics.Vaccin;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowOrderMiaoInfo extends AppCompatActivity {

    @BindView(R.id.comeBack)
    TextView mComeBack;
    @BindView(R.id.show_detail_miao_name)
    TextView mShowMiaoName;
    @BindView(R.id.show_detail_miao_Effect)
    TextView mShowMiaoEffect;
    @BindView(R.id.show_detail_miao_Age)
    TextView mShowMiaoAge;
    @BindView(R.id.show_detail_miao_Process)
    TextView mShowMiaoProcess;
    @BindView(R.id.show_detail_miao_Price)
    TextView mShowMiaoPrice;
    @BindView(R.id.show_detail_miao_Attention)
    TextView mShowMiaoAttention;
    @BindView(R.id.show_detail_miao_Disadv)
    TextView mShowMiaoDisadv;
    @BindView(R.id.show_OrderBabyName)
    TextView mShowBabyName;
    @BindView(R.id.show_OrderCertiArea)
    TextView mShowCertiArea;
    @BindView(R.id.show_OrderCertiTime)
    TextView mShowCertiTime;

    private VaccinDao vaccinDao = MyApplication.getInstances().getDaoSession().getVaccinDao();
    private OrderVaccinDao orderVaccinDao = MyApplication.getInstances().getDaoSession().getOrderVaccinDao();
    private InoculationDao inoculationDao = MyApplication.getInstances().getDaoSession().getInoculationDao();
    private HosInfoDao hosInfoDao = MyApplication.getInstances().getDaoSession().getHosInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recoder_ordermiao);

        ButterKnife.bind(this);

        setMiaoText();
        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowOrderMiaoInfo.this.finish();
            }
        });

    }

    private OrderVaccin getThis(){
        OrderVaccin orderVaccin = new OrderVaccin();
        orderVaccin = orderVaccinDao.load(getOrderId());
        return orderVaccin;
    }
    private Long getOrderId(){
        Intent intentGetId = getIntent();
        Long orderMiaoId = intentGetId.getLongExtra("clickOrderMiaoId",0L);
        return orderMiaoId;
    }
    private void setMiaoText(){
        OrderVaccin orderVaccin = new OrderVaccin();
        orderVaccin = getThis();
        Vaccin vaccin = new Vaccin();
        vaccin = getVaccin();
        mShowMiaoName.setText(vaccin.getVaccinName());
        mShowMiaoAge.setText(vaccin.getVaccinAge());
        mShowMiaoAttention.setText(vaccin.getVaccinAttention());
        mShowMiaoEffect.setText(vaccin.getVaccinEffect());
        mShowMiaoDisadv.setText(vaccin.getVaccinDisadv());
        mShowMiaoPrice.setText(vaccin.getVaccinPrice().toString());
        mShowMiaoProcess.setText(vaccin.getVaccinProcess());
        mShowBabyName.setText(getBabyName());
        mShowCertiArea.setText(getHos());
        mShowCertiTime.setText(orderVaccin.getInocluTime());
    }
    private Vaccin getVaccin(){
        Vaccin vaccin = new Vaccin();
        vaccin = vaccinDao.load(getThis().getVaccinId());
        return vaccin;
    }
    private String getBabyName(){
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationDao.load(getThis().getInocluId());
        return inoculation.getInoculBaby();
    }
    private String getHos(){
        HosInfo hosInfo = new HosInfo();
        hosInfo = hosInfoDao.load(getThis().getHosId());
        return hosInfo.getHosName();
    }
}
