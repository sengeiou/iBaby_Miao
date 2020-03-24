package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.greendao.gen.ExamProjectDao;
import com.atyume.greendao.gen.ProjectToExamDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ProjectToExam;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditExam extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;
    @BindView(R.id.edit_ExamTop)
    TextView mTopBar;
    @BindView(R.id.edit_examName)
    EditText mEditExamName;
    @BindView(R.id.edit_examHos)
    EditText mEditExamHos;
    @BindView(R.id.edit_examPrice)
    EditText mEditExamPrice;
    @BindView(R.id.button_add_exam)
    QMUIRoundButton mbtnAddExam;

    String ExamName, ExamHos;
    Double ExamPrice;

    private ExamInfoDao examInfoDao = MyApplication.getInstances().getDaoSession().getExamInfoDao();
    private ExamProjectDao examProjectDao = MyApplication.getInstances().getDaoSession().getExamProjectDao();
    private ProjectToExamDao projectToExamDao = MyApplication.getInstances().getDaoSession().getProjectToExamDao();
    private List<CheckBox> checkBoxList=new ArrayList<CheckBox>();
    private List<String> ProjectNameList = new ArrayList<String>();
    private LinearLayout ll_checkBoxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editexam);
        ButterKnife.bind(this);

//        String[] checkboxText = new String[] { "项目1", "项目2",
//                "项目3", "项目4" };
        List<ExamProject> examProjectList = getProjectList();

        ll_checkBoxList=(LinearLayout) findViewById(R.id.exam_checkboxlist);
        for(int i=0; i<examProjectList.size();i++){
            CheckBox checkBox=(CheckBox) View.inflate(this, R.layout.project_checkbox, null);
            checkBox.setText(examProjectList.get(i).getProjectName());
            ll_checkBoxList.addView(checkBox);
            checkBoxList.add(checkBox);
        }

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditExam.this.finish();
            }
        });

        mbtnAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText();
                for(CheckBox checkBox:checkBoxList){
                    if(checkBox.isChecked()){
                        ProjectNameList.add(checkBox.getText().toString());
                    }
                }

                if(TextUtils.isEmpty(ExamName)){
                    Toast.makeText(EditExam.this, "请输入体检套餐名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ExamHos)){
                    Toast.makeText(EditExam.this, "请输入体检医院", Toast.LENGTH_SHORT).show();
                    return;
                }

                insertData(ExamName, ExamPrice, ExamHos);

                insertExamProject(ExamName, ProjectNameList);
                Intent intent = new Intent(EditExam.this, ExamViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void insertData(String ExamName, Double ExamPrice, String ExamHos){
        long insert = examInfoDao.insert(new ExamInfo(ExamName,ExamPrice,ExamHos));
        if (insert > 0) {
            Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
        }
    }
    private void insertExamProject(String ExamName, List<String> ProjectNameList){
        ExamInfo examInfo = examInfoDao.queryBuilder().where(ExamInfoDao.Properties.ExamName.eq(ExamName),ExamInfoDao.Properties.ExamHosName.eq(ExamHos)).unique();
        Long examInfoId = examInfo.getId();
        List<Long> ProjectIdList = new ArrayList<Long>();
        for(int i=0; i<ProjectNameList.size();i++){
            ExamProject project = examProjectDao.queryBuilder().where(ExamProjectDao.Properties.ProjectName.eq(ProjectNameList.get(i))).unique();
            Long projectId = project.getId();
            ProjectIdList.add(projectId);
            projectToExamDao.insert(new ProjectToExam(examInfoId, projectId));
        }

    }
    private void getEditText(){
        ExamName = mEditExamName.getText().toString();
        ExamHos = mEditExamHos.getText().toString();
        ExamPrice = Double.parseDouble(mEditExamPrice.getText().toString());
    }

    private List<ExamProject> getProjectList(){
        List<ExamProject> examProjectList = examProjectDao.loadAll();
        return examProjectList;
    }
    private void initTop(){
        mTopBar.setText("新增体检套餐");
    }

}
