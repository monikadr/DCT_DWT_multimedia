package com.hw9.fb.favorites;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hw9.fb.R;
import com.hw9.fb.favorite_manage;
import com.hw9.fb.userViewDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Monika on 4/11/2017.
 */

public class favPageListViewAdapter extends ArrayAdapter<JSONObject> {
    favorite_manage favMan = null;
    public favPageListViewAdapter(Context context, int resource, ArrayList<JSONObject> legislators) {
        super(context, resource, legislators);
        favMan = favorite_manage.getInstance(getApplicationContext());
    }

    static class ViewHolder {

        LinearLayout wrapper;
        ImageView imageView;

        TextView userName;
        private ImageView fav;


        public ImageView details;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        ViewHolder mViewHolder = null;
        JSONObject user = null;

        //if(convertView!=null) return convertView;

        if (convertView == null) {


            mViewHolder = new ViewHolder();

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.users_row, parent, false);

            mViewHolder.wrapper = (LinearLayout) convertView.findViewById(R.id.wrapper);
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.pp);
            mViewHolder.userName = (TextView) convertView.findViewById(R.id.name);
            mViewHolder.fav = (ImageView) convertView.findViewById(R.id.fav);
            mViewHolder.details = (ImageView) convertView.findViewById(R.id.details);
            mViewHolder.details.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // Toast.makeText(getApplicationContext(), "ImageView clicked for the row "+position, Toast.LENGTH_SHORT).show();

                    JSONObject jsonObject = getItem(position);
                    Intent intent = new Intent(getContext(), userViewDetails.class);
                    intent.putExtra("user", String.valueOf(jsonObject));
                    intent.putExtra("type","Pages");
                    getContext().startActivity(intent);
                }
            });

            convertView.setTag(mViewHolder);


        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        user = getItem(position);

        if (user != null) {
            try {
                // Log.i("url",String.valueOf(user.getJSONObject("picture").getJSONObject("data").getString("url")));
                Picasso.with(getContext()).load(String.valueOf(user.getJSONObject("picture").getJSONObject("data").getString("url"))).into(mViewHolder.imageView);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String content = (user.getString("name"));
                mViewHolder.userName.setText(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if(favMan.isFavorited("Pages", user.getString("id"))){
                    mViewHolder.fav.setImageResource(R.mipmap.favorites_on);
                }
                else {
                    mViewHolder.fav.setImageResource(R.mipmap.favorites_off);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            mViewHolder.wrapper.setBackgroundResource(R.drawable.list_item_background);
            mViewHolder.wrapper.setBackground(getContext().getResources().getDrawable(R.drawable.list_item_background, null));
            mViewHolder.wrapper.setBackgroundResource(R.drawable.list_item_background);
        }

        return convertView;
    }
}
