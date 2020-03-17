package com.atyume.ibabym.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.atyume.ibabym.R;
import com.atyume.ibabym.ui.home.EditBaby;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    @BindView(R.id.read_topbar)
    QMUITopBar mbtnReadTopbar;
    @BindView(R.id.edit_baby_information)
    Button mbtnEditBaby;
    @BindView(R.id.view_baby_information)
    Button mbtnViewBaby;
    @BindView(R.id.order_miao)
    Button mbtnOrderMiao;
    @BindView(R.id.order_exam)
    Button mbtnOrderExam;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this,root);
        initTopBar();
        /*final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        /*接种信息*/
        mbtnViewBaby.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewBabyInfo.class);
                startActivity(intent);
            }
        });
        /*自助建档*/
        mbtnEditBaby.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditBaby.class);
                startActivity(intent);
            }
        });
        /*疫苗预约*/
        mbtnOrderMiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecyclerMiaoActivity.class);
                startActivity(intent);
            }
        });
        /*体检预约*/
        mbtnOrderExam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), RecyclerExamActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
    private void initTopBar(){
        mbtnReadTopbar.setTitle("学习课堂");
    }
}