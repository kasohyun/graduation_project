package com.example.owner.project_final.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PreferenceHelper {
    private static final String BASE_PREFERENCES = "base_preferences";

    public static synchronized void setNickName(Context context, String token) {
        SharedPreferences pref = context.getSharedPreferences(BASE_PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("nick", token);
        editor.apply();
    }

    public static synchronized String getNickName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(BASE_PREFERENCES, Activity.MODE_PRIVATE);
        return pref.getString("nick", null);
    }

}
