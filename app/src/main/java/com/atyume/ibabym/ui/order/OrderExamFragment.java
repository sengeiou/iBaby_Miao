package com.atyume.ibabym.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.OrderRecyclerAdapter;
import com.atyume.ibabym.ui.RecyclerViewList.DividerItemDecoration;
import com.atyume.ibabym.utils.MyOrderList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderExamFragment  extends Fragment {
    private List<MyOrderList> mDatas = new ArrayList<>();
    private OrderRecyclerAdapter recyclerAdapter;

    private String[] data = {"儿童6个月体检套餐","儿童一岁体检套餐","儿童一岁半体检套餐","儿童二岁体检套餐","儿童二岁半体检套餐","儿童三岁体检套餐","儿童三岁半体检套餐"};

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
        /*mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),DividerItemDecoration.VERTICAL_LIST));  //设置间隔（可选水平或者垂直）*/
        /*recyclerAdapter.setOnMyItemClickListener(new OrderRecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                System.out.println("onClick---"+pos);
                Intent intent = new Intent(getActivity(),ViewMiaoDetail.class);
                startActivity(intent);
r
            }

        });*/
        return root;
    }
    private void initData() {
        for (int i = 0; i < data.length; i++) {
            MyOrderList myOrderList = new MyOrderList();
            myOrderList.setTitle(data[i]);
            myOrderList.setIsfinish("未完成");
            myOrderList.setTake_Ordertime("2020-03-15");
            myOrderList.setOrderTime("2020-03-17");
            mDatas.add(myOrderList);
        }
    }
}
