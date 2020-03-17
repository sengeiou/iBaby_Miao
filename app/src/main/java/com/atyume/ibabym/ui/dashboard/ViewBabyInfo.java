package com.atyume.ibabym.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.ibabym.Dao.DaoUtils;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.Inoculation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewBabyInfo extends AppCompatActivity {

    private final String TAG = ViewBabyInfo.class.getSimpleName();
    DaoUtils mdaoUtils;

    @BindView(R.id.edittext_certiArea)
    TextView mEditArea;
    @BindView(R.id.edittext_babyName)
    TextView mEditName;
    @BindView(R.id.edittext_babySex)
    TextView mEditSex;
    @BindView(R.id.edittext_homead)
    TextView mEditHome;
    @BindView(R.id.edittext_nowad)
    TextView mEditNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbaby);
        ButterKnife.bind(this);
        mdaoUtils = new DaoUtils(this);

        Inoculation baby = mdaoUtils.queryMeiziById(1001L);
        Log.i(TAG, mdaoUtils.queryMeiziById(1001L).toString());



    }
}
