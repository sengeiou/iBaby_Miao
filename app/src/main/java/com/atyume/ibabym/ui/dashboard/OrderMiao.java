package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.ibabym.Model.HosInfoModel;
import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.Model.InoculationModel;
import com.atyume.ibabym.Model.OrderVaccinModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.HosInfo;
import com.atyume.ibabym.utils.MyOrderHosList;
import com.atyume.ibabym.utils.XCDropDownListView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderMiao extends AppCompatActivity{
    @BindView(R.id.comeBack)
    TextView mComeBack;
    @BindView(R.id.edit_OrderBabyName)
    EditText mOrderBabyName;
    @BindView(R.id.edit_OrderCertiArea)
    XCDropDownListView mOrderCertiArea;
    @BindView(R.id.edit_OrderCertiTime)
    EditText mOrderCertiTime;
    @BindView(R.id.button_sure_order_miao)
    QMUIRoundButton mbtnSureOrder;

    String babyName,certiTime;
    Long certiArea;

    InoculationModel inoculationModel = new InoculationModel();
    OrderVaccinModel orderVaccinModel = new OrderVaccinModel();
    HosInfoModel hosInfoModel = new HosInfoModel();

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
                makeMiaoOrder(certiTime,babyName,certiArea);

                Intent intent = new Intent(OrderMiao.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getEditText(){
        babyName = mOrderBabyName.getText().toString();
        certiArea = mOrderCertiArea.getHosId();
        certiTime = mOrderCertiTime.getText().toString();
    }
    private void setEditText(){
        mOrderBabyName.setText(getBaby());
        List<MyOrderHosList> myOrderHosLists = new ArrayList<MyOrderHosList>();
        myOrderHosLists = getHos(getMiaoId());
        mOrderCertiArea.setItemsData(myOrderHosLists);
    }

    private Long getMiaoId(){
        Intent intentGetId = getIntent();
        Long miaoId = intentGetId.getLongExtra("MiaoId",0L);
        return miaoId;
    }
    private String getBaby(){
        return inoculationModel.getBabyName(getUserId());
    }
    private Long getUserId(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);
        return userId;
    }

    private String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    private void makeMiaoOrder(String certiTime, String babyName, Long hosId){
        Long babyId = inoculationModel.getBabyIdByName(babyName);
        //在疫苗预约表中新增
        orderVaccinModel.insertOrderVaccin(certiTime,getDate(),babyId,getMiaoId(),hosId,0);
//        判断疫苗数量>0
        if(hosInfoModel.judgeVaccinAmount(hosId)){
            //在门诊信息表中疫苗数量减去1
            hosInfoModel.deleteVaccinAmount(hosId);
            Toast.makeText(OrderMiao.this, "预约成功"+" hos "+certiArea, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(OrderMiao.this, "预约失败"+" hos "+certiArea+"库存不足", Toast.LENGTH_SHORT).show();
        }

    }

    private List<MyOrderHosList> getHos(Long vaccinId){
        List<HosInfo> hosInfoList = new ArrayList<HosInfo>();
        hosInfoList = hosInfoModel.getHosInfoList(vaccinId);

        List<MyOrderHosList> myOrderHosLists = new ArrayList<MyOrderHosList>();
        if(hosInfoList!=null || !hosInfoList.isEmpty()){
            for(HosInfo hosInfo : hosInfoList){
                MyOrderHosList myOrderHos = new MyOrderHosList();
                myOrderHos.setHosId(hosInfo.getId());
                myOrderHos.setHosName(hosInfo.getHosName());
                myOrderHosLists.add(myOrderHos);
            }
        }
        return myOrderHosLists;
    }
}
