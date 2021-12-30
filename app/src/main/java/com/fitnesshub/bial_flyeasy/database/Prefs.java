package com.fitnesshub.bial_flyeasy.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.fitnesshub.bial_flyeasy.models.UserModel;
import com.fitnesshub.bial_flyeasy.utils.HelperClass;
import com.google.gson.Gson;

import javax.inject.Inject;

public class Prefs {

    private final Context context;

    @Inject
    public Prefs(Context context) {
        this.context = context.getApplicationContext();
    }

    public boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isUserLoggedIn", false);
    }

    public void setUserLoggedIn(boolean b) {
        SharedPreferences.Editor editor = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean("isUserLoggedIn", b);
        editor.apply();

        if (!b) {
            editor.clear();
            editor.apply();
        }
    }

    public void SetUserData(UserModel user) {
        SharedPreferences.Editor editor = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.apply();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("jwtToken", null);
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("jwtToken", token);
        editor.apply();
    }

    public UserModel getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HelperClass.MY_PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", null);
        return gson.fromJson(json, UserModel.class);
    }
}
