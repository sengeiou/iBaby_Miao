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
import com.atyume.ibabym.Model.HosInfoModel;
import com.atyume.ibabym.Model.InoculationModel;
import com.atyume.ibabym.Model.OrderVaccinModel;
import com.atyume.ibabym.Model.VaccinModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.HosInfo;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderVaccin;
import com.atyume.ibabym.basics.Vaccin;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
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
    @BindView(R.id.count_wait)
    TextView mCountWait;
    @BindView(R.id.sure_Succeed)
    QMUIRoundButton mSureSucceed;
    @BindView(R.id.sure_Cancel)
    QMUIRoundButton mSureCancel;
    @BindView(R.id.relative_show_other)
    QMUIRelativeLayout mRelative;

    VaccinModel vaccinModel = new VaccinModel();
    OrderVaccinModel orderVaccinModel = new OrderVaccinModel();
    InoculationModel inoculationModel = new InoculationModel();
    HosInfoModel hosInfoModel = new HosInfoModel();

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

        mSureSucceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderVaccinModel.updateSucceed(getOrderVaccin());
                mSureSucceed.setText("已完成");
                mSureSucceed.setVisibility(View.INVISIBLE);
                mSureCancel.setVisibility(View.INVISIBLE);
                mRelative.setVisibility(View.INVISIBLE);
            }
        });

        mSureCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderVaccinModel.updateCancel(getOrderVaccin());
                hosInfoModel.updateVaccinAmount(getOrderVaccin().getHosId());
                mSureSucceed.setVisibility(View.INVISIBLE);
                mSureCancel.setVisibility(View.INVISIBLE);
                mRelative.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void setMiaoText(){
        OrderVaccin orderVaccin = new OrderVaccin();
        orderVaccin = getOrderVaccin();

        Vaccin vaccin = new Vaccin();
        vaccin = getVaccin(orderVaccin.getVaccinId());

        mShowMiaoName.setText(vaccin.getVaccinName());
        mShowMiaoAge.setText(vaccin.getVaccinAge());
        mShowMiaoAttention.setText(vaccin.getVaccinAttention());
        mShowMiaoEffect.setText(vaccin.getVaccinEffect());
        mShowMiaoDisadv.setText(vaccin.getVaccinDisadv());
        mShowMiaoPrice.setText(vaccin.getVaccinPrice().toString());
        mShowMiaoProcess.setText(vaccin.getVaccinProcess());
        mShowBabyName.setText(getBabyName(orderVaccin.getInocluId()));
        mShowCertiArea.setText(getHos(orderVaccin.getHosId()));
        mShowCertiTime.setText(orderVaccin.getInocluTime());
//        显示排队人数
        int waitCount = getCountWait(orderVaccin.getInocluTime())-1;
        if(waitCount < 0){
            mRelative.setVisibility(View.INVISIBLE);
        }
        else{
            String wait = String.valueOf(waitCount);
            mCountWait.setText(wait);
        }
//         按钮的显示
        if(orderVaccin.getIsSucceed()==1){
            mSureSucceed.setVisibility(View.INVISIBLE);
            mSureCancel.setVisibility(View.INVISIBLE);
            mRelative.setVisibility(View.INVISIBLE);
        }
        else if(orderVaccin.getIsSucceed()==2){
            mSureSucceed.setVisibility(View.INVISIBLE);
            mSureCancel.setVisibility(View.INVISIBLE);
            mRelative.setVisibility(View.INVISIBLE);
        }

    }

    private OrderVaccin getOrderVaccin(){
        OrderVaccin orderVaccin = new OrderVaccin();
        Long orderVaccinId = getOrderId();
        orderVaccin = orderVaccinModel.getOrderVaccin(getOrderId());
        return orderVaccin;
    }

    private Long getOrderId(){
        Intent intentGetId = getIntent();
        Long orderMiaoId = intentGetId.getLongExtra("clickOrderMiaoId",0L);
        return orderMiaoId;
    }

    private Vaccin getVaccin(Long vaccinId){
        return vaccinModel.getVaccin(vaccinId);
    }

    private String getBabyName(Long babyId){
        return inoculationModel.getBabyNameByBaby(babyId);
    }

    private String getHos(Long hosId){
        return hosInfoModel.getHosName(hosId);
    }

    private int getCountWait(String orderTime){
        return orderVaccinModel.getOrderCountByDate(orderTime);
    }
}
