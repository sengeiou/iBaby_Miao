package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderExam extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;

    private ExamInfoDao examInfoDao = MyApplication.getInstances().getDaoSession().getExamInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order_exam);
        ButterKnife.bind(this);
        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderExam.this.finish();
            }
        });
    }
    private ExamInfo getThis(){
        Intent intentGetId = getIntent();
        Long examId = intentGetId.getLongExtra("clickExamId",0L);
        ExamInfo examInfo = examInfoDao.queryBuilder().where(ExamInfoDao.Properties.Id.eq(examId)).unique();
        return examInfo;
    }
}
