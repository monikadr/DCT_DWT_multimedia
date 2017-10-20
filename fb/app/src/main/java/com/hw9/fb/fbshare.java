package com.hw9.fb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

//import com.facebook.UiLifecycleHelper;
//import com.facebook.widget.FacebookDialog;

public class fbshare extends Activity {
    private static final String TAG = "ShareOnFacebook";
    String name ;
    String id;
    String url ;
    Bitmap ur;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        url  = "http://cs-server.usc.edu:45678/";
        //Log.d("a",String.valueOf(Uri.parse(url)));
        shareOnWall();
    }

    void shareOnWall() {
        ShareDialog shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog.registerCallback(callbackManager, new

                FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        onBackPressed();
                        Log.d(TAG, "onSuccess: ");
                        Toast.makeText(fbshare.this, "You shared this post", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel() {
                        onBackPressed();
                        Log.d(TAG, "onCancel: ");
                        Toast.makeText(fbshare.this, "Not shared", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onError(FacebookException error) {
                        onBackPressed();
                        Log.d(TAG, "onError: ");
                        Toast.makeText(fbshare.this, "onError" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            Toast.makeText(fbshare.this, "sharing "+name+"!!", Toast.LENGTH_SHORT).show();

            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(name)
                    .setContentDescription("FB SHARE FROM USC CSCI571 Spring 2017")
                    .setContentUrl(Uri.parse(url))
                    .setImageUrl(Uri.parse(id))

                    .build();

            shareDialog.show(linkContent);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}