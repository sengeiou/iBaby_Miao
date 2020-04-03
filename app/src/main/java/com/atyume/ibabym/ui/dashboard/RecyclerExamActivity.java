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

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.ibabym.Model.ExamInfoModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.RecyclerAdapter;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.ui.RecyclerViewList.DividerItemDecoration;
import com.atyume.ibabym.utils.MyExamList;
import com.atyume.ibabym.utils.MyLiveList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerExamActivity extends AppCompatActivity {

    @BindView(R.id.comeBack)
    TextView mComeBack;
    @BindView(R.id.tv_select_text)
    EditText mEditSelect;
    @BindView(R.id.tv_sure)
    TextView mSureSelect;

    private List<MyLiveList> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter recyclerAdapter;

    ExamInfoModel examInfoModel = new ExamInfoModel();
    String selectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_miao);
        ButterKnife.bind(this);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerExamActivity.this.finish();
            }
        });

        mSureSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectText = mEditSelect.getText().toString();
                Intent intent = new Intent(RecyclerExamActivity.this, SelectExamShowList.class);
                intent.putExtra("selectText",selectText);
                startActivity(intent);
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
                Toast.makeText(RecyclerExamActivity.this,"onClick---"+pos,Toast.LENGTH_LONG).show();
                System.out.println("onClick---"+pos);
                Intent intent = new Intent(RecyclerExamActivity.this, ViewExamDetail.class);
                intent.putExtra("clickExamId",(mDatas.get(pos)).getId());
                startActivity(intent);

                /*recyclerAdapter.addItem(pos);*/

            }

            @Override
            public void mLongClick(View v, int pos) {
                Toast.makeText(RecyclerExamActivity.this,"onLongClick---"+pos,Toast.LENGTH_LONG).show();
                System.out.println("onLongClick---"+pos);
                /*recyclerAdapter.removeData(pos);*/
            }
        });


    }

    private void initData() {
        List<ExamInfo> examInfoList = new ArrayList<ExamInfo>();
        examInfoList = examInfoModel.getExamList();
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

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        TextView mShowTitle = (TextView) findViewById(R.id.list_title);
        mShowTitle.setText("体检预约");
    }
}