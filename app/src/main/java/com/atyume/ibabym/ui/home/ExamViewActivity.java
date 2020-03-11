package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atyume.ibabym.R;
import com.atyume.ibabym.ui.RecyclerViewList.RecyclerViewListStyle;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamViewActivity  extends RecyclerViewListStyle {

    @BindView(R.id.btn_editor_add)
    TextView mBtnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initTopBar();
        setContentView(R.layout.miao_recycle_muti);
        ButterKnife.bind(this);

        initData();
        initListener();
    }
    @Override
    protected void initTopBar(){
        super.initTopBar();
        /*topbar.setTitle("体检项目管理");*/
    }
    @Override
    protected void initData(){
        super.initData();
    }
    @Override
    protected void initListener(){
        super.initListener();
    }
    @Override
    protected void addItem(){
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamViewActivity.this,EditExam.class);
                startActivity(intent);
            }
        });
    }
}

