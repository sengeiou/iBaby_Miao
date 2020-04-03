package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.Model.VaccinModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.RecyclerAdapter;

import com.atyume.ibabym.basics.Vaccin;
import com.atyume.ibabym.ui.RecyclerViewList.DividerItemDecoration;
import com.atyume.ibabym.utils.MyLiveList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectMiaoShowList extends AppCompatActivity {

    @BindView(R.id.comeBack)
    TextView mComeBack;


    private List<MyLiveList> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter recyclerAdapter;

    VaccinModel vaccinModel = new VaccinModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        ButterKnife.bind(this);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectMiaoShowList.this.finish();
            }
        });

        initView();

        initData();
        recyclerAdapter = new RecyclerAdapter(this, mDatas);
        mRecyclerView.setAdapter(recyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));  //设置间隔（可选水平或者垂直）
        recyclerAdapter.setOnMyItemClickListener(new RecyclerAdapter.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                Toast.makeText(SelectMiaoShowList.this, "onClick---" + pos + "mDatas:" + mDatas.get(pos).toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SelectMiaoShowList.this, ViewMiaoDetail.class);
                intent.putExtra("clickMiaoId", (mDatas.get(pos)).getId());
                startActivity(intent);
            }

            @Override
            public void mLongClick(View v, int pos) {
                Toast.makeText(SelectMiaoShowList.this, "onLongClick---" + pos, Toast.LENGTH_LONG).show();

                /*recyclerAdapter.removeData(pos);*/
            }
        });

    }

    private void initData() {
        List<Vaccin> vaccinList = new ArrayList<Vaccin>();
        vaccinList = getVaccinList();
        if (vaccinList != null || !vaccinList.isEmpty()) {
            for (int i = 0; i < vaccinList.size(); i++) {
                MyLiveList myLiveList = new MyLiveList();
                myLiveList.setTitle(vaccinList.get(i).getVaccinName());
                myLiveList.setSource("适用：" + vaccinList.get(i).getVaccinAge());
                myLiveList.setId(vaccinList.get(i).getId());
                mDatas.add(myLiveList);
            }
        }
    }

    private List<Vaccin> getVaccinList(){
        if(getSelectTect()==null || getSelectTect().equals("")){
            return vaccinModel.geVaccinList();
        }
        return vaccinModel.getVaccinListByName(getSelectTect());
    }

    private String getSelectTect(){
        Intent intent = getIntent();
        String selectText = intent.getStringExtra("selectText");
        return selectText;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        TextView mShowTitle = (TextView) findViewById(R.id.list_title);
        mShowTitle.setText("疫苗信息");
    }
}
