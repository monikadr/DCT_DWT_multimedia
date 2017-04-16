package com.hw9.fb;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Monika on 4/11/2017.
 */

public class favorite_manage {
    private static favorite_manage myInstance = null;
        private SharedPreferences prefs = null;
        private static Context context = null;

        public static favorite_manage getInstance(Context ctx){
            if(context==null) context=ctx;
            if(myInstance!=null) return myInstance;
            myInstance = new favorite_manage();
            return myInstance;
        }

        public boolean isFavorited(String type, String id){
            if(this.prefs==null)
                prefs = PreferenceManager.getDefaultSharedPreferences(context);

            switch (type) {

                case "Users":
                    String user_favorite = prefs.getString("user_favorite", null);
                    JSONArray user_jsonarray = null;
                    if(user_favorite!=null){
                        try {
                            user_jsonarray = new JSONArray(user_favorite);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(user_jsonarray!=null && user_jsonarray.length()>0){
                        for (int i = 0; i < user_jsonarray.length(); i++) {
                            try {
                                if (user_jsonarray.getJSONObject(i).getString("id").equals(id)){
                                     return true;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    break;

                case "Pages":
                    String pages_favorite = prefs.getString("pages_favorite", null);
                    JSONArray pages_jsonarray = null;
                    if(pages_favorite!=null){
                        try {
                            pages_jsonarray = new JSONArray(pages_favorite);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(pages_jsonarray!=null && pages_jsonarray.length()>0){
                        for (int i = 0; i < pages_jsonarray.length(); i++) {
                            try {
                                if (pages_jsonarray.getJSONObject(i).getString("id").equals(id)){
                                     return true;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    break;

                case "Events":
                    String events_favorite = prefs.getString("events_favorite", null);
                    JSONArray events_jsonarray = null;
                    if(events_favorite!=null){
                        try {
                            events_jsonarray = new JSONArray(events_favorite);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(events_jsonarray!=null && events_jsonarray.length()>0){
                        for (int i = 0; i < events_jsonarray.length(); i++) {
                            try {
                                if (events_jsonarray.getJSONObject(i).getString("id").equals(id)){
                                    return true;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    break;

                case "Places":
                    String places_favorite = prefs.getString("places_favorite", null);
                    JSONArray places_jsonarray = null;
                    if(places_favorite!=null){
                        try {
                            places_jsonarray = new JSONArray(places_favorite);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(places_jsonarray!=null && places_jsonarray.length()>0){
                        for (int i = 0; i < places_jsonarray.length(); i++) {
                            try {
                                if (places_jsonarray.getJSONObject(i).getString("id").equals(id)){
                                    return true;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    break;
                case "Groups":
                    String groups_favorite = prefs.getString("groups_favorite", null);
                    JSONArray groups_jsonarray = null;
                    if(groups_favorite!=null){
                        try {
                            groups_jsonarray = new JSONArray(groups_favorite);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(groups_jsonarray!=null && groups_jsonarray.length()>0){
                        for (int i = 0; i < groups_jsonarray.length(); i++) {
                            try {
                                if (groups_jsonarray.getJSONObject(i).getString("id").equals(id)){
                                    return true;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    break;
                default:
                    return false;
            }

            return false;
        }
    public void removeFromFavorites(String type, String id){
        if(this.prefs==null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        if(!isFavorited(type, id)) return;

        switch (type) {

            case "Users":
                String user_favorite = prefs.getString("user_favorite", null);
                JSONArray user_jsonarray = null,
                        new_user_jsonarray = new JSONArray();
                if(user_favorite!=null){
                    try {
                        user_jsonarray = new JSONArray(user_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(user_jsonarray!=null && user_jsonarray.length()>0){
                    for (int i = 0; i < user_jsonarray.length(); i++) {
                        try {
                            if (!(user_jsonarray.getJSONObject(i).getString("id").equals(id)))
                                new_user_jsonarray.put(user_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                editor.putString("user_favorite", String.valueOf(new_user_jsonarray));
                  break;

            case "Pages":
                String pages_favorite = prefs.getString("pages_favorite", null);
                JSONArray pages_jsonarray = null,
                        new_pages_jsonarray = new JSONArray();
                if(pages_favorite!=null){
                    try {
                        pages_jsonarray = new JSONArray(pages_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(pages_jsonarray!=null && pages_jsonarray.length()>0){
                    for (int i = 0; i < pages_jsonarray.length(); i++) {
                        try {
                            if (!(pages_jsonarray.getJSONObject(i).getString("id").equals(id)))
                                new_pages_jsonarray.put(pages_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                editor.putString("pages_favorite", String.valueOf(new_pages_jsonarray));
                break;

            case "Events":
                String events_favorite = prefs.getString("events_favorite", null);
                JSONArray events_jsonarray = null,
                        new_events_jsonarray = new JSONArray();
                if(events_favorite!=null){
                    try {
                        events_jsonarray = new JSONArray(events_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(events_jsonarray!=null && events_jsonarray.length()>0){
                    for (int i = 0; i < events_jsonarray.length(); i++) {
                        try {
                            if (!(events_jsonarray.getJSONObject(i).getString("id").equals(id)))
                                new_events_jsonarray.put(events_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                editor.putString("events_favorite", String.valueOf(new_events_jsonarray));
                 break;

            case "Places":
                String places_favorite = prefs.getString("places_favorite", null);
                JSONArray places_jsonarray = null,
                        new_places_jsonarray = new JSONArray();
                if(places_favorite!=null){
                    try {
                        places_jsonarray = new JSONArray(places_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(places_jsonarray!=null && places_jsonarray.length()>0){
                    for (int i = 0; i < places_jsonarray.length(); i++) {
                        try {
                            if (!(places_jsonarray.getJSONObject(i).getString("id").equals(id)))
                                new_places_jsonarray.put(places_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                editor.putString("places_favorite", String.valueOf(new_places_jsonarray));
                break;
            case "Groups":
                String groups_favorite = prefs.getString("groups_favorite", null);
                JSONArray groups_jsonarray = null,
                        new_groups_jsonarray = new JSONArray();
                if(groups_favorite!=null){
                    try {
                        groups_jsonarray = new JSONArray(groups_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(groups_jsonarray!=null && groups_jsonarray.length()>0){
                    for (int i = 0; i < groups_jsonarray.length(); i++) {
                        try {
                            if (!(groups_jsonarray.getJSONObject(i).getString("id").equals(id)))
                                new_groups_jsonarray.put(groups_jsonarray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                editor.putString("groups_favorite", String.valueOf(new_groups_jsonarray));
                break;
            default:
                return;
        }

        editor.apply();
    }
// to change
    public void addToFavorites(String type, String id, JSONObject object){
        if(this.prefs==null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        if(isFavorited(type, id)) return;
        switch (type) {

            case "Users":
                String user_favorite = prefs.getString("user_favorite", null);
                JSONArray user_jsonarray = new JSONArray();
                if(user_favorite!=null){
                    try {
                        user_jsonarray = new JSONArray(user_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                user_jsonarray.put(object);
                editor.putString("user_favorite", String.valueOf(user_jsonarray));
                  break;

            case "Pages":
                String pages_favorite = prefs.getString("pages_favorite", null);
                JSONArray pages_jsonarray = new JSONArray();
                if(pages_favorite!=null){
                    try {
                        pages_jsonarray = new JSONArray(pages_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                pages_jsonarray.put(object);
                editor.putString("pages_favorite", String.valueOf(pages_jsonarray));
                break;

            case "Events":
                String events_favorite = prefs.getString("events_favorite", null);
                JSONArray events_jsonarray = new JSONArray();
                if(events_favorite!=null){
                    try {
                        events_jsonarray = new JSONArray(events_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                events_jsonarray.put(object);
                editor.putString("events_favorite", String.valueOf(events_jsonarray));
                  break;

            case "Places":
                String places_favorite = prefs.getString("places_favorite", null);
                JSONArray places_jsonarray = new JSONArray();
                if(places_favorite!=null){
                    try {
                        places_jsonarray = new JSONArray(places_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                places_jsonarray.put(object);
                editor.putString("places_favorite", String.valueOf(places_jsonarray));
                break;


            case "Groups":
                String groups_favorite = prefs.getString("groups_favorite", null);
                JSONArray groups_jsonarray = new JSONArray();
                if(groups_favorite!=null){
                    try {
                        groups_jsonarray = new JSONArray(groups_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                groups_jsonarray.put(object);
                editor.putString("groups_favorite", String.valueOf(groups_jsonarray));
                break;

            default:
                 return;
        }
        editor.apply();
    }

    public JSONArray getFavorite(String type){
        if(this.prefs==null)
            prefs = PreferenceManager.getDefaultSharedPreferences(context);

        switch (type) {

            case "Users":
                 String user_favorite = prefs.getString("user_favorite", null);
                JSONArray user_jsonarray = new JSONArray();
                if(user_favorite!=null){
                    try {
                        user_jsonarray = new JSONArray(user_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return  user_jsonarray;

            case "Pages":
                String pages_favorite = prefs.getString("pages_favorite", null);
                JSONArray pages_jsonarray = new JSONArray();
                if(pages_favorite!=null){
                    try {
                        pages_jsonarray = new JSONArray(pages_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return pages_jsonarray;

            case "Events":
                  String events_favorite = prefs.getString("events_favorite", null);
                JSONArray events_jsonarray = new JSONArray();
                if(events_favorite!=null){
                    try {
                        events_jsonarray = new JSONArray(events_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return events_jsonarray;
            case "Places":
                String places_favorite = prefs.getString("places_favorite", null);
                JSONArray places_jsonarray = new JSONArray();
                if(places_favorite!=null){
                    try {
                        places_jsonarray = new JSONArray(places_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return places_jsonarray;

            case "Groups":
                String groups_favorite = prefs.getString("groups_favorite", null);
                JSONArray groups_jsonarray = new JSONArray();
                if(groups_favorite!=null){
                    try {
                        groups_jsonarray = new JSONArray(groups_favorite);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return groups_jsonarray;

            default:
                return new JSONArray();
        }
    }

}
