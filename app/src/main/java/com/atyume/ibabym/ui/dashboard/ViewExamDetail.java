package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.greendao.gen.ExamProjectDao;
import com.atyume.greendao.gen.ProjectToExamDao;
import com.atyume.ibabym.Model.ExamInfoModel;
import com.atyume.ibabym.Model.ProjectToExamModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ProjectToExam;
import com.google.android.material.tabs.TabLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewExamDetail extends AppCompatActivity {
    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.show_examName)
    TextView mShowExamName;
    @BindView(R.id.show_examPrice)
    TextView mShowExamPrice;
    @BindView(R.id.show_examHos)
    TextView mShowExamHos;
    @BindView(R.id.TableLayout_Project)
    TableLayout ShowExamProject;
    @BindView(R.id.button_take_order_exam)
    QMUIRoundButton mbtnOrderExam;

    ExamInfoModel examInfoModel = new ExamInfoModel();
    ProjectToExamModel projectToExamModel = new ProjectToExamModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_exam);
        ButterKnife.bind(this);
        setEditText();
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
                intent.putExtra("orderExamId",getExamId());
                startActivity(intent);
                finish();
            }
        });

    }
    private void setEditText(){
        ExamInfo examInfo = getThis();
        mShowExamName.setText(examInfo.getExamName());
        mShowExamHos.setText(examInfo.getExamHosName());
        mShowExamPrice.setText(examInfo.getExamPrice().toString());
        initTable();
    }

    private void initTable(){
        List<ExamProject> examProjectList = new ArrayList<ExamProject>();
        examProjectList = projectToExamModel.getExamProjects(getExamId());
        if(examProjectList!=null || !examProjectList.isEmpty()){
            for (int i = 0; i < examProjectList.size(); i++) {
                //创建一行
                TableRow row = new TableRow(getApplicationContext());
                //创建显示的内容,这里创建的是一列
                TextView text1 = new TextView(getApplicationContext());
                TextView text2 = new TextView(getApplicationContext());

                //设置显示内容
                text1.setText(examProjectList.get(i).getProjectName());
                text2.setText(examProjectList.get(i).getProjectDetail());
                text1.setBackgroundColor(Color.WHITE);
                text2.setBackgroundColor(Color.WHITE);
                LinearLayout.LayoutParams layoutParams = new TableRow.LayoutParams(-1,-1);
                layoutParams.setMargins(1,1,1,1);
                text1.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
                text2.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
                text1.setLayoutParams(layoutParams);
                text2.setLayoutParams(layoutParams);
                //添加到Row
                row.addView(text1);
                row.addView(text2);

                //将一行数据添加到表格中
                ShowExamProject.addView(row);
            }
        }
    }


    private ExamInfo getThis(){
        Long examId = getExamId();
        ExamInfo examInfo = new ExamInfo();
        examInfo = examInfoModel.getExamInfo(examId);
        return examInfo;
    }
    private Long getExamId(){
        Intent intentGetId = getIntent();
        Long examId = intentGetId.getLongExtra("clickExamId",0L);
        return examId;
    }

}
