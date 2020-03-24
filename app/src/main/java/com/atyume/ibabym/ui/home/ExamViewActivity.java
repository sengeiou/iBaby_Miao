package com.atyume.ibabym.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.greendao.gen.ExamInfoDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.ExamRadioAdapter;
import com.atyume.ibabym.adapter.MineRadioAdapter;
import com.atyume.ibabym.basics.ExamInfo;
import com.atyume.ibabym.basics.ExamProject;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.ui.RecyclerViewList.DividerItemDecoration;
import com.atyume.ibabym.ui.RecyclerViewList.RecyclerViewListStyle;
import com.atyume.ibabym.utils.MyExamList;
import com.atyume.ibabym.utils.MyLiveList;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamViewActivity extends Activity implements View.OnClickListener, ExamRadioAdapter.OnItemClickListener {

    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;

    @BindView(R.id.comeBack)
    TextView mComeBack;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_select_num)
    TextView mTvSelectNum;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.select_all)
    TextView mSelectAll;
    @BindView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout mLlMycollectionBottomDialog;
    @BindView(R.id.btn_editor)
    TextView mBtnEditor;
    @BindView(R.id.btn_editor_add)
    TextView mBtnAdd;
    private ExamRadioAdapter mRadioAdapter = null;
    private LinearLayoutManager mLinearLayoutManager;
    private List<MyExamList> mList = new ArrayList<>();
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private int index = 0;

    private ExamInfoDao examInfoDao = MyApplication.getInstances().getDaoSession().getExamInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miao_recycle_muti);
        ButterKnife.bind(this);

        initData();
        initListener();
    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    protected void setBtnBackground(int size) {
        if (size != 0) {
            mBtnDelete.setBackgroundResource(R.drawable.button_shape);
            mBtnDelete.setEnabled(true);
            mBtnDelete.setTextColor(Color.WHITE);
        } else {
            mBtnDelete.setBackgroundResource(R.drawable.button_noclickable_shape);
            mBtnDelete.setEnabled(false);
            mBtnDelete.setTextColor(ContextCompat.getColor(this, R.color.color_b7b8bd));
        }
    }

    protected void initData(){
        mRadioAdapter = new ExamRadioAdapter(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration itemDecorationHeader = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        itemDecorationHeader.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_main_bg_height_1));
        mRecyclerview.addItemDecoration(itemDecorationHeader);
        mRecyclerview.setAdapter(mRadioAdapter);

        mRadioAdapter.setOnMyItemClickListener((v, pos) -> {
            Toast.makeText(ExamViewActivity.this,"onClick---"+pos+"mDatas:"+mList.get(pos).toString(),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ExamViewActivity.this, EditExam.class);
            intent.putExtra("manageExamId",(mList.get(pos)).getExamId());
            startActivity(intent);
        });

        List<ExamInfo> examInfoList = getThis();
        //数据
        for (int i = 0; i < examInfoList.size(); i++) {
            MyExamList myExamList = new MyExamList();
            myExamList.setExamName(examInfoList.get(i).getExamName());
            myExamList.setExamHos(examInfoList.get(i).getExamHosName());
            myExamList.setExamPrice(examInfoList.get(i).getExamPrice());
            myExamList.setExamId(examInfoList.get(i).getId());
            mList.add(myExamList);
            mRadioAdapter.notifyAdapter(mList, false);
        }
    }
    private List<ExamInfo> getThis(){
        List<ExamInfo> examInfoList = examInfoDao.loadAll();
        return examInfoList;
    }

    protected void initListener(){
        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamViewActivity.this.finish();
            }
        });
        mRadioAdapter.setOnItemClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mSelectAll.setOnClickListener(this);
        mBtnEditor.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete:
                deleteVideo();
                break;
            case R.id.select_all:
                selectAllMain();
                break;
            case R.id.btn_editor:
                updataEditMode();
                break;
            case R.id.btn_editor_add:
                addItem();
                break;
            default:
                break;
        }
    }


    protected void addItem(){
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamViewActivity.this,EditExam.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 全选和反选
     */
    protected void selectAllMain() {
        if (mRadioAdapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = mRadioAdapter.getMyExamList().size(); i < j; i++) {
                mRadioAdapter.getMyExamList().get(i).setSelect(true);
            }
            index = mRadioAdapter.getMyExamList().size();
            mBtnDelete.setEnabled(true);
            mSelectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = mRadioAdapter.getMyExamList().size(); i < j; i++) {
                mRadioAdapter.getMyExamList().get(i).setSelect(false);
            }
            index = 0;
            mBtnDelete.setEnabled(false);
            mSelectAll.setText("全选");
            isSelectAll = false;
        }
        mRadioAdapter.notifyDataSetChanged();
        setBtnBackground(index);
        mTvSelectNum.setText(String.valueOf(index));
    }

    /**
     * 删除逻辑
     */
    protected void deleteVideo() {
        if (index == 0){
            mBtnDelete.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = mRadioAdapter.getMyExamList().size(), j =0 ; i > j; i--) {
                    MyExamList myExam = mRadioAdapter.getMyExamList().get(i-1);
                    if (myExam.isSelect()) {
                        mRadioAdapter.getMyExamList().remove(myExam);
                        index--;

                        examInfoDao.deleteByKey(myExam.getExamId());
                    }
                }
                index = 0;
                mTvSelectNum.setText(String.valueOf(0));
                setBtnBackground(index);
                if (mRadioAdapter.getMyExamList().size() == 0){
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                }
                mRadioAdapter.notifyDataSetChanged();
                builder.dismiss();
            }
        });
    }
    protected void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            mBtnEditor.setText("取消");
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
            editorStatus = true;
        } else {
            mBtnEditor.setText("编辑");
            mLlMycollectionBottomDialog.setVisibility(View.GONE);
            editorStatus = false;
            clearAll();
        }
        mRadioAdapter.setEditMode(mEditMode);
    }


    protected void clearAll() {
        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
        mSelectAll.setText("全选");
        setBtnBackground(0);
    }

    @Override
    public void onItemClickListener(int pos, List<MyExamList> myExamList) {
        if (editorStatus) {
            MyExamList myExam = myExamList.get(pos);
            boolean isSelect = myExam.isSelect();
            if (!isSelect) {
                index++;
                myExam.setSelect(true);
                if (index == myExamList.size()) {
                    isSelectAll = true;
                    mSelectAll.setText("取消全选");
                }

            } else {
                myExam.setSelect(false);
                index--;
                isSelectAll = false;
                mSelectAll.setText("全选");
            }
            setBtnBackground(index);
            mTvSelectNum.setText(String.valueOf(index));
            mRadioAdapter.notifyDataSetChanged();
        }
    }
}

