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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.Map;

/**
 * Created by Monika on 4/11/2017.
 */

public class posts extends Fragment {
    private static user myInstance = null;
    ArrayList<HashMap<String, String>> arraylistUncollected = new ArrayList<HashMap<String, String>>();
    ViewPager pager;
    TabLayout tabLayout;
    pagerAdaptor adapter=null;
    String key ;
    String url ;
    private static Context ctx;
    ListView listView=null;
    Map<String, Integer> mapIndex = null;
    LinearLayout indexLayout = null;
    View view = null;
    JSONArray data1=null;
    private ArrayList<HashMap<String,String>> arr;
    postListViewAdaptor adap = null;

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
        try {
            JSONObject obj = new JSONObject(bundle.getString("user"));
            key = obj.getString("id");
            Log.d("key", key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = "http://sample-env-1.xj6ungehth.us-west-2.elasticbeanstalk.com/fb.php?uid=" + key;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.posts_list, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ctx = getActivity().getApplicationContext();

        this.listView = (ListView) view.findViewById(R.id.postView);
        this.view = view;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(final JSONObject response) {
                        try {

                             ArrayList<JSONObject> jsonObjectsUser = new ArrayList<>();
                            HashMap<String, String> map = new HashMap<String, String>();

                            if(response.has("posts")) {
                                for (int i = 0; i < response.getJSONObject("posts").getJSONArray("data").length(); i++) {
                                    jsonObjectsUser.add(response.getJSONObject("posts").getJSONArray("data").getJSONObject(i));
                                    JSONObject jsject = response.getJSONObject("posts").getJSONArray("data").getJSONObject(i);
                                    map.put("name", response.getString("name"));
                                    map.put("url",response.getString("id"));
                                    map.put("createdTime", jsject.getString("created_time"));
                                    map.put("message", jsject.getString("message"));

                                    // Set the JSON Objects into the array
                                    arraylistUncollected.add(map);
                                }
                            }

                            // display list
                            if(response.has("posts")) {

                                adap = new postListViewAdaptor(ctx, R.id.postView, arraylistUncollected);
                                listView.setAdapter(adap);
                            }
                            else {

                                //noAlbum details = new noAlbum();
                                noPosts po = new noPosts();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(android.R.id.content, po);
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
                        Log.d("user error:",error.getMessage());
                        Toast.makeText(ctx,"user error:"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(ctx).addToRequestQueue(jsObjRequest);
    }
}