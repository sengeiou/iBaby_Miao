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
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.OrderExamInfo;
import com.atyume.ibabym.basics.ProjectToExam;

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

    private ExamInfoDao examInfoDao = MyApplication.getInstances().getDaoSession().getExamInfoDao();
    private ProjectToExamDao projectToExamDao = MyApplication.getInstances().getDaoSession().getProjectToExamDao();
    private ExamProjectDao examProjectDao = MyApplication.getInstances().getDaoSession().getExamProjectDao();
    private OrderExamInfoDao orderExamInfoDao = MyApplication.getInstances().getDaoSession().getOrderExamInfoDao();
    private InoculationDao inoculationDao = MyApplication.getInstances().getDaoSession().getInoculationDao();

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

    }
    private void setEditText(){
        OrderExamInfo orderExamInfo = new OrderExamInfo();
        orderExamInfo = getThis();
        ExamInfo examInfo = new ExamInfo();
        examInfo = getExam();
        mShowExamName.setText(examInfo.getExamName());
        mShowExamHos.setText(examInfo.getExamHosName());
        mShowExamPrice.setText(examInfo.getExamPrice().toString());
        List<String> examProjectList = new ArrayList<String>();
        examProjectList = getProjects();
        mShowExamProject.setText(examProjectList.toString());
        mShowOrderExamBaby.setText(getBabyName());
        mShowOrderExamTime.setText(orderExamInfo.getOrderTime());
    }


    private List<String> getProjects(){
        List<ProjectToExam> projectToExamList = projectToExamDao.queryBuilder().where(ProjectToExamDao.Properties.ExamId.eq(getExam().getId())).list();
        List<String> examProjectList = new ArrayList<String>();
        for(ProjectToExam projectToExam : projectToExamList){
            ExamProject examProject = examProjectDao.load(projectToExam.getProjectId());
            examProjectList.add(examProject.getProjectName());
        }
        return examProjectList;
    }

    private String getBabyName(){
        Inoculation inoculation = new Inoculation();
        inoculation = inoculationDao.load(getThis().getInoculId());
        return inoculation.getInoculBaby();
    }
    private ExamInfo getExam(){
        ExamInfo examInfo = new ExamInfo();
        examInfo = examInfoDao.load(getThis().getExamId());
        return examInfo;
    }

    private OrderExamInfo getThis(){
        Long orderExamId = getOrderExamId();
        OrderExamInfo orderExamInfo = new OrderExamInfo();
        orderExamInfo = orderExamInfoDao.load(orderExamId);
        return orderExamInfo;
    }
    private Long getOrderExamId(){
        Intent intentGetId = getIntent();
        Long orderExamId = intentGetId.getLongExtra("clickOrderExamId",0L);
        return orderExamId;
    }

}
