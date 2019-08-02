package com.example.newproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapterTwo extends FragmentStatePagerAdapter {
    int counttab;

    public PageAdapterTwo(FragmentManager fm,int counttab) {
        super(fm);
        this.counttab=counttab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                Tabact1 tabact1=new Tabact1();
                return tabact1;
            case 1:
                Tabact2 tabact2=new Tabact2();
                return tabact2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
