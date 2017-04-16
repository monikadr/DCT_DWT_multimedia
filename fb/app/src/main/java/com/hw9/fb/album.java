package com.hw9.fb;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hw9.fb.utilities.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Monika on 4/11/2017.
 */

public class album  extends Fragment {

    private static user myInstance = null;
    ViewPager pager;
    TabLayout tabLayout;
    userAlbumDetailsAdaptor adapter = null;
    String key;
    String url;
    private static Context ctx;
    ExpandableListView listView = null;


    Map<String, Integer> mapIndex = null;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    LinearLayout indexLayout = null;
    View view = null;
    JSONArray data1 = null;
    ArrayList<JSONObject> data2 = null;
    AlbumViewAdapter adap ;

    public static user getInstance() {
        if (myInstance != null) return myInstance;
        myInstance = new user();
        return myInstance;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //initialize viewpager adapter here
        this.adapter = new userAlbumDetailsAdaptor(getChildFragmentManager());
        Bundle bundle = this.getArguments();
        try {
            JSONObject obj = new JSONObject(bundle.getString("user"));
            key = obj.getString("id");
            Log.d("key", key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = "http://sample-env-1.xj6ungehth.us-west-2.elasticbeanstalk.com/fb.php?uid=" + key;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_detials_albums, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        ctx = getActivity().getApplicationContext();
        this.listView = (ExpandableListView) getView().findViewById(R.id.lvExp);
         this.view = view;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(final JSONObject response) {
                        try {

                                JSONObject jsonObjectsAlbum = new JSONObject();
                                listDataHeader = new ArrayList<String>();
                                listDataChild = new HashMap<String, List<String>>();

                                if (response.has("albums")) {

                                    for (int i = 0; i < response.getJSONObject("albums").getJSONArray("data").length(); i++) {
                                        jsonObjectsAlbum = response.getJSONObject("albums").getJSONArray("data").getJSONObject(i);
                                        listDataHeader.add(jsonObjectsAlbum.getString("name"));

                                        List<String> albumPhoto = new ArrayList<String>();
                                        for (int j = 0; j < jsonObjectsAlbum.getJSONObject("photos").getJSONArray("data").length(); j++) {
                                            albumPhoto.add(jsonObjectsAlbum.getJSONObject("photos").getJSONArray("data").getJSONObject(j).getString("id"));
                                        }
                                        listDataChild.put(listDataHeader.get(i), albumPhoto);
                                    }
                                }
                                if (response.has("albums")) {
                                    // display list
                                    adap = new AlbumViewAdapter(ctx, listDataHeader, listDataChild);
                                    listView.setAdapter(adap);
                                } else {

                                    noAlbum details = new noAlbum();
                                    noPosts po = new noPosts();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(android.R.id.content, details);
                                    // fragmentTransaction.hide(po);
                                    fragmentTransaction.commit();
                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("user error:", error.getMessage());
                        // Toast.makeText(ctx, "user error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(ctx).addToRequestQueue(jsObjRequest);
    }

}




