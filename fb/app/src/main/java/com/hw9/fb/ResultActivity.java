package com.hw9.fb;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Monika on 4/10/2017.
 */

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        Bundle args = new Bundle();
        args.putString("key",getIntent().getStringExtra("key"));
        args.putString("lat",getIntent().getStringExtra("lat"));
        args.putString("long",getIntent().getStringExtra("long"));

        final ViewPager viewPager = (ViewPager)  findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);
        final pagerAdaptor adapter = new pagerAdaptor
                (getSupportFragmentManager(), tabLayout.getTabCount(),args);

        viewPager.setAdapter(adapter);
            String key = getIntent().getStringExtra("key");


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

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

