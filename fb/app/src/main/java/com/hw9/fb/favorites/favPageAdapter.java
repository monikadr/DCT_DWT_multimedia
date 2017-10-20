package com.hw9.fb.favorites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hw9.fb.user;

/**
 * Created by Monika on 4/14/2017.
 */

public class favPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment user=null, events=null, pages=null;
    private Bundle fragmentBundle=null;

    public favPageAdapter(FragmentManager fm,int NumOfTabs) {
        super(fm);
      //  fragmentBundle = data;
        this.mNumOfTabs = NumOfTabs;
    }

    public favPageAdapter(FragmentManager fm) {
        super(fm);
        //  fragmentBundle = data;
        //this.mNumOfTabs = NumOfTabs;
    }

    @Override

    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                favUser tab1 = new favUser();
              //  tab1.setArguments(this.fragmentBundle);
                return tab1;
            case 1:
                favPages tab2 = new favPages();
               // tab2.setArguments(this.fragmentBundle);
                return tab2;
            case 2:
                favEvents tab3 = new favEvents();
                //tab3.setArguments(this.fragmentBundle);
                return tab3;
            case 3:
                favPlaces tab4 = new favPlaces();
               // tab4.setArguments(this.fragmentBundle);
                return tab4;
            case 4:
                favGroup tab5 = new favGroup();
                //tab5.setArguments(this.fragmentBundle);
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
