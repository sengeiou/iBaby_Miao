package com.atyume.ibabym.ui.home;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.ibabym.R;

public class MiaoViewActivity extends AppCompatActivity {
    private String[] data = {"九价HPV","四价HPV","二价HPV","三价流感疫苗","四价流感疫苗","狂犬病疫苗","水痘疫苗"};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miaoview);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
            MiaoViewActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView) findViewById(R.id.miao_list_view);
        listView.setAdapter(adapter);
    }
}
