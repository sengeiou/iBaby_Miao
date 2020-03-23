package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.VaccinDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.Vaccin;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderMiao extends AppCompatActivity{
    @BindView(R.id.comeBack)
    TextView mComeBack;
    @BindView(R.id.edit_OrderBabyName)
    EditText mOrderBabyName;
    @BindView(R.id.edit_OrderCertiArea)
    EditText mOrderCertiArea;
    @BindView(R.id.edit_OrderCertiTime)
    EditText mOrderCertiTime;
    @BindView(R.id.button_sure_order_miao)
    QMUIRoundButton mbtnSureOrder;

    private VaccinDao vaccinDao = MyApplication.getInstances().getDaoSession().getVaccinDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order_miao);
        ButterKnife.bind(this);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderMiao.this.finish();
            }
        });
        mbtnSureOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private Vaccin getThis(){
        Intent intentGetId = getIntent();
        Long miaoId = intentGetId.getLongExtra("MiaoId",0L);
        Vaccin vaccin = vaccinDao.queryBuilder().where(VaccinDao.Properties.Id.eq(miaoId)).unique();
        return vaccin;
    }

}
