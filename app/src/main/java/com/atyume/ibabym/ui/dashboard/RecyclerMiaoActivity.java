package com.atyume.ibabym.ui.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.RecyclerAdapter;
import com.atyume.ibabym.ui.RecyclerViewList.DividerItemDecoration;
import com.atyume.ibabym.utils.MyLiveList;

import java.util.ArrayList;
import java.util.List;

public class RecyclerMiaoActivity extends AppCompatActivity {

    private List<MyLiveList> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter recyclerAdapter;

    private String[] data = {"九价HPV","四价HPV","二价HPV","三价流感疫苗","四价流感疫苗","狂犬病疫苗","水痘疫苗"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_miao);

        initView();

        initData();
        recyclerAdapter = new RecyclerAdapter(this,mDatas);
        mRecyclerView.setAdapter(recyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));  //设置间隔（可选水平或者垂直）
        recyclerAdapter.setOnMyItemClickListener(new RecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                Toast.makeText(RecyclerMiaoActivity.this,"onClick---"+pos,Toast.LENGTH_LONG).show();
                System.out.println("onClick---"+pos);
                Intent intent = new Intent(RecyclerMiaoActivity.this, ViewMiaoDetail.class);
                startActivity(intent);

                /*recyclerAdapter.addItem(pos);*/
            }

            @Override
            public void mLongClick(View v, int pos) {
                Toast.makeText(RecyclerMiaoActivity.this,"onLongClick---"+pos,Toast.LENGTH_LONG).show();
                System.out.println("onLongClick---"+pos);
                /*recyclerAdapter.removeData(pos);*/
            }
        });


    }

    private void initData() {
        for (int i = 0; i < data.length; i++) {
            MyLiveList myLiveList = new MyLiveList();
            myLiveList.setTitle(data[i]);
            myLiveList.setSource(data[i]);
            mDatas.add(myLiveList);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        TextView mShowTitle = (TextView) findViewById(R.id.list_title);
        mShowTitle.setText("疫苗预约");
    }
}