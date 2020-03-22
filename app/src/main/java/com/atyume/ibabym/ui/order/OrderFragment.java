package com.atyume.ibabym.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.TitleFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFragment extends Fragment {
    @BindView(R.id.order_topbar)
    QMUITopBar mOrderTopbar;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this,root);
        initTopBar();

//        tabLayout.setTabTextColors(Color.WHITE, Color.GRAY);//设置文本在选中和为选中时候的颜色
//        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);//设置选中时的指示器的颜色
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//可滑动，默认是FIXED

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OrderMiaoFragment());
        fragments.add(new OrderExamFragment());

        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getChildFragmentManager(), fragments, new String[]{"疫苗预约", "体检预约"});
        viewpager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewpager);
        return root;
    }
    private void initTopBar(){
        mOrderTopbar.setTitle("我的预约");
    }
}