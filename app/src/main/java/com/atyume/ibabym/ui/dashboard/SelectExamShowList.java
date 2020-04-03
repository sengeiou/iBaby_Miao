package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.Model.ExamInfoModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.RecyclerAdapter;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.ui.RecyclerViewList.DividerItemDecoration;
import com.atyume.ibabym.utils.MyLiveList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectExamShowList extends AppCompatActivity {

    @BindView(R.id.comeBack)
    TextView mComeBack;

    private List<MyLiveList> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter recyclerAdapter;

    ExamInfoModel examInfoModel = new ExamInfoModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        ButterKnife.bind(this);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectExamShowList.this.finish();
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
                Toast.makeText(SelectExamShowList.this,"onClick---"+pos,Toast.LENGTH_LONG).show();
                System.out.println("onClick---"+pos);
                Intent intent = new Intent(SelectExamShowList.this, ViewExamDetail.class);
                intent.putExtra("clickExamId",(mDatas.get(pos)).getId());
                startActivity(intent);

                /*recyclerAdapter.addItem(pos);*/

            }

            @Override
            public void mLongClick(View v, int pos) {
                Toast.makeText(SelectExamShowList.this,"onLongClick---"+pos,Toast.LENGTH_LONG).show();
                System.out.println("onLongClick---"+pos);
                /*recyclerAdapter.removeData(pos);*/
            }
        });
    }

    private void initData() {
        List<ExamInfo> examInfoList = new ArrayList<ExamInfo>();
        examInfoList = getExamList();
        //数据
        if(examInfoList!=null || !examInfoList.isEmpty()){
            for (int i = 0; i < examInfoList.size(); i++) {
                MyLiveList myExamList = new MyLiveList();
                myExamList.setTitle(examInfoList.get(i).getExamName());
                myExamList.setSource(examInfoList.get(i).getExamHosName());
                myExamList.setId(examInfoList.get(i).getId());
                mDatas.add(myExamList);
            }
        }

    }

    private List<ExamInfo> getExamList(){
        if(getSelectTect()==null || getSelectTect().equals("")){
            return examInfoModel.getExamList();
        }
        return examInfoModel.getExamListByName(getSelectTect());
    }

    private String getSelectTect(){
        Intent intent = getIntent();
        String selectText = intent.getStringExtra("selectText");
        return selectText;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        TextView mShowTitle = (TextView) findViewById(R.id.list_title);
        mShowTitle.setText("体检套餐");
    }
}