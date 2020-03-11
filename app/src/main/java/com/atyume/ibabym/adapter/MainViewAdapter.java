package com.atyume.ibabym.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.atyume.ibabym.R;


public class MainViewAdapter extends BaseAdapter {
    private Fragment[] fragmentArray;
    private FragmentManager fragmentManager;
    private int hasMsgIndex=0;

    public void setHasMsgIndex(int hasMsgIndex) {
        this.hasMsgIndex = hasMsgIndex;
    }

    public MainViewAdapter(FragmentManager fragmentManager, Fragment[] fragmentArray) {
        this.fragmentManager = fragmentManager;
        this.fragmentArray = fragmentArray;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int hasMsgIndex() {
        return hasMsgIndex;
    }

    @Override
    public String[] getTextArray() {
        return new String[] {"首页", "阅读","我的"};
    }

    @Override
    public Fragment[] getFragmentArray() {
        return fragmentArray;
    }

    @Override
    public int[] getIconImageArray() {
        return new int[] {R.mipmap.home_grey, R.mipmap.read_grey, R.mipmap.mine_grey};
    }

    @Override
    public int[] getSelectedIconImageArray() {
        return new int[] {R.mipmap.home, R.mipmap.read, R.mipmap.mine};
    }

    @Override
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
}

