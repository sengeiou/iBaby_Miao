package com.atyume.ibabym.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.atyume.ibabym.R;

public class AdminViewAdapter extends BaseAdapter {
    private Fragment[] fragmentArray;
    private FragmentManager fragmentManager;
    private int hasMsgIndex=0;

    public void setHasMsgIndex(int hasMsgIndex) {
        this.hasMsgIndex = hasMsgIndex;
    }

    public AdminViewAdapter(FragmentManager fragmentManager, Fragment[] fragmentArray) {
        this.fragmentManager = fragmentManager;
        this.fragmentArray = fragmentArray;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int hasMsgIndex() {
        return hasMsgIndex;
    }

    @Override
    public String[] getTextArray() {
        return new String[] {"管理中心","个人中心"};
    }

    @Override
    public Fragment[] getFragmentArray() {
        return fragmentArray;
    }

    @Override
    public int[] getIconImageArray() {
        return new int[] {R.mipmap.home_grey, R.mipmap.mine_grey};
    }

    @Override
    public int[] getSelectedIconImageArray() {
        return new int[] {R.mipmap.home, R.mipmap.mine};
    }

    @Override
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
}

