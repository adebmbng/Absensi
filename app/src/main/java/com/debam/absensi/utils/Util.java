package com.debam.absensi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Debam on 9/7/2016.
 */

public class Util {

    public static void saveString(Context activity, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        if (editor.commit()) {
            Log.d("SP", "commited " + value);
        } else {
            Log.d("SP", "not commited");
        }
    }

    public static void saveBoolean(Context activity, String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        if (editor.commit()) {
            Log.d("SP", "commited " + value);
        } else {
            Log.d("SP", "not commited");
        }
    }

    public static String LoginAs(Context ctx){
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String value = sharedPreferences.getString(
                Constant.SP.Login, null);
        return value;
    }

    public static String getUsername(Context ctx){
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String value = sharedPreferences.getString(
                Constant.SP.User, null);
        return value;
    }
    public static String getName(Context ctx){
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String value = sharedPreferences.getString(
                Constant.SP.Name, null);
        return value;
    }

}
