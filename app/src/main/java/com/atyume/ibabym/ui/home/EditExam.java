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
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.MyApplication;
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
    private List<CheckBox> checkBoxList=new ArrayList<CheckBox>();
    private LinearLayout ll_checkBoxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_editexam);
        ButterKnife.bind(this);

        String[] checkboxText = new String[] { "您是学生吗？", "是否喜欢android？",
                "您喜欢旅游吗？", "打算出国吗？" };

        LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(
                R.layout.activity_editexam, null);



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
                if(TextUtils.isEmpty(ExamName)){
                    Toast.makeText(EditExam.this, "请输入体检套餐名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ExamHos)){
                    Toast.makeText(EditExam.this, "请输入体检医院", Toast.LENGTH_SHORT).show();
                    return;
                }
                insertData();
                Intent intent = new Intent(EditExam.this, ExamViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void insertData(){
        ExamInfo examInfo = new ExamInfo(ExamName, ExamPrice, ExamHos);
        long insert = examInfoDao.insert(examInfo);
        if (insert > 0) {
            Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
        }
    }
    private void getEditText(){
        ExamName = mEditExamName.getText().toString();
        ExamHos = mEditExamHos.getText().toString();
        ExamPrice = Double.parseDouble(mEditExamPrice.getText().toString());
    }
    private void initTop(){
        mTopBar.setText("新增体检套餐");
    }

}
