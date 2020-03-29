package com.atyume.ibabym.ui.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.greendao.gen.InoculationDao;
import com.atyume.greendao.gen.OrderExamInfoDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.OrderRecyclerAdapter;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderExamInfo;
import com.atyume.ibabym.ui.RecyclerViewList.DividerItemDecoration;
import com.atyume.ibabym.ui.dashboard.OrderExam;
import com.atyume.ibabym.utils.MyOrderList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class OrderExamFragment  extends Fragment {
    private List<MyOrderList> mDatas = new ArrayList<>();
    private OrderRecyclerAdapter recyclerAdapter;

    /*private String[] data = {"儿童6个月体检套餐","儿童一岁体检套餐","儿童一岁半体检套餐","儿童二岁体检套餐","儿童二岁半体检套餐","儿童三岁体检套餐","儿童三岁半体检套餐"};*/
    private OrderExamInfoDao orderExamInfoDao = MyApplication.getInstances().getDaoSession().getOrderExamInfoDao();
    private ExamInfoDao examInfoDao = MyApplication.getInstances().getDaoSession().getExamInfoDao();
    private InoculationDao inoculationDao = MyApplication.getInstances().getDaoSession().getInoculationDao();

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

        recyclerAdapter = new OrderRecyclerAdapter(getActivity(),mDatas);
        recyclerView.setAdapter(recyclerAdapter);
        return root;
    }
    private void initData() {
        if(getOrderList() == null){
            return;
        }
        List<OrderExamInfo> orderExamInfoList = getOrderList();
        for (OrderExamInfo orderExamInfo : orderExamInfoList) {
            MyOrderList myOrderList = new MyOrderList();
            myOrderList.setTitle(getExamName(orderExamInfo.getExamId()));
            myOrderList.setIsfinish(getIsFinish(orderExamInfo.getIsSucced()));
            myOrderList.setTake_Ordertime(orderExamInfo.getTakeTime());
            myOrderList.setOrderTime(orderExamInfo.getOrderTime());
            mDatas.add(myOrderList);
        }
    }

    private List<OrderExamInfo> getOrderList(){
        List<OrderExamInfo> orderExamInfoList = new ArrayList<OrderExamInfo>();
        orderExamInfoList = orderExamInfoDao.queryBuilder().where(OrderExamInfoDao.Properties.InoculId.eq(selectBabyByParent().getId())).list();
        return orderExamInfoList;
    }

    private Inoculation selectBabyByParent(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationDao.queryBuilder().where(InoculationDao.Properties.ParentId.eq(userId)).unique();
        return inoculation;
    }

    private String getExamName(Long examId){
        ExamInfo examInfo = examInfoDao.load(examId);
        return examInfo.getExamName();
    }
    private String getIsFinish(Integer isFinish){
        if(isFinish==0){
            return "未完成";
        }
        return "已完成";
    }
}
