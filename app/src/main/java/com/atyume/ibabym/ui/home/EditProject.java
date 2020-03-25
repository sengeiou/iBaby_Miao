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

public class EditProject extends AppCompatActivity {
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
        initTop();
        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProject.this.finish();
            }
        });
        mbtnAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();
                if(TextUtils.isEmpty(ProjectName)){
                    Toast.makeText(EditProject.this, "请输入体检项目名称", Toast.LENGTH_SHORT).show();
                }
                insertProject(ProjectName,ProjectDetail,ProjectPrice);
                Intent intent = new Intent(EditProject.this, ProjectViewActivity.class);
                startActivity(intent);//返回页面1
                finish();
            }
        });
    }

    private void getEditText(){
        ProjectName = mEditProjectName.getText().toString();
        ProjectDetail = mEditProjectDetail.getText().toString();
        ProjectPrice = Double.parseDouble(mEditProjectPrice.getText().toString());
    }

    private void insertProject(String projectName,String projectDetail,Double projectPrice){
        ExamProject examProject = new ExamProject(projectName,projectDetail,projectPrice);
        long insert = examProjectDao.insert(examProject);
        if(insert > 0){
            Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
        }

    }
    private void initTop(){
        mTopBar.setText("新增体检项目");
    }
}
