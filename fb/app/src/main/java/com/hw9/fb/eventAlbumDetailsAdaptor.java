package com.hw9.fb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Monika on 4/11/2017.
 */

public class eventAlbumDetailsAdaptor extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    private Bundle fragmentBundle=null;

    public eventAlbumDetailsAdaptor(FragmentManager fm, int NumOfTabs, Bundle data) {
        super(fm);
        fragmentBundle = data;
        this.mNumOfTabs = NumOfTabs;
    }
    public eventAlbumDetailsAdaptor(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                noAlbum tab1 = new noAlbum();
               // tab1.setArguments(this.fragmentBundle);
                return tab1;
            case 1:
                noPosts tab2 = new noPosts();
               // tab2.setArguments(this.fragmentBundle);
                return tab2;

            default:
                return new user();
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
