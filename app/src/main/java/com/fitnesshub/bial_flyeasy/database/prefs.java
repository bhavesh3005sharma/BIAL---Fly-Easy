package com.fitnesshub.bial_flyeasy.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class prefs {
    public static String getCity(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("city", Context.MODE_PRIVATE);
        //Log.i("GetFirstChange ", sharedPreferences.getBoolean("first", true) + "");
        return sharedPreferences.getString("city","bengaluru");
    }

    public static void setCity(Context context, String city) {
        //Log.i("FirstChange ", bool + "");
        SharedPreferences.Editor editor = context.getSharedPreferences("city", Context.MODE_PRIVATE).edit();
        editor.putString("city",city);
        editor.apply();
    }
}
