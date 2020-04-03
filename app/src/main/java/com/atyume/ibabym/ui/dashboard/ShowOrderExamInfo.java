package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.greendao.gen.ExamProjectDao;
import com.atyume.greendao.gen.InoculationDao;
import com.atyume.greendao.gen.OrderExamInfoDao;
import com.atyume.greendao.gen.ProjectToExamDao;
import com.atyume.ibabym.Model.ExamInfoModel;
import com.atyume.ibabym.Model.InoculationModel;
import com.atyume.ibabym.Model.OrderExamModel;
import com.atyume.ibabym.Model.ProjectToExamModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderExamInfo;
import com.atyume.ibabym.basics.ProjectToExam;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowOrderExamInfo extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.show_examName)
    TextView mShowExamName;
    @BindView(R.id.show_examPrice)
    TextView mShowExamPrice;
    @BindView(R.id.show_examHos)
    TextView mShowExamHos;
    @BindView(R.id.show_examProject)
    TextView mShowExamProject;
    @BindView(R.id.show_OrderExamBaby)
    TextView mShowOrderExamBaby;
    @BindView(R.id.show_OrderExamTime)
    TextView mShowOrderExamTime;
    @BindView(R.id.count_wait)
    TextView mShowCountWait;
    @BindView(R.id.sure_Succeed)
    QMUIRoundButton mSureSucceed;
    @BindView(R.id.sure_Cancel)
    QMUIRoundButton mSureCancel;
    @BindView(R.id.relative_show_other)
    QMUIRelativeLayout mRelative;

    ExamInfoModel examInfoModel = new ExamInfoModel();
    ProjectToExamModel projectToExamModel = new ProjectToExamModel();
    OrderExamModel orderExamModel = new OrderExamModel();
    InoculationModel inoculationModel = new InoculationModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recoder_orderexam);
        ButterKnife.bind(this);
        setEditText();
        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowOrderExamInfo.this.finish();
            }
        });

        mSureSucceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderExamModel.updateSucceed(getOrderExamInfo());
                mSureSucceed.setVisibility(View.INVISIBLE);
                mSureCancel.setVisibility(View.INVISIBLE);
                mRelative.setVisibility(View.INVISIBLE);
            }
        });
        mSureCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderExamModel.updateCancel(getOrderExamInfo());
                mSureSucceed.setVisibility(View.INVISIBLE);
                mSureCancel.setVisibility(View.INVISIBLE);
                mRelative.setVisibility(View.INVISIBLE);
            }
        });

    }
    private void setEditText(){
        OrderExamInfo orderExamInfo = new OrderExamInfo();
        orderExamInfo = getOrderExamInfo();

        ExamInfo examInfo = new ExamInfo();
        examInfo = getExam();

        mShowExamName.setText(examInfo.getExamName());
        mShowExamHos.setText(examInfo.getExamHosName());
        mShowExamPrice.setText(examInfo.getExamPrice().toString());

        List<String> examProjectList = new ArrayList<String>();
        examProjectList = projectToExamModel.getProjectNameLists(orderExamInfo.getExamId());

        mShowExamProject.setText(examProjectList.toString());
        mShowOrderExamBaby.setText(getBabyName());
        mShowOrderExamTime.setText(orderExamInfo.getOrderTime());

        //        显示排队人数
        int waitCount = getCountWait(orderExamInfo.getOrderTime())-1;
        if(waitCount < 0){
            mRelative.setVisibility(View.INVISIBLE);
        }
        else{
            String wait = String.valueOf(waitCount);
            mShowCountWait.setText(wait);
        }
//         按钮的显示
        if(orderExamInfo.getIsSucced()==1){
            mSureSucceed.setText("已完成");
            mSureSucceed.setVisibility(View.INVISIBLE);
            mSureCancel.setVisibility(View.INVISIBLE);
            mRelative.setVisibility(View.INVISIBLE);
        }
        else if(orderExamInfo.getIsSucced()==2){
            mSureSucceed.setVisibility(View.INVISIBLE);
            mSureCancel.setVisibility(View.INVISIBLE);
            mRelative.setVisibility(View.INVISIBLE);
        }
    }


    private String getBabyName(){
        Long babyId = getOrderExamInfo().getInoculId();
        return inoculationModel.getBabyNameByBaby(babyId);
    }

    private ExamInfo getExam(){
        ExamInfo examInfo = new ExamInfo();
        Long examId = getOrderExamInfo().getExamId();
        examInfo = examInfoModel.getExamInfo(examId);
        return examInfo;
    }

    private OrderExamInfo getOrderExamInfo(){
        OrderExamInfo orderExamInfo = new OrderExamInfo();
        orderExamInfo = orderExamModel.getOrderExamInfo(getOrderExamId());
        return orderExamInfo;
    }

    private Long getOrderExamId(){
        Intent intentGetId = getIntent();
        Long orderExamId = intentGetId.getLongExtra("clickOrderExamId",0L);
        return orderExamId;
    }

    private int getCountWait(String orderTime){
        return orderExamModel.getOrderCountByDate(orderTime);
    }
}
