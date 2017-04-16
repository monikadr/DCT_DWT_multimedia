package com.hw9.fb;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Monika on 4/12/2017.
 */

public class postListViewAdaptor extends ArrayAdapter<HashMap<String, String>> {



    public postListViewAdaptor(Context context, int resource, ArrayList<HashMap<String,String>> user) {
        super(context, resource, user);


    }

    static class ViewHolder {

        RelativeLayout wrapper;
        ImageView imageView;
        TextView pname;
        TextView message;
        private ImageView fav;


    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        userListViewAdapter.ViewHolder mViewHolder = null;
        HashMap<String, String> user = null;

        //if(convertView!=null) return convertView;

        if (convertView == null) {


            mViewHolder = new userListViewAdapter.ViewHolder();

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.post_row, parent, false);

          //  mViewHolder.wrapper = (LinearLayout) convertView.findViewById(R.id.wrapper);
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.pp);
            mViewHolder.userName = (TextView) convertView.findViewById(R.id.message);
            mViewHolder.time = (TextView) convertView.findViewById(R.id.time);
            mViewHolder.pname = (TextView) convertView.findViewById(R.id.name);
           // mViewHolder.fav = (ImageView) convertView.findViewById(R.id.fav);


            convertView.setTag(mViewHolder);


        } else {
            mViewHolder = (userListViewAdapter.ViewHolder) convertView.getTag();
        }

        user = getItem(position);

        if (user != null) {

                // Log.i("url",String.valueOf(user.getJSONObject("picture").getJSONObject("data").getString("url")));
                //Log.d("image",String.valueOf("user"));
               //Picasso.with(getContext()).load(user.getString("url")).into(mViewHolder.imageView);
                String url = "https://graph.facebook.com/v2.8/"+user.get("url")+"/picture?access_token=EAAED0CT6escBAGjZCH9S4ZAvbvKGcBhPkVpZCy9ZB1TJxzkKCldIfTSRNprOVhfVOsym3j8gFMZBaKyijJBPlgOLuDZCulZCZCuFwMUjFUTFmPgvMkcrnsRC3qvZCNrhLt0mhQbpV0Uy13X2suswzKk1hexz0wa8acU4ZD";
                ImageView pp = (ImageView) convertView.findViewById(R.id.photo);
                new DownloadImageTask(pp)
                        .execute(url);

            String content = user.get("message");
                mViewHolder.userName.setText(content);
            String name = user.get("name");
            mViewHolder.pname.setText(name);
            String time = user.get("createdTime");
            mViewHolder.time.setText(time);







         }

        return convertView;
    }
}

