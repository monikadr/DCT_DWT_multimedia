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
    private static final String PERSISTENT_VARIABLE_BUNDLE_KEY = "persistentVariable";
    View rootView;
    ViewPager mPager;
    userAlbumDetailsAdaptor adapter;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //initialize viewpager adapter here
        this.adapter = new userAlbumDetailsAdaptor(getChildFragmentManager());
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.no_album, container, false);
       // mPager = (ViewPager) getView().findViewById(R.id.view_pager);
        //mPager.setOffscreenPageLimit(0);

        return rootView;
    }


}
