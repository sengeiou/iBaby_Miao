package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.HosInfoDao;
import com.atyume.greendao.gen.InoculationDao;
import com.atyume.greendao.gen.OrderVaccinDao;
import com.atyume.greendao.gen.VaccinDao;
import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.HosInfo;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderVaccin;
import com.atyume.ibabym.basics.Vaccin;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    String babyName,certiArea,certiTime;
    private VaccinDao vaccinDao = MyApplication.getInstances().getDaoSession().getVaccinDao();
    private InoculationDao inoculationDao = MyApplication.getInstances().getDaoSession().getInoculationDao();
    private OrderVaccinDao orderVaccinDao = MyApplication.getInstances().getDaoSession().getOrderVaccinDao();
    private HosInfoDao hosInfoDao = MyApplication.getInstances().getDaoSession().getHosInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order_miao);
        ButterKnife.bind(this);
        setEditText();

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderMiao.this.finish();
            }
        });
        mbtnSureOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();
                insertMiaoOrder(certiTime,babyName,certiArea);
                Toast.makeText(OrderMiao.this, "预约成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderMiao.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void getEditText(){
        babyName = mOrderBabyName.getText().toString();
        certiArea = mOrderCertiArea.getText().toString();
        certiTime = mOrderCertiTime.getText().toString();
    }
    private void setEditText(){
        mOrderBabyName.setText(getBaby());
    }
    private Vaccin getThis(){
        Vaccin vaccin = vaccinDao.load(getMiaoId());
        return vaccin;
    }
    private Long getMiaoId(){
        Intent intentGetId = getIntent();
        Long miaoId = intentGetId.getLongExtra("MiaoId",0L);
        return miaoId;
    }
    private String getBaby(){
        Inoculation inoculation = selectBabyByParent();
        return inoculation.getInoculBaby();
    }
    private Inoculation selectBabyByParent(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);
        Inoculation inoculation = inoculationDao.queryBuilder().where(InoculationDao.Properties.ParentId.eq(userId)).unique();
        return inoculation;
    }
    private Long getHos(String certiArea){
        HosInfo hosInfo = hosInfoDao.queryBuilder().where(HosInfoDao.Properties.HosName.eq(certiArea)).unique();
        return hosInfo.getId();
    }
    private String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    private void insertMiaoOrder(String certiTime, String babyName, String certiArea){
        OrderVaccin orderVaccin = new OrderVaccin(certiTime,getDate(),selectBabyByParent().getId(),getMiaoId(),getHos(certiArea),0);
        orderVaccinDao.insert(orderVaccin);
    }

}
