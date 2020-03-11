package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.atyume.ibabym.R;
import com.atyume.ibabym.ui.RecyclerViewList.RecyclerViewListStyle;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MiaoViewActivity extends RecyclerViewListStyle {
    /*private String[] data = {"九价HPV","四价HPV","二价HPV","三价流感疫苗","四价流感疫苗","狂犬病疫苗","水痘疫苗"};*/

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
                Intent intent = new Intent(MiaoViewActivity.this,EditMiao.class);
                startActivity(intent);
            }
        });
    }
}
