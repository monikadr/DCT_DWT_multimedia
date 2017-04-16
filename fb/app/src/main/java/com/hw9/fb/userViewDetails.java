package com.hw9.fb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Monika on 4/11/2017.
 */

public class userViewDetails extends AppCompatActivity {
    JSONObject jsonObject = null;
    favorite_manage favMan = null;
    fbshare share = null;
    String url,type;
    JSONObject user;
    int key;
    String uid;
    private static Context ctx;
    userAlbumDetailsAdaptor adaptor = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
         favMan = favorite_manage.getInstance(getApplicationContext());
        //share = fbshare.getInstance();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Bundle args = new Bundle();
        args.putString("user",getIntent().getStringExtra("user"));

        try {
            user = new JSONObject(getIntent().getStringExtra("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            uid = user.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        type = getIntent().getStringExtra("type");
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        adaptor = new userAlbumDetailsAdaptor(getSupportFragmentManager(), tabLayout.getTabCount(),args);
        viewPager.setAdapter(adaptor);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.favo) {
            favMan.addToFavorites(type,uid,user);
            Toast.makeText(userViewDetails.this, "Added to Favorites", Toast.LENGTH_SHORT).show();

            return true;
        }
        if (id == R.id.share) {
            Intent intent = new Intent(ctx,fbshare.class);
            startActivity(intent);



        }

        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}