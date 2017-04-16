package com.hw9.fb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Monika on 4/11/2017.
 */

public class pagerAdaptor extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    private Bundle fragmentBundle=null;

    public pagerAdaptor(FragmentManager fm, int NumOfTabs,Bundle data) {
        super(fm);
        fragmentBundle = data;
        this.mNumOfTabs = NumOfTabs;
    }
    public pagerAdaptor(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                user tab1 = new user();
                tab1.setArguments(this.fragmentBundle);
                return tab1;
            case 1:
                page tab2 = new page();
                tab2.setArguments(this.fragmentBundle);
                return tab2;
            case 2:
                event tab3 = new event();
                tab3.setArguments(this.fragmentBundle);
                return tab3;
            case 3:
                places tab4 = new places();
                tab4.setArguments(this.fragmentBundle);
                return tab4;
            case 4:
                group tab5 = new group();
                tab5.setArguments(this.fragmentBundle);
                return tab5;

            default:
                return new user();
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
