package com.hw9.fb;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Monika on 4/15/2017.
 */

public class noAlbum extends Fragment {
    ActionBar mActionBar;
    ViewPager mPager;
    TabLayout.Tab tab;
    private static Context ctx;
    TextView text;
    View view;

    private static noAlbum myInstance = null;

    public static noAlbum getInstance() {
        if (myInstance != null) return myInstance;
        myInstance = new noAlbum();
        return myInstance;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.no_album, container, false);
        return rootView;
    }


}
