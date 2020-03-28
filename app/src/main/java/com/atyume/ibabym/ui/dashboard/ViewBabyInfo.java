package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.InoculationDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.ui.notifications.UpdateBaby;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewBabyInfo extends AppCompatActivity {

    @BindView(R.id.comeBack)
    TextView mComeBack;
    @BindView(R.id.edit_myBaby)
    TextView mEditMyBaby;
    @BindView(R.id.show_babyName)
    TextView mShowBabyName;
    @BindView(R.id.show_babyBirth)
    TextView mShowBabyBirth;
    @BindView(R.id.show_babySex)
    TextView mShowBabySex;
    @BindView(R.id.show_homead)
    TextView mShowBabyHome;
    @BindView(R.id.show_nowad)
    TextView mShowBabyNow;

    private InoculationDao babydao = MyApplication.getInstances().getDaoSession().getInoculationDao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbaby);
        ButterKnife.bind(this);

        Inoculation inoculation = getBaby();
        Long viewBabyId = inoculation.getId();
        initView(inoculation);

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewBabyInfo.this.finish();
            }
        });

        mEditMyBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBabyInfo.this, UpdateBaby.class);
                intent.putExtra("BabyId",viewBabyId);
                startActivity(intent);
            }
        });

    }
    private Inoculation getBaby(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);

        Inoculation inoculation = selectBabyByParent(userId);
        return inoculation;
    }
    private Inoculation selectBabyByParent(Long parentId){
        Inoculation inoculation = babydao.queryBuilder().where(InoculationDao.Properties.ParentId.eq(parentId)).unique();
        return inoculation;
    }

    private void initView(Inoculation inoculation){
        mShowBabyName.setText(inoculation.getInoculBaby());
        mShowBabyHome.setText(inoculation.getBabyHome());
        mShowBabyNow.setText(inoculation.getBabyAdress());
    }
}
