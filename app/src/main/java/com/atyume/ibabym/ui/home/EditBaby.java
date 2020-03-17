package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.InoculationDao;

import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditBaby extends AppCompatActivity {

    @BindView(R.id.button_add_baby)
    QMUIRoundButton mbtnAddBaby;

   /* private final String TAG = EditBaby.class.getSimpleName();
    DaoUtils mdaoUtils;*/

    private Long inoculationId = 1001L;
    private InoculationDao dao = MyApplication.getInstances().getDaoSession().getInoculationDao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbaby);
        ButterKnife.bind(this);
        /*mdaoUtils = new DaoUtils(this);*/

        mbtnAddBaby.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                /*Inoculation baby  = new Inoculation(1001L,"小黄","女","福建省龙岩市","福建省长汀县",1L);
                dao.insert(baby);
                Inoculation babyReturn  = new Inoculation();
                babyReturn = dao.load(inoculationId);
                Log.d("查询结果：",babyReturn.getInoculBaby()+" "+babyReturn.getBabyHome());*/
                Intent intent = new Intent(EditBaby.this, MainActivity.class);
                Toast.makeText(EditBaby.this,"点我了",Toast.LENGTH_LONG).show();
                startActivity(intent);

            }

        });
    }
}
