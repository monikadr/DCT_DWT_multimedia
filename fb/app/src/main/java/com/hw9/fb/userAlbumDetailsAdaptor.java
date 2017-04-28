package com.hw9.fb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Monika on 4/11/2017.
 */

public class userAlbumDetailsAdaptor extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    private Bundle fragmentBundle=null;

    public userAlbumDetailsAdaptor(FragmentManager fm, int NumOfTabs,Bundle data) {
        super(fm);
        fragmentBundle = data;
        this.mNumOfTabs = NumOfTabs;
    }
    public userAlbumDetailsAdaptor(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                album tab1 = new album();
                tab1.setArguments(this.fragmentBundle);
                return tab1;
            case 1:
                posts tab2 = new posts();
                tab2.setArguments(this.fragmentBundle);
                return tab2;

            default:
                return new album();
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
