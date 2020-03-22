package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.VaccinDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.Vaccin;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMiaoDetail extends AppCompatActivity {

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
    @BindView(R.id.button_take_order_miao)
    QMUIRoundButton mbtnOrderMiao;

    private VaccinDao vaccinDao = MyApplication.getInstances().getDaoSession().getVaccinDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_miao);

        ButterKnife.bind(this);

        Vaccin vaccin = getThis();
        setMiaoText(vaccin);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewMiaoDetail.this.finish();
            }
        });
        mbtnOrderMiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewMiaoDetail.this, OrderMiao.class);
                intent.putExtra("MiaoId",vaccin.getId());
                startActivity(intent);
            }
        });
    }
    /*private void initData(){
        Vaccin vaccin = getThis();
        setMiaoText(vaccin);
    }*/

    private Vaccin getThis(){
        Intent intentGetId = getIntent();
        Long miaoId = intentGetId.getLongExtra("clickMiaoId",0L);
        Vaccin vaccin = vaccinDao.queryBuilder().where(VaccinDao.Properties.Id.eq(miaoId)).unique();
        return vaccin;
    }
    private void setMiaoText(Vaccin vaccin){
        mShowMiaoName.setText(vaccin.getVaccinName());
        mShowMiaoAge.setText(vaccin.getVaccinAge());
        mShowMiaoAttention.setText(vaccin.getVaccinAttention());
        mShowMiaoEffect.setText(vaccin.getVaccinEffect());
        mShowMiaoDisadv.setText(vaccin.getVaccinDisadv());
        mShowMiaoPrice.setText(vaccin.getVaccinPrice().toString());
        mShowMiaoProcess.setText(vaccin.getVaccinProcess());
    }

}
