package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ExamProjectDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.MyApplication;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectInfo extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;
    @BindView(R.id.edit_project_topbar)
    TextView mTopBar;
    @BindView(R.id.edit_projectName)
    EditText mEditProjectName;
    @BindView(R.id.edit_projectDetail)
    EditText mEditProjectDetail;
    @BindView(R.id.edit_projectPrice)
    EditText mEditProjectPrice;
    @BindView(R.id.button_add_project)
    QMUIRoundButton mbtnAddProject;
    String ProjectName,ProjectDetail;
    Double ProjectPrice;
    private ExamProjectDao examProjectDao = MyApplication.getInstances().getDaoSession().getExamProjectDao();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editproject);
        ButterKnife.bind(this);
        /*初始化*/
        initTop();
        setEditText();

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectInfo.this.finish();
            }
        });
        mbtnAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();
                if(TextUtils.isEmpty(ProjectName)){
                    Toast.makeText(ProjectInfo.this, "请输入体检项目名称", Toast.LENGTH_SHORT).show();
                }
                updateProject(ProjectName,ProjectDetail,ProjectPrice);
                Intent intent = new Intent(ProjectInfo.this, ProjectViewActivity.class);
                startActivity(intent);//返回页面1
                finish();
            }
        });
    }

    private  ExamProject getThis(){
        Intent intentGetId = getIntent();
        Long ProjectId = intentGetId.getLongExtra("manageProjectId",0L);
        ExamProject examProject = examProjectDao.load(ProjectId);
        return examProject;
    }
    private void setEditText(){
        ExamProject examProject = getThis();
        mEditProjectName.setText(examProject.getProjectName());
        mEditProjectDetail.setText(examProject.getProjectDetail());
        mEditProjectPrice.setText(examProject.getProjectPrice().toString());
    }
    private void getEditText(){
        ProjectName = mEditProjectName.getText().toString();
        ProjectDetail = mEditProjectDetail.getText().toString();
        ProjectPrice = Double.parseDouble(mEditProjectPrice.getText().toString());
    }

    private void updateProject(String projectName,String projectDetail,Double projectPrice){
        ExamProject examProject = getThis();
        examProject.setProjectName(projectName);
        examProject.setProjectDetail(projectDetail);
        examProject.setProjectPrice(projectPrice);
        examProjectDao.update(examProject);
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();

    }
    private void initTop(){
        mTopBar.setText("修改体检项目信息");
    }
}
