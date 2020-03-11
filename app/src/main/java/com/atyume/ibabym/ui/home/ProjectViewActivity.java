package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.atyume.ibabym.R;
import com.atyume.ibabym.ui.RecyclerViewList.RecyclerViewListStyle;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectViewActivity  extends RecyclerViewListStyle {

    @BindView(R.id.btn_editor_add)
    TextView mBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.miao_recycle_muti);
        ButterKnife.bind(this);

        initData();
        initListener();
    }
    @Override
    protected void initTopBar(){
        super.initTopBar();

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
                Intent intent = new Intent(ProjectViewActivity.this,EditProject.class);
                startActivity(intent);
            }
        });
    }
}

