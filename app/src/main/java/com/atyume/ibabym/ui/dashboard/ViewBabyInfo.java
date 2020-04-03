package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.InoculationDao;
import com.atyume.ibabym.Model.InoculationModel;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.ui.home.EditBaby;
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

    InoculationModel inoculationModel = new InoculationModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbaby);
        ButterKnife.bind(this);

        initView();

        mComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewBabyInfo.this.finish();
            }
        });

        mEditMyBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inoculation inoculation = new Inoculation();
                if(getBaby()!=null) {
                    inoculation = getBaby();
                    Intent intent = new Intent(ViewBabyInfo.this, UpdateBaby.class);
                    intent.putExtra("BabyId",inoculation.getId());
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(ViewBabyInfo.this, EditBaby.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    private Inoculation getBaby(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("loginUserId",0L);

        Inoculation inoculation = new Inoculation();
        inoculation = inoculationModel.selectBabyByParent(userId);
        return inoculation;
    }


    private void initView(){
        Inoculation inoculation = new Inoculation();
        if(getBaby()!=null){
            inoculation = getBaby();
            mShowBabyName.setText(inoculation.getInoculBaby());
            mShowBabyHome.setText(inoculation.getBabyAdress());
            mShowBabyNow.setText(inoculation.getBabyHome());
            mShowBabyBirth.setText(inoculation.getBabyData());
            mShowBabySex.setText(inoculation.getBabySex());
        }
    }
}
