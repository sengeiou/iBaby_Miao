package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.greendao.gen.InoculationDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderExam extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.edit_OrderExamBaby)
    EditText mEditOrderExamBaby;
    @BindView(R.id.edit_OrderHos)
    EditText mEditOrderHos;
    @BindView(R.id.edit_OrderExamTime)
    EditText getmEditOrderTime;

    private ExamInfoDao examInfoDao = MyApplication.getInstances().getDaoSession().getExamInfoDao();
    private InoculationDao inoculationDao = MyApplication.getInstances().getDaoSession().getInoculationDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order_exam);
        ButterKnife.bind(this);
        setEditText();
        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderExam.this.finish();
            }
        });
    }
    private void setEditText(){
        ExamInfo examInfo = getThis();
        mEditOrderExamBaby.setText(getBaby());
        mEditOrderHos.setText(examInfo.getExamHosName());
    }
    private ExamInfo getThis(){
        ExamInfo examInfo = examInfoDao.load(getExamId());
        return examInfo;
    }
    private Long getExamId(){
        Intent intentGetId = getIntent();
        Long examId = intentGetId.getLongExtra("orderExamId",0L);
        return examId;
    }
    private String getBaby(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);

        Inoculation inoculation = selectBabyByParent(userId);
        return inoculation.getInoculBaby();
    }
    private Inoculation selectBabyByParent(Long parentId){
        Inoculation inoculation = inoculationDao.queryBuilder().where(InoculationDao.Properties.ParentId.eq(parentId)).unique();
        return inoculation;
    }
}
