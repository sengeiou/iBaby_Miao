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
import com.atyume.ibabym.Model.ExamInfoModel;
import com.atyume.ibabym.Model.ExamProjectModel;
import com.atyume.ibabym.Model.ProjectToExamModel;
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

public class ExamInfoActivity extends AppCompatActivity {
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

    ExamInfoModel examInfoModel = new ExamInfoModel();
    ExamProjectModel examProjectModel = new ExamProjectModel();
    ProjectToExamModel projectToExamModel = new ProjectToExamModel();

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
        List<ExamProject> examProjectList = new ArrayList<ExamProject>();
        examProjectList = examProjectModel.getProjectList();

        ll_checkBoxList=(LinearLayout) findViewById(R.id.exam_checkboxlist);
        for(int i=0; i<examProjectList.size();i++){
            CheckBox checkBox=(CheckBox) View.inflate(this, R.layout.project_checkbox, null);
            checkBox.setText(examProjectList.get(i).getProjectName());
            ll_checkBoxList.addView(checkBox);
            checkBoxList.add(checkBox);
        }

        setEditText();
        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamInfoActivity.this.finish();
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
                    Toast.makeText(ExamInfoActivity.this, "请输入体检套餐名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ExamHos)){
                    Toast.makeText(ExamInfoActivity.this, "请输入体检医院", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateData(ExamName, ExamPrice, ExamHos);

                updateExamProject(ProjectNameList);
                Intent intent = new Intent(ExamInfoActivity.this, ExamViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void updateData(String ExamName, Double ExamPrice, String ExamHos){

        ExamInfo examInfo = new ExamInfo();
        examInfo = getThis();
        examInfoModel.updateExamInfo(examInfo,ExamName,ExamPrice,ExamHos);
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }

    private void updateExamProject(List<String> ProjectNameList){
        ExamInfo examInfo = getThis();
        Long examInfoId = examInfo.getId();
        //删除旧的套餐-单项
        deleteOldSelectId(examInfoId);
        //添加新的套餐-单项
        projectToExamModel.insertExamProject(examInfoId,ProjectNameList);

    }
    private ExamInfo getThis(){
        Intent intentGetId = getIntent();
        Long examId = intentGetId.getLongExtra("manageExamId",0L);
        ExamInfo examInfo = new ExamInfo();
        examInfo = examInfoModel.getExamInfo(examId);
        return examInfo;
    }

    private void setEditText(){
        ExamInfo examInfo = new ExamInfo();
        examInfo = getThis();

        mEditExamName.setText(examInfo.getExamName());
        mEditExamHos.setText(examInfo.getExamHosName());
        mEditExamPrice.setText(examInfo.getExamPrice().toString());

        List<String> projectNameList = new ArrayList<String>();
        projectNameList = projectToExamModel.getSelectProjectNameList(examInfo.getId());

        for(CheckBox checkBox:checkBoxList){
            for(int i=0;i<projectNameList.size();i++){
                if((checkBox.getText().toString()).equals(projectNameList.get(i))){
                    checkBox.setChecked(true);
                    break;
                }
            }
        }

    }
    private void getEditText(){
        ExamName = mEditExamName.getText().toString();
        ExamHos = mEditExamHos.getText().toString();
        ExamPrice = Double.parseDouble(mEditExamPrice.getText().toString());
    }

    private void deleteOldSelectId(Long examId){
        List<String> projectNameList = new ArrayList<String>();
        projectNameList = projectToExamModel.getSelectProjectNameList(examId);
        projectToExamModel.deleteOldSelectProjectById(examId,projectNameList);
    }

    private void initTop(){
        mTopBar.setText("体检套餐信息");
    }

}
