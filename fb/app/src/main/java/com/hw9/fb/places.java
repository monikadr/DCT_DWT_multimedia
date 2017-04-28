package com.hw9.fb;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hw9.fb.utilities.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Monika on 4/11/2017.
 */

public class places extends Fragment {
    private static user myInstance = null;
    ViewPager pager;
    TabLayout tabLayout;
    pagerAdaptor adapter=null;
    String key ;
    int cur;
    int len;
    String url ;
    String lat;
    String longitude;
    private static Context ctx;
    ListView listView=null;
    Map<String, Integer> mapIndex = null;
    LinearLayout indexLayout = null;
    View view = null;
    JSONArray data1=null;
    ArrayList<JSONObject> data2=null;
    placeListViewAdapter adap = null;
    public static user getInstance(){
        if(myInstance!=null) return myInstance;
        myInstance = new user();
        return myInstance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //initialize viewpager adapter here
        this.adapter = new pagerAdaptor(getChildFragmentManager());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            key = bundle.getString("key");
            lat = bundle.getString("lat");
            longitude = bundle.getString("long");
        }
        url = "http://sample-env.ikykuzxqc3.us-west-2.elasticbeanstalk.com/fb.php?keyword="+key+"&type=Places&lat="+longitude+"&long="+lat;
        Log.d("url",url);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_list, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ctx = getActivity().getApplicationContext();

        this.listView = (ListView) view.findViewById(R.id.listView);
        this.view = view;
        Button btn = (Button) getView().findViewById(R.id.previous);
        btn.setEnabled(false);
        getData(view);
    }
    public void getData(View view){
       JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(final JSONObject response) {
                        try {

                            final ArrayList<JSONObject> jsonObjectsUser = new ArrayList<JSONObject>();
                            final ArrayList<JSONObject> jsonObjectsUser2 = new ArrayList<JSONObject>();
                            final ArrayList<JSONObject> jsonObjectsUser3 = new ArrayList<JSONObject>();
                            len = response.getJSONArray("data").length();


                            for(int i=0;i<10;i++) {
                                jsonObjectsUser.add(response.getJSONArray("data").getJSONObject(i));
                            }
                            // display list

                            adap = new placeListViewAdapter(ctx, R.id.listView, jsonObjectsUser);
                            listView.setAdapter(adap);

                            cur = 10;
                            Button next = (Button) getView().findViewById(R.id.next);
                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Button btn = (Button) getView().findViewById(R.id.previous);
                                    btn.setEnabled(true);
                                    if(cur == 10) {
                                        for (int i = 10; i < 19; i++) {
                                            try {
                                                if(jsonObjectsUser2.size()!=10)
                                                jsonObjectsUser2.add(response.getJSONArray("data").getJSONObject(i));

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        adap = new placeListViewAdapter(ctx, R.id.listView, jsonObjectsUser2);
                                        listView.setAdapter(adap);
                                        cur = 20;
                                    }
                                    else
                                    if(cur==20)
                                    {
                                        try {
                                            for (int i = 20; i < response.getJSONArray("data").length(); i++) {
                                                try {
                                                   if(jsonObjectsUser3.size()!=5)
                                                    jsonObjectsUser3.add(response.getJSONArray("data").getJSONObject(i));

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        adap = new placeListViewAdapter(ctx, R.id.listView, jsonObjectsUser3);
                                        listView.setAdapter(adap);
                                        Button next = (Button) getView().findViewById(R.id.next);
                                        next.setEnabled(false);
                                        cur=25;
                                    }


                                }
                            });
                            Button previous = (Button) getView().findViewById(R.id.previous);
                            previous.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Button btn = (Button) getView().findViewById(R.id.next);
                                    btn.setEnabled(true);
                                    if(cur==25) {
                                        adap = new placeListViewAdapter(ctx, R.id.listView, jsonObjectsUser2);
                                        listView.setAdapter(adap);
                                        cur=20;
                                    }
                                    else if(cur==20){
                                        adap = new placeListViewAdapter(ctx, R.id.listView, jsonObjectsUser);
                                        listView.setAdapter(adap);
                                        cur=10;
                                        Button previous = (Button) getView().findViewById(R.id.previous);
                                        previous.setEnabled(false);
                                    }


                                }
                            });




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("user error:",error.getMessage());
                       // Toast.makeText(ctx,"user error:"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(ctx).addToRequestQueue(jsObjRequest);
     //   Log.d("response",String.valueOf("response"));
    }
    public void onResume() {
        super.onResume();
        getData(view);

    }

}