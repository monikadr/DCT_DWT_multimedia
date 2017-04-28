package com.hw9.fb.favorites;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hw9.fb.R;
import com.hw9.fb.favorite_manage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Monika on 4/14/2017.
 */

public class favGroup extends Fragment {
    public static favGroup myInstance = null;
    ViewPager pager;
    private static Context ctx;
    ListView listView=null;
    TabLayout tabLayout;
    View view = null;
    JSONArray data=null;
    favorite_manage favMan = null;
    favGroupListViewAdapter adapter;

    public static favGroup getInstance(){
        if(myInstance!=null) return myInstance;
        myInstance = new favGroup();
        return myInstance;
    }

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        favMan = favorite_manage.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pagelayout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        ctx = getActivity().getApplicationContext();

        this.listView = (ListView) view.findViewById(R.id.listView);
        this.view = view;
    }
    public void getUserdata(final Context ctx, JSONArray jsonArray) {
        try{
            int len = jsonArray.length();
            this.data = jsonArray;

            final ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
            for(int i=0;i<jsonArray.length();i++)
                jsonObjects.add(jsonArray.getJSONObject(i));
            adapter = new favGroupListViewAdapter(ctx, R.id.listView, jsonObjects);
            if(this.view!=null){
                listView = (ListView) view.findViewById(R.id.listView);
                listView.setAdapter(adapter);

               /* //details
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        JSONObject jsonObject = (JSONObject) listView.getItemAtPosition(position);
                        Intent intent = new Intent(ctx, FavViewDetails.class);
                        intent.putExtra("user", String.valueOf(jsonObject));
                        intent.putExtra("type","Groups");
                        startActivity(intent);
                    }
                });*/
            }


        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }



    }
    public void onResume() {
        super.onResume();
        getUserdata(getContext(), favMan.getFavorite("Groups"));
    }
}
