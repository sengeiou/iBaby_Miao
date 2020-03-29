package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.greendao.gen.InoculationDao;
import com.atyume.greendao.gen.OrderExamInfoDao;
import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderExamInfo;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    EditText mEditOrderTime;
    @BindView(R.id.button_sure_order_exam)
    QMUIRoundButton mbtnOrderExam;

    private String babyName,orderHos,orderTime;
    private ExamInfoDao examInfoDao = MyApplication.getInstances().getDaoSession().getExamInfoDao();
    private InoculationDao inoculationDao = MyApplication.getInstances().getDaoSession().getInoculationDao();
    private OrderExamInfoDao orderExamInfoDao = MyApplication.getInstances().getDaoSession().getOrderExamInfoDao();

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
        mbtnOrderExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();
                insertOrderExam(babyName,orderTime);
                Toast.makeText(OrderExam.this, "预约成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderExam.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getEditText(){
        babyName = mEditOrderExamBaby.getText().toString();
        orderHos = mEditOrderHos.getText().toString();
        orderTime = mEditOrderTime.getText().toString();
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
        return selectBabyByParent().getInoculBaby();
    }
    private Inoculation selectBabyByParent(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);
        Inoculation inoculation = inoculationDao.queryBuilder().where(InoculationDao.Properties.ParentId.eq(userId)).unique();
        return inoculation;
    }
    private String getDate(){        //现在系统时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    private void insertOrderExam(String babyName,String orderTime){
        Inoculation inoculation = inoculationDao.queryBuilder().where(InoculationDao.Properties.InoculBaby.eq(babyName)).unique();
        OrderExamInfo orderExamInfo = new OrderExamInfo(getExamId(),inoculation.getId(),getDate(),orderTime,0);
        orderExamInfoDao.insert(orderExamInfo);
    }

}
