package com.example.newproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    int counttab;

    public PageAdapter(FragmentManager fm,int counttab) {
        super(fm);
        this.counttab=counttab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                TabOne tabOne=new TabOne();
                return tabOne;
            case 1:
                TabTwo tabTwo=new TabTwo();
                return tabTwo;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
