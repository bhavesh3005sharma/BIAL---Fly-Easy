package com.fitnesshub.bial_flyeasy.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.fitnesshub.bial_flyeasy.models.UserModel;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.google.gson.Gson;

public class Prefs {

    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isUserLoggedIn", false);
    }

    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("jwtToken", token);
        editor.apply();
    }

    public static void SetUserData(Context context, UserModel user) {
        SharedPreferences.Editor editor = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.apply();
    }

    public static void setUserLoggedIn(Context context, boolean b) {
        SharedPreferences.Editor editor = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean("isUserLoggedIn", b);
        editor.apply();

        if (!b) {
            editor.clear();
            editor.apply();
        }
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("jwtToken", null);
    }

    public static UserModel getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", null);
        return gson.fromJson(json, UserModel.class);
    }

    public static String getCity(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("city", "Bengaluru");
    }

    public static void setCity(Context context, String city) {
        SharedPreferences.Editor editor = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("city", city);
        editor.apply();
    }
}
