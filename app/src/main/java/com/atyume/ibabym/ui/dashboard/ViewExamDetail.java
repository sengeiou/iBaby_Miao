package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.MyApplication;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewExamDetail extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.edit_examName)
    TextView mEditExamName;
    @BindView(R.id.edit_examPrice)
    TextView mEditExamPrice;
    @BindView(R.id.edit_examProject)
    TextView mEditExamProject;
    @BindView(R.id.button_take_order_exam)
    QMUIRoundButton mbtnOrderExam;

    private ExamInfoDao examInfoDao = MyApplication.getInstances().getDaoSession().getExamInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_exam);
        ButterKnife.bind(this);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewExamDetail.this.finish();
            }
        });

        mbtnOrderExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewExamDetail.this, OrderExam.class);
                startActivity(intent);
            }
        });
    }
    private void setEditText(){
        ExamInfo examInfo = new ExamInfo();
        examInfo = getThis();


    }
    private ExamInfo getThis(){
        Intent intentGetId = getIntent();
        Long examId = intentGetId.getLongExtra("clickExamId",0L);
        ExamInfo examInfo = examInfoDao.queryBuilder().where(ExamInfoDao.Properties.Id.eq(examId)).unique();
        return examInfo;
    }

}
