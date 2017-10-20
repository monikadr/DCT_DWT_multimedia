package com.hw9.fb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Monika on 4/15/2017.
 */

public class noPosts extends Fragment {
    View rootView;
    private boolean mUserSeen = false;
    private boolean mViewCreated = false;
    userAlbumDetailsAdaptor adapter;
    private static noPosts myInstance = null;

    public static noPosts getInstance() {
        if (myInstance != null) return myInstance;
        myInstance = new noPosts();
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
         rootView = inflater.inflate(R.layout.no_posts, container, false);

        return rootView;

    }



}
