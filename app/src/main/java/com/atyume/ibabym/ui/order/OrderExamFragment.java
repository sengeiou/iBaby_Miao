package com.atyume.ibabym.ui.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.greendao.gen.InoculationDao;
import com.atyume.greendao.gen.OrderExamInfoDao;
import com.atyume.ibabym.Model.ExamInfoModel;
import com.atyume.ibabym.Model.InoculationModel;
import com.atyume.ibabym.Model.OrderExamModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.OrderRecyclerAdapter;
import com.atyume.ibabym.adapter.RecyclerAdapter;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderExamInfo;
import com.atyume.ibabym.ui.RecyclerViewList.DividerItemDecoration;
import com.atyume.ibabym.ui.dashboard.OrderExam;
import com.atyume.ibabym.ui.dashboard.RecyclerExamActivity;
import com.atyume.ibabym.ui.dashboard.ShowOrderExamInfo;
import com.atyume.ibabym.ui.dashboard.ViewExamDetail;
import com.atyume.ibabym.utils.MyOrderList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class OrderExamFragment extends Fragment {
    private List<MyOrderList> mDatas = new ArrayList<MyOrderList>();
    private OrderRecyclerAdapter recyclerAdapter;

    OrderExamModel orderExamModel = new OrderExamModel();
    ExamInfoModel examInfoModel = new ExamInfoModel();
    InoculationModel inoculationModel = new InoculationModel();

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order_miao, container, false);
        ButterKnife.bind(this,root);

        initData();

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.order_miao_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new OrderRecyclerAdapter(getActivity(), mDatas);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnMyItemClickListener(new OrderRecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                Toast.makeText(getActivity(),"onClick--"+mDatas.get(pos).getTitle(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), ShowOrderExamInfo.class);
                intent.putExtra("clickOrderExamId",(mDatas.get(pos)).getId());
                startActivity(intent);
            }

        });
        return root;
    }
    private void initData() {
        List<OrderExamInfo> orderExamInfoList = new ArrayList<OrderExamInfo>();
        orderExamInfoList = getOrderList();
        if(orderExamInfoList != null || !orderExamInfoList.isEmpty()){
            for (int i =0;i<orderExamInfoList.size();i++) {
                MyOrderList myOrderList = new MyOrderList();
                myOrderList.setTitle(examInfoModel.getExamName(orderExamInfoList.get(i).getExamId()));
                myOrderList.setIsfinish(getIsFinish(orderExamInfoList.get(i).getIsSucced()));
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
            orderExamInfoList = orderExamModel.getOrderList(inoculation.getId());
        }
        return orderExamInfoList;
    }

    private Inoculation selectBabyByParent(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationModel.selectBabyByParent(userId);
        return inoculation;
    }



    private String getIsFinish(Integer isFinish){
        if(isFinish==0){
            return "未完成";
        }
        else if(isFinish==2){
            return "已取消";
        }
       return "已完成";
    }
}
