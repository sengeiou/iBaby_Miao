package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.ibabym.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewExamDetail extends AppCompatActivity {
    @BindView(R.id.button_take_order_exam)
    QMUIRoundButton mbtnOrderExam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_exam);
        ButterKnife.bind(this);
        mbtnOrderExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewExamDetail.this, OrderExam.class);
                startActivity(intent);
            }
        });
    }
}
