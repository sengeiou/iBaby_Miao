package com.atyume.ibabym.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.ibabym.MainActivity;
import com.atyume.ibabym.R;

public class MiaoManage extends AppCompatActivity {

    private Button add_miao;
    private Button modify_miao;
    private Button delete_miao;
    private Button select_miao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miaomanage);
        add_miao =(Button) findViewById(R.id.add_miao);
        add_miao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiaoManage.this, EditBaby.class);
                startActivity(intent);//返回页面1
            }
        });
    }
}
