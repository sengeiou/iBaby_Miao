package com.atyume.ibabym.ui.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.greendao.gen.VaccinDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.RecyclerAdapter;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.Vaccin;
import com.atyume.ibabym.ui.RecyclerViewList.DividerItemDecoration;
import com.atyume.ibabym.ui.home.MiaoViewActivity;
import com.atyume.ibabym.utils.MyLiveList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerMiaoActivity extends AppCompatActivity {

    @BindView(R.id.comeBack)
    TextView mComeBack;

    private List<MyLiveList> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter recyclerAdapter;

    private String[] data = {"九价HPV","四价HPV","二价HPV","三价流感疫苗","四价流感疫苗","狂犬病疫苗","水痘疫苗"};
    private VaccinDao vaccinDao = MyApplication.getInstances().getDaoSession().getVaccinDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_miao);

        ButterKnife.bind(this);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerMiaoActivity.this.finish();
            }
        });

        initView();

        initData();
        recyclerAdapter = new RecyclerAdapter(this,mDatas);
        mRecyclerView.setAdapter(recyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));  //设置间隔（可选水平或者垂直）
        recyclerAdapter.setOnMyItemClickListener(new RecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                Toast.makeText(RecyclerMiaoActivity.this,"onClick---"+pos+"mDatas:"+mDatas.get(pos).toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RecyclerMiaoActivity.this, ViewMiaoDetail.class);
                intent.putExtra("clickMiaoId",(mDatas.get(pos)).getId());
                startActivity(intent);
            }

            @Override
            public void mLongClick(View v, int pos) {
                Toast.makeText(RecyclerMiaoActivity.this,"onLongClick---"+pos,Toast.LENGTH_LONG).show();

                /*recyclerAdapter.removeData(pos);*/
            }
        });

    }
    private List<Vaccin> getData(){
        List<Vaccin> vList = new ArrayList<Vaccin>();
        vList = vaccinDao.loadAll();
        return vList;
    }
    private void initData() {
        if(getData() == null){
            return;
        }
        List<Vaccin> vaccinList = getData();
        for (int i = 0; i < vaccinList.size(); i++) {
            MyLiveList myLiveList = new MyLiveList();
            myLiveList.setTitle(vaccinList.get(i).getVaccinName());
            myLiveList.setSource("适用：" + vaccinList.get(i).getVaccinAge());
            myLiveList.setId(vaccinList.get(i).getId());
            mDatas.add(myLiveList);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        TextView mShowTitle = (TextView) findViewById(R.id.list_title);
        mShowTitle.setText("疫苗预约");
    }
}