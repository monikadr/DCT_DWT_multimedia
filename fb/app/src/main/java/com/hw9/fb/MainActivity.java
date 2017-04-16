package com.hw9.fb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hw9.fb.favorites.fav;
import com.hw9.fb.utilities.trackGPS;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private trackGPS gps;
    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
         //   return true;
       // }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            EditText key = (EditText) findViewById(R.id.keyword);
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("key",key.getText().toString());
            startActivity(intent);
        } else if (id == R.id.nav_Favorites) {
            EditText key = (EditText) findViewById(R.id.keyword);
            Intent intent = new Intent(this, fav.class);
            intent.putExtra("key",key.getText().toString());
            startActivity(intent);
        }
        else if (id == R.id.aboutme) {

            Intent intent = new Intent(this, aboutMe.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void search(View view) {

        EditText key = (EditText) findViewById(R.id.keyword);
       // String message = key.getText().toString();
        if (key.getText().toString().trim().length() <= 0) {
            Toast.makeText(MainActivity.this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
        }
        else
        {
            gps = new trackGPS(MainActivity.this);
            if(gps.canGetLocation()){


                longitude = -122.084;//gps.getLongitude();
                latitude = 37.422;//gps .getLatitude();

                Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
            }
            else
            {

                gps.showSettingsAlert();
            }
           // String url = "http://sample-env-1.xj6ungehth.us-west-2.elasticbeanstalk.com/fb.php?keyword="+key+"type=Users";
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("key",key.getText().toString());
            intent.putExtra("lat",Double.toString(longitude));
            intent.putExtra("long",Double.toString(latitude));
            startActivity(intent);
        }

    }

    public void clear(View view) {
        EditText key = (EditText) findViewById(R.id.keyword);
        // String message = key.getText().toString();
        if (key.getText().toString().trim().length() > 0) {
            EditText keyword = (EditText) findViewById(R.id.keyword);
            keyword.setText("");
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }

}
