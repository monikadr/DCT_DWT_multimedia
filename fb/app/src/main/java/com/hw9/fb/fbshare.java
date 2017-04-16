package com.hw9.fb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

//import com.facebook.UiLifecycleHelper;
//import com.facebook.widget.FacebookDialog;

public class fbshare extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        }

}