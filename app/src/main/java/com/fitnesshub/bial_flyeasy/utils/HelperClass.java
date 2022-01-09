package com.fitnesshub.bial_flyeasy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperClass {
    public static final String MY_PREFS_NAME = "codesleep-merntest-prefs";
    public static String BASE_URL = "https://codesleep-merntest.azurewebsites.net";

    public static void toast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressbar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    public static void hideProgressbar(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) return false;

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void showSnackBar(String msg, Activity activity, int backgroundColor) {
        TSnackbar snackbar = TSnackbar.make(activity.findViewById(android.R.id.content), msg, TSnackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(backgroundColor);
        TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static String getDate(String dateISO) {
        if(dateISO==null) return "N/A";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = format.parse(dateISO);
            assert date != null;
            String dateStr = (new SimpleDateFormat("dd-mm-yy")).format(date);
            return (dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return ("N/A");
        }
    }

    public static String getTime(String dateISO) {
        if(dateISO==null) return "N/A";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = format.parse(dateISO);
            assert date != null;
            String dateStr = (new SimpleDateFormat("hh:mm")).format(date);
            return (dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return ("N/A");
        }
    }
}
