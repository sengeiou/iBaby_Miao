package com.atyume.ibabym.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atyume.greendao.gen.InoculationDao;
import com.atyume.greendao.gen.ParentInfoDao;
import com.atyume.ibabym.R;
import com.atyume.ibabym.basics.Inoculation;
import com.atyume.ibabym.basics.MyApplication;
import com.atyume.ibabym.basics.ParentInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class testActivity extends AppCompatActivity {
    @BindView(R.id.button_add)
    Button mbtnAdd;
    @BindView(R.id.button_select)
    Button mbtnSelect;
    @BindView(R.id.button_delete)
    Button mbtnDalete;
    @BindView(R.id.text_showAll)
    TextView mtextShow;
    @BindView(R.id.button_update)
    TextView mbtnUpdate;

    String all;

    private ParentInfoDao parentDao = MyApplication.getInstances().getDaoSession().getParentInfoDao();
    private InoculationDao babydao = MyApplication.getInstances().getDaoSession().getInoculationDao();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);

    }
    @OnClick({R.id.button_add, R.id.button_select, R.id.button_delete, R.id.button_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_add:
                insertData();
                break;
            case R.id.button_select:
                selectData();
                break;
            case R.id.button_delete:
                deleteData();
                break;
            case R.id.button_update:
                updateData();
                break;
        }
    }
    private void selectData() {
        mtextShow.setText("");
        List<ParentInfo> parentInfos = parentDao.loadAll();
        List<Inoculation> inoculations = babydao.loadAll();
        /*for(ParentInfo parentInfo:parentInfos){
            all=all.concat(parentInfo.toString());
        }*/
        mtextShow.setText(parentInfos.toString()+"baby:"+inoculations.toString());
    }

    private void insertData() {
        ParentInfo parentInfo = new ParentInfo(2L,"15159045183","福建省厦门市",null,null,"123456");
        long insert = parentDao.insert(parentInfo);
        if (insert > 0) {
            Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData(){
        /*parentDao.deleteByKey(2L);*/
        Long id1 = Long.valueOf(1004);
        Long id2 = Long.valueOf(1005);
        babydao.deleteByKey(id1);
        babydao.deleteByKey(id2);

    }

    private void updateData(){
        Inoculation inoculation1 = babydao.queryBuilder().where(InoculationDao.Properties.Id.eq(Long.valueOf(1002))).unique();
        inoculation1.setParentId(3L);
        babydao.update(inoculation1);
    }

}
