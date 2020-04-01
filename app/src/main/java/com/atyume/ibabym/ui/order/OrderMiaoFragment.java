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

import com.atyume.greendao.gen.InoculationDao;
import com.atyume.greendao.gen.OrderVaccinDao;
import com.atyume.greendao.gen.VaccinDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.OrderRecyclerAdapter;
import com.atyume.ibabym.adapter.RecyclerAdapter;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderVaccin;
import com.atyume.ibabym.basics.Vaccin;
import com.atyume.ibabym.ui.RecyclerViewList.DividerItemDecoration;
import com.atyume.ibabym.ui.dashboard.ShowOrderMiaoInfo;
import com.atyume.ibabym.ui.dashboard.ViewMiaoDetail;
import com.atyume.ibabym.utils.MyOrderList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class OrderMiaoFragment extends Fragment {
    private List<MyOrderList> mDatas = new ArrayList<MyOrderList>();
    @BindView(R.id.order_miao_recycler_view)
    RecyclerView mRecyclerView;
    private OrderRecyclerAdapter recyclerAdapter;

//    private String[] data = {"九价HPV","四价HPV","二价HPV","三价流感疫苗","四价流感疫苗","狂犬病疫苗","水痘疫苗"};
    private OrderVaccinDao orderVaccinDao = MyApplication.getInstances().getDaoSession().getOrderVaccinDao();
    private InoculationDao inoculationDao = MyApplication.getInstances().getDaoSession().getInoculationDao();
    private VaccinDao vaccinDao = MyApplication.getInstances().getDaoSession().getVaccinDao();

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
        mRecyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnMyItemClickListener(new OrderRecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                System.out.println("onClick---"+pos);
                Intent intent = new Intent(getActivity(), ShowOrderMiaoInfo.class);
                intent.putExtra("clickOrderMiaoId",(mDatas.get(pos)).getId());
                startActivity(intent);
            }

        });
        return root;
    }
    private void initData() {
        List<OrderVaccin> orderVaccinList = new ArrayList<OrderVaccin>();
        orderVaccinList = getOrderList();
        if(orderVaccinList != null || !orderVaccinList.isEmpty()){
            for (int i=0;i<orderVaccinList.size();i++) {
                MyOrderList myOrderList = new MyOrderList();
                myOrderList.setTitle(getVaccin(orderVaccinList.get(i).getVaccinId()));
                myOrderList.setIsfinish(getIsFinish(orderVaccinList.get(i).getIsSucceed()));
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
            orderVaccinList = orderVaccinDao.queryBuilder().where(OrderVaccinDao.Properties.InocluId.eq(selectBabyByParent().getId())).list();
        }
        return orderVaccinList;
    }

    private Inoculation selectBabyByParent(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationDao.queryBuilder().where(InoculationDao.Properties.ParentId.eq(userId)).unique();
        return inoculation;
    }
    private String getVaccin(Long vaccinId){
        Vaccin vaccin = vaccinDao.load(vaccinId);
        return vaccin.getVaccinName();
    }
    private String getIsFinish(Integer isFinish){
        if(isFinish==0){
            return "未完成";
        }
        return "已完成";
    }
}
