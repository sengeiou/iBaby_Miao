package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.Model.ExamInfoModel;
import com.atyume.ibabym.Model.InoculationModel;
import com.atyume.ibabym.Model.OrderExamModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.OrderRecyclerAdapter;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.OrderExamInfo;
import com.atyume.ibabym.utils.MyOrderList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamRecorderList extends AppCompatActivity {
    private List<MyOrderList> mDatas = new ArrayList<MyOrderList>();
    @BindView(R.id.comeBack)
    TextView mbtnComeBack;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private OrderRecyclerAdapter recyclerAdapter;

    OrderExamModel orderExamModel = new OrderExamModel();
    ExamInfoModel examInfoModel = new ExamInfoModel();
    InoculationModel inoculationModel = new InoculationModel();


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
                ExamRecorderList.this.finish();
            }
        });

        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new OrderRecyclerAdapter(this, mDatas);
        mRecyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnMyItemClickListener(new OrderRecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                Toast.makeText(ExamRecorderList.this, "onClick--" + mDatas.get(pos).getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ExamRecorderList.this, ShowOrderExamInfo.class);
                intent.putExtra("clickOrderExamId", (mDatas.get(pos)).getId());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        List<OrderExamInfo> orderExamInfoList = new ArrayList<OrderExamInfo>();
        orderExamInfoList = getOrderList();
        if(orderExamInfoList != null || !orderExamInfoList.isEmpty()){
            for (int i =0;i<orderExamInfoList.size();i++) {
                MyOrderList myOrderList = new MyOrderList();
                myOrderList.setTitle(examInfoModel.getExamName(orderExamInfoList.get(i).getExamId()));
                myOrderList.setIsfinish("已完成");
                myOrderList.setTake_Ordertime(orderExamInfoList.get(i).getTakeTime());
                myOrderList.setOrderTime(orderExamInfoList.get(i).getOrderTime());
                myOrderList.setId(orderExamInfoList.get(i).getId());
                mDatas.add(myOrderList);
            }
        }
    }

    private List<OrderExamInfo> getOrderList(){
        List<OrderExamInfo> orderExamInfoList = new ArrayList<OrderExamInfo>();
        Inoculation inoculation = new Inoculation();
        inoculation = selectBabyByParent();
        if(inoculation!=null){
            orderExamInfoList = orderExamModel.getAlreadyFinishOrderList(inoculation.getId());
        }
        return orderExamInfoList;
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
        topBar.setText("体检记录");
    }
}
