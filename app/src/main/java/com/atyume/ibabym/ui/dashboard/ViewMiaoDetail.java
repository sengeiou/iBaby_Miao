package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.ibabym.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMiaoDetail extends AppCompatActivity {
    @BindView(R.id.button_take_order_miao)
    QMUIRoundButton mbtnOrderMiao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_miao);
        ButterKnife.bind(this);
        mbtnOrderMiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewMiaoDetail.this, OrderMiao.class);
                startActivity(intent);
            }
        });
    }

}
