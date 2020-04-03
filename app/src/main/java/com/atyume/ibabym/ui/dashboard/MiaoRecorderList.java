package com.atyume.ibabym.ui.dashboard;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.Model.InoculationModel;
import com.atyume.ibabym.Model.OrderVaccinModel;
import com.atyume.ibabym.Model.VaccinModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.OrderRecyclerAdapter;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.OrderVaccin;
import com.atyume.ibabym.utils.MyOrderList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MiaoRecorderList extends AppCompatActivity {

    private List<MyOrderList> mDatas = new ArrayList<MyOrderList>();
    @BindView(R.id.comeBack)
    TextView mbtnComeBack;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private OrderRecyclerAdapter recyclerAdapter;

    OrderVaccinModel orderVaccinModel = new OrderVaccinModel();
    InoculationModel inoculationModel = new InoculationModel();
    VaccinModel vaccinModel = new VaccinModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        ButterKnife.bind(this);
        initTopBar();
        initData();

        mbtnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiaoRecorderList.this.finish();
            }
        });

        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new OrderRecyclerAdapter(this,mDatas);
        mRecyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnMyItemClickListener(new OrderRecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                System.out.println("onClick---"+pos);
                Intent intent = new Intent(MiaoRecorderList.this, ShowOrderMiaoInfo.class);
                intent.putExtra("clickOrderMiaoId",(mDatas.get(pos)).getId());
                startActivity(intent);
            }

        });

    }
    private void initData() {
        List<OrderVaccin> orderVaccinList = new ArrayList<OrderVaccin>();
        orderVaccinList = getOrderList();
        if(orderVaccinList != null || !orderVaccinList.isEmpty()){
            for (int i=0;i<orderVaccinList.size();i++) {
                MyOrderList myOrderList = new MyOrderList();
                myOrderList.setTitle(vaccinModel.getVaccinNameById(orderVaccinList.get(i).getVaccinId()));
                myOrderList.setIsfinish("已完成");
                myOrderList.setTake_Ordertime(orderVaccinList.get(i).getOrderVaccinTime());
                myOrderList.setOrderTime(orderVaccinList.get(i).getInocluTime());
                myOrderList.setId(orderVaccinList.get(i).getId());
                mDatas.add(myOrderList);
            }
        }
    }
    private List<OrderVaccin> getOrderList(){
        List<OrderVaccin> orderVaccinList = new ArrayList<OrderVaccin>();
        Inoculation inoculation = new Inoculation();
        inoculation = selectBabyByParent();
        if(inoculation != null){
            orderVaccinList = orderVaccinModel.getAlreadyFinishOrderList(inoculation.getId());
        }
        return orderVaccinList;
    }

    private Inoculation selectBabyByParent(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationModel.selectBabyByParent(userId);
        return inoculation;
    }

    private void initTopBar(){
        TextView topBar = (TextView)findViewById(R.id.list_title);
        topBar.setText("已种疫苗");
    }
}
